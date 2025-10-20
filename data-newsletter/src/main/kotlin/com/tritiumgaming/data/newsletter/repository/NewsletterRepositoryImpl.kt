package com.tritiumgaming.data.newsletter.repository

import com.tritiumgaming.core.common.network.ConnectivityManagerHelper
import com.tritiumgaming.data.newsletter.dto.flat.FlattenedNewsletterInboxDto
import com.tritiumgaming.data.newsletter.dto.flat.toExternal
import com.tritiumgaming.data.newsletter.dto.local.toInternal
import com.tritiumgaming.data.newsletter.dto.remote.toInternal
import com.tritiumgaming.data.newsletter.source.local.NewsletterLocalDataSource
import com.tritiumgaming.data.newsletter.source.remote.NewsletterRemoteDataSource
import com.tritiumgaming.shared.home.domain.newsletter.model.NewsletterInbox
import com.tritiumgaming.shared.home.domain.newsletter.repository.NewsletterRepository
import com.tritiumgaming.shared.home.domain.newsletter.source.NewsletterDatastore
import io.ktor.http.Url
import kotlinx.coroutines.CoroutineDispatcher

class NewsletterRepositoryImpl(
    private val localDataSource: NewsletterLocalDataSource,
    private val remoteDataSource: NewsletterRemoteDataSource,
    private val dataStoreSource: NewsletterDatastore,
    private val connectivityManagerHelper: ConnectivityManagerHelper,
    coroutineDispatcher: CoroutineDispatcher
): NewsletterRepository {

    override fun initializeDatastoreLiveData() = dataStoreSource.initializeDatastoreLiveData()

    override fun initDatastoreFlow() = dataStoreSource.initDatastoreFlow()

    private var localCache: List<FlattenedNewsletterInboxDto> = emptyList()

    private fun getLocalInboxes(): Result<List<FlattenedNewsletterInboxDto>> {
        val result = localDataSource.fetchInboxes()
        val out = result.getOrDefault(emptyList()).toInternal()
        return Result.success(out)
    }

    private suspend fun fetchRemoteInbox(
        inboxUrl: Url
    ): Result<FlattenedNewsletterInboxDto> {
        val result = remoteDataSource.fetchInbox(inboxUrl)

        return result.map{ dto -> dto.toInternal() }
    }

    private suspend fun fetchRemoteInboxes(): Result<List<FlattenedNewsletterInboxDto>> {

        val connection = connectivityManagerHelper.getActiveNetworkTransport()
        connection.exceptionOrNull()?.let { e ->
            return Result.failure(Exception("ABORTING remote inbox fetch.", e))
        }

        localCache.map { inbox ->
            inbox.url?.let { rawURL ->
                val result = fetchRemoteInbox(Url(rawURL))
                result.getOrNull()?.let { remote ->
                    inbox.channel = remote.channel
                }
            }
        }

        return Result.success(localCache)
    }

    override suspend fun fetchInboxes(): Result<List<NewsletterInbox>> {

        // Accept Synchronization:
        // Initialize the Inboxes with locally. Fetch remote data if possible
        if(localCache.isEmpty()) {
            localCache = getLocalInboxes().getOrDefault(emptyList())
        }

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

        return Result.success(result.getOrThrow().toExternal()) // Successful initialization

    }

    override suspend fun saveInboxLastReadDate(id: String, date: Long) =
        dataStoreSource.setLastReadDate(id, date)

    init {
        initializeDatastoreLiveData()
    }

}

