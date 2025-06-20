package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.local.LocalNewsletterInboxDto

interface NewsletterLocalDataSource {

    //fun fetchInboxes(): Map<NewsletterInboxType.NewsletterInboxTypeDTO, NewsletterInbox>

    fun fetchInboxes(): Result<List<LocalNewsletterInboxDto>>

}