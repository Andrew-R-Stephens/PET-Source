package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local.dto.LocalNewsletterInboxDto

interface NewsletterLocalDataSource {

    //fun fetchInboxes(): Map<NewsletterInboxType.NewsletterInboxTypeDTO, NewsletterInbox>

    fun fetchInboxes(): Result<List<LocalNewsletterInboxDto>>

}