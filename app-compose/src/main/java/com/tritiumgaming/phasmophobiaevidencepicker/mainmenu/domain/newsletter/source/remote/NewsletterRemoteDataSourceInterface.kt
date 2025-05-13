package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source.remote

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterInbox

interface NewsletterRemoteDataSourceInterface {
    suspend fun fetchRemoteInboxData(inbox: NewsletterInbox)
}