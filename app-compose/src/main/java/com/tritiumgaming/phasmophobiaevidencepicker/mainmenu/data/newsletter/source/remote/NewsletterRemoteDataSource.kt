package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.remote

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.remote.api.NewsletterService
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.remote.dto.RemoteNewsletterInboxDto
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source.NewsletterRemoteDataSource
import io.ktor.http.Url

class NewsletterRemoteDataSource(
    private val newsletterApi: NewsletterService
): NewsletterRemoteDataSource {

    override suspend fun fetchInbox(inboxUrl: Url): RemoteNewsletterInboxDto =
        newsletterApi.fetchInbox(inboxUrl).getOrThrow()


}

