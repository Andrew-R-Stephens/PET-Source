package com.tritiumgaming.data.newsletter.source.remote

import com.tritiumgaming.data.newsletter.dto.remote.RemoteNewsletterInboxDto
import io.ktor.http.Url

interface NewsletterRemoteDataSource {

    suspend fun fetchInbox(url: Url): Result<RemoteNewsletterInboxDto>

}