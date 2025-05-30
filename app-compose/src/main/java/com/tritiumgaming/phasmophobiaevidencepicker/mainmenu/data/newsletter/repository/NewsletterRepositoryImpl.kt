package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.repository

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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NewsletterRepositoryImpl(
    private val localDataSource: NewsletterLocalDataSource,
    private val remoteDataSource: NewsletterRemoteDataSource,
    private val dataStoreSource: NewsletterDatastore,
    coroutineDispatcher: CoroutineDispatcher
): NewsletterRepository {

    private var cache: List<FlattenedNewsletterInboxDto> = emptyList()

    override fun initialSetupEvent() = dataStoreSource.initialSetupEvent()
    override suspend fun initFlow() = dataStoreSource.flow

    override suspend fun getLocalInboxes(): List<FlattenedNewsletterInboxDto> =
        localDataSource.fetchInboxes().toInternal()
    override suspend fun fetchRemoteInbox(inboxUrl: Url): FlattenedNewsletterInboxDto =
        remoteDataSource.fetchInbox(inboxUrl).toInternal()

    override suspend fun synchronizeInboxes() {
        cache.map { inbox ->
            inbox.url?.let { rawURL ->
                val remote = remoteDataSource.fetchInbox(Url(rawURL)).toInternal()
                inbox.channel = remote.channel
            }
        }
    }

    override fun getInboxes(): List<NewsletterInbox> = cache.toExternal()

    override suspend fun saveInboxLastReadDate(id: String, date: Long) =
        dataStoreSource.setLastReadDate(id, date)

    init {
        initialSetupEvent()

        CoroutineScope(coroutineDispatcher).launch {
            cache = getLocalInboxes()
            synchronizeInboxes()
        }
    }

}
