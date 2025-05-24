package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.model.NewsletterInboxType
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterInbox
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source.NewsletterLocalDataSource

class NewsletterLocalDataSource(
    private val applicationContext: Context
): NewsletterLocalDataSource {

    override fun fetchInboxes(): Map<NewsletterInboxType.NewsletterInboxTypeDTO, NewsletterInbox> {

        val inboxes = mutableMapOf<NewsletterInboxType.NewsletterInboxTypeDTO, NewsletterInbox>()

        NewsletterInboxType.NewsletterInboxTypeDTO.entries.forEach {
            inboxes[it] = NewsletterInbox(
                NewsletterInboxType.create(
                    context = applicationContext,
                    inboxType = it
                )
            )
        }

        return inboxes
    }

}