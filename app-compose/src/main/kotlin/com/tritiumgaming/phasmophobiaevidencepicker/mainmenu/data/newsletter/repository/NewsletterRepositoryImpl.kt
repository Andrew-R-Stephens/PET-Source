package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.repository

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.network.ConnectivityManagerHelper
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.FlattenedNewsletterInboxDto
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local.NewsletterLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.mapper.toExternal
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.mapper.toInternal
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.remote.NewsletterRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterInbox
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.repository.NewsletterRepository
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source.NewsletterDatastore
import io.ktor.http.Url
import kotlinx.coroutines.CoroutineDispatcher

class NewsletterRepositoryImpl(
    private val localDataSource: NewsletterLocalDataSource,
    private val remoteDataSource: NewsletterRemoteDataSource,
    private val dataStoreSource: NewsletterDatastore,
    private val connectivityManagerHelper: ConnectivityManagerHelper,
    coroutineDispatcher: CoroutineDispatcher
): NewsletterRepository {

    private var cache: List<FlattenedNewsletterInboxDto> = emptyList()

    override fun getLocalInboxes(): Result<List<FlattenedNewsletterInboxDto>> {
        val result = localDataSource.fetchInboxes()
        val out = result.getOrDefault(emptyList()).toInternal()
        return Result.success(out)
    }

    override suspend fun fetchRemoteInbox(
        inboxUrl: Url
    ): Result<FlattenedNewsletterInboxDto> {
        val result = remoteDataSource.fetchInbox(inboxUrl)

        return result.map{ dto -> dto.toInternal() }
    }

    suspend fun fetchRemoteInboxes(): Result<List<FlattenedNewsletterInboxDto>> {

        val connection = connectivityManagerHelper.getActiveNetworkTransport()
        connection.exceptionOrNull()?.let { e ->
            return Result.failure(Exception("ABORTING remote inbox fetch.", e))
        }

        cache.map { inbox ->
            inbox.url?.let { rawURL ->
                val result = fetchRemoteInbox(Url(rawURL))
                result.getOrNull()?.let { remote ->
                    inbox.channel = remote.channel
                }
            }
        }

        return Result.success(cache)
    }

    override suspend fun synchronizeInboxes(
        forceUpdate: Boolean
    ): Result<Boolean> {

        // Accept Synchronization:
        // Initialize the Inboxes with locally. Fetch remote data if possible
        if(cache.isEmpty()) {
            cache = getLocalInboxes().getOrDefault(emptyList())
        }

        // Now that the cache is initialized locally, Fetch remote data if possible
        val cacheMessageSum = cache.sumOf { it.channel?.messages?.size ?: 0 } // Count saved messages
        if(forceUpdate || cacheMessageSum == 0) {
            // Check for internet connection. Exit if no connection
            val connection = connectivityManagerHelper.getActiveNetworkTransport()
            connection.exceptionOrNull()?.let { e ->
                return Result.failure(Exception("ABORTING remote inbox synchronization.", e))
            }

            // Update inboxes. Exit if failure
            val result = fetchRemoteInboxes()
            result.exceptionOrNull()?.let { e ->
                return Result.failure(Exception("Remote synchronization failed.", e))
            }

            return Result.success(true) // Successful initialization

        }
        // Reject resynchronization if not allowed to force update
        Log.w("Newsletter", "SKIPPING remote inbox synchronization. " +
                "ForceUpdate: $forceUpdate or cacheMessageSum: $cacheMessageSum != 0")
        return Result.success(false) // Unsuccessful update

    }

    override fun getInboxes(): List<NewsletterInbox> = cache.toExternal()

    override suspend fun saveInboxLastReadDate(id: String, date: Long) =
        dataStoreSource.setLastReadDate(id, date)

    override fun initialSetupEvent() = dataStoreSource.initialSetupEvent()
    override suspend fun initFlow() = dataStoreSource.flow

    init {
        initialSetupEvent()
    }

}

