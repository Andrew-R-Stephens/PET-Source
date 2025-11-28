package com.tritiumgaming.data.newsletter.repository

import com.tritiumgaming.core.common.network.ConnectivityManagerHelper
import com.tritiumgaming.data.newsletter.dto.flat.FlattenedNewsletterInboxDto
import com.tritiumgaming.data.newsletter.dto.flat.toExternal
import com.tritiumgaming.data.newsletter.dto.local.toInternal
import com.tritiumgaming.data.newsletter.dto.remote.toInternal
import com.tritiumgaming.data.newsletter.source.local.NewsletterLocalDataSource
import com.tritiumgaming.data.newsletter.source.remote.NewsletterRemoteDataSource
import com.tritiumgaming.shared.data.newsletter.repository.NewsletterRepository
import com.tritiumgaming.shared.data.newsletter.source.NewsletterDatastore
import com.tritiumgaming.shared.data.newsletter.model.NewsletterInbox
import io.ktor.http.Url
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class NewsletterRepositoryImpl(
    private val localDataSource: NewsletterLocalDataSource,
    private val remoteDataSource: NewsletterRemoteDataSource,
    private val dataStoreSource: NewsletterDatastore,
    private val connectivityManagerHelper: ConnectivityManagerHelper,
    private val coroutineDispatcher: CoroutineDispatcher
): NewsletterRepository {

    override fun initializeDatastoreLiveData() = dataStoreSource.initializeDatastoreLiveData()

    override fun initDatastoreFlow() = dataStoreSource.initDatastoreFlow()

    private var _inboxFlow = MutableStateFlow<List<FlattenedNewsletterInboxDto>>(emptyList())
    val flow: StateFlow<List<FlattenedNewsletterInboxDto>> = _inboxFlow.asStateFlow()

    private fun getLocalInboxes(): Result<List<FlattenedNewsletterInboxDto>> {
        val result = localDataSource.fetchInboxes()
        val out = result.getOrDefault(emptyList()).toInternal()
        return Result.success(out)
    }

    private suspend fun fetchRemoteInbox(
        inboxUrl: Url
    ): Result<FlattenedNewsletterInboxDto> {
        val result = remoteDataSource.fetchInbox(inboxUrl)
        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Failed to fetch remote inbox.", it))
        }

        return try {
            result.map{ dto -> dto.toInternal() }
        } catch (e: Exception) {
            Result.failure(Exception("Failed to parse remote inbox.", e))
        }
    }

    private suspend fun fetchRemoteInboxes(): Result<List<FlattenedNewsletterInboxDto>> {
        val connection = connectivityManagerHelper.getActiveNetworkTransport()
        connection.exceptionOrNull()?.let { e ->
            return Result.failure(Exception("ABORTING remote inbox fetch.", e))
        }

        val currentCache = getLocalInboxes().getOrThrow() // Use the current value from the flow

        currentCache.map { inbox ->
            inbox.url?.let { rawURL ->
                val result = fetchRemoteInbox(Url(rawURL))
                result.getOrThrow().let { remote ->
                    inbox.channel = remote.channel
                }
            }
        }

        return Result.success(currentCache)
    }

    override suspend fun fetchInboxes(): Result<List<NewsletterInbox>> {

        // Accept Synchronization:
        // Initialize the Inboxes with locally. Fetch remote data if possible
        if(_inboxFlow.value.isEmpty()) {
            _inboxFlow.update { getLocalInboxes().getOrDefault(emptyList()) }
        }

        // Check for internet connection. Exit if no connection
        val connection = connectivityManagerHelper.getActiveNetworkTransport()
        connection.exceptionOrNull()?.let { e ->
            return Result.failure(Exception("ABORTING remote inbox synchronization.", e))
        }

        // Update inboxes. Exit if failure
        return try {
            val internal = fetchRemoteInboxes().getOrThrow()
            _inboxFlow.update { internal }
            Result.success(flow.value.toExternal()) // Successful initialization
        } catch (e: Exception) {
            Result.failure(Exception("There was an error parsing the inboxes.", e))
        }
    }

    override fun getInboxFlow(): Flow<List<NewsletterInbox>> = flow
        .map { it.toExternal() }

    override suspend fun saveInboxLastReadDate(id: String, date: Long) =
        dataStoreSource.setLastReadDate(id, date)

    init {
        initializeDatastoreLiveData()
    }

}

