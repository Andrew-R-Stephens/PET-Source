package com.tritiumgaming.data.newsletter.source.remote

import com.tritiumgaming.data.newsletter.dto.remote.RemoteNewsletterInboxDto
import com.tritiumgaming.data.newsletter.source.remote.api.NewsletterService
import io.ktor.http.Url
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsletterRemoteDataSourceImpl(
    private val newsletterApi: NewsletterService
): NewsletterRemoteDataSource {

    override suspend fun fetchInbox(
        url: Url
    ): Result<RemoteNewsletterInboxDto> = withContext(Dispatchers.IO) {
        Result.success(newsletterApi.fetchInbox(url).getOrThrow())
    }

}

