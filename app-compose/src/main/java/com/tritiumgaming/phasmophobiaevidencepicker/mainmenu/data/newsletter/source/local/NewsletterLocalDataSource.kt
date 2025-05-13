package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterInbox
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.repository.NewsletterRepository.NewsletterInboxType
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source.local.NewsletterLocalDataSource

class NewsletterLocalDataSource: NewsletterLocalDataSource {

    override fun createInboxes(context: Context): Map<NewsletterInboxType.InboxTypeDTO, NewsletterInbox> {

        val inboxes = mutableMapOf<NewsletterInboxType.InboxTypeDTO, NewsletterInbox>()

        NewsletterInboxType.InboxTypeDTO.entries.forEach {
            inboxes[it] = NewsletterInbox(
                NewsletterInboxType.create(context = context, inboxType = it)
            )
        }

        return inboxes
    }

}