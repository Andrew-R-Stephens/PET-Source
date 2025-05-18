package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterInbox

interface NewsletterRemoteDataSource {

    suspend fun fetchInbox(inbox: NewsletterInbox)

}