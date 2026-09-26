package com.tritiumgaming.data.newsletter.source.remote

import com.tritiumgaming.data.newsletter.dto.remote.RemoteNewsletterInboxDto
import io.ktor.http.Url
import kotlinx.coroutines.flow.Flow

interface NewsletterRemoteDataSource {

    suspend fun fetchInbox(url: Url): Result<RemoteNewsletterInboxDto>

    suspend fun fetchInboxes(urls: List<Url>): Flow<Result<List<RemoteNewsletterInboxDto>>>

}