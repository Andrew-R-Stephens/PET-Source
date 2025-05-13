package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.repository

import android.content.Context
import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local.NewsletterLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.remote.NewsletterRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterInbox
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.repository.NewsletterRepository
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.repository.NewsletterRepository.NewsletterInboxType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsletterRepository(
    context: Context,
    localDataSource: NewsletterLocalDataSource,
    remoteDataSource: NewsletterRemoteDataSource
): NewsletterRepository {

    override val _inboxes =
        MutableStateFlow<Map<NewsletterInboxType.InboxTypeDTO, NewsletterInbox>>(mapOf())
    override val inboxes = _inboxes.asStateFlow()
    override fun setInboxes(inboxes: Map<NewsletterInboxType.InboxTypeDTO, NewsletterInbox>) {
        _inboxes.update { inboxes }
    }

    init {
        Log.d("NewsletterRepository", "Initializing")

        setInboxes(localDataSource.createInboxes(context))
        _inboxes.value.entries.forEach { inboxPair ->
            CoroutineScope(context = Dispatchers.IO).launch {
                remoteDataSource.fetchRemoteInboxData(inboxPair.value)
            }
        }
    }

}