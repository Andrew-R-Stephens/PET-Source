package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.model.NewsletterInboxType
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterInbox

interface NewsletterLocalDataSource {

    fun fetchInboxes(): Map<NewsletterInboxType.NewsletterInboxTypeDTO, NewsletterInbox>

}