package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source.local

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterInbox
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.repository.NewsletterRepository

interface NewsletterLocalDataSource {

    fun createInboxes(context: Context): Map<NewsletterRepository.NewsletterInboxType.InboxTypeDTO, NewsletterInbox>

}