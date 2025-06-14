package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.remote

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.remote.RemoteNewsletterInboxDto
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.remote.api.NewsletterService
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source.NewsletterRemoteDataSource
import io.ktor.http.Url
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsletterRemoteDataSource(
    private val newsletterApi: NewsletterService
): NewsletterRemoteDataSource {

    override suspend fun fetchInbox(
        inboxUrl: Url
    ): Result<RemoteNewsletterInboxDto> = withContext(Dispatchers.IO) {
        Result.success(newsletterApi.fetchInbox(inboxUrl).getOrThrow())
    }

}

