package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.repository

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local.NewsletterLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.model.NewsletterInboxType
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.remote.NewsletterRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterInbox
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.repository.NewsletterRepository

class NewsletterRepositoryImpl(
    private val localDataSource: NewsletterLocalDataSource,
    private val remoteDataSource: NewsletterRemoteDataSource
): NewsletterRepository {

    override suspend fun getInboxes(): Map<NewsletterInboxType.NewsletterInboxTypeDTO, NewsletterInbox> {

        val localInboxes = localDataSource.fetchInboxes()
        localInboxes.entries.forEach { inboxPair ->
            remoteDataSource.fetchInbox(inboxPair.value)
        }

        return localInboxes
    }

}