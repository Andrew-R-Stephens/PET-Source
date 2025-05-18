package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.repository

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.model.NewsletterInboxType
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterInbox

interface NewsletterRepository {

    suspend fun getInboxes(): Map<NewsletterInboxType.InboxTypeDTO, NewsletterInbox>

}