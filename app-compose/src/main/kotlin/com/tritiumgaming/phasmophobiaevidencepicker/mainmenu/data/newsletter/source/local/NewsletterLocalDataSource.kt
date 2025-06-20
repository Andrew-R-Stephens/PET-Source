package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local

interface NewsletterLocalDataSource {

    fun fetchInboxes(): Result<List<com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.local.LocalNewsletterInboxDto>>

}