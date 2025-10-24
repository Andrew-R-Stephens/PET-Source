package com.tritiumgaming.data.newsletter.source.remote

import android.net.http.HttpException
import android.util.Log
import com.tritiumgaming.data.newsletter.dto.remote.RemoteNewsletterInboxDto
import com.tritiumgaming.data.newsletter.source.remote.api.NewsletterService
import io.ktor.http.Url
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.io.IOException

class NewsletterRemoteDataSourceImpl(
    private val newsletterApi: NewsletterService,
    private val dispatcher: CoroutineDispatcher
): NewsletterRemoteDataSource {

    override suspend fun fetchInbox(
        url: Url
    ): Result<RemoteNewsletterInboxDto> = withContext(dispatcher) {
        val inboxes = newsletterApi.fetchInbox(url).getOrElse {
            return@withContext Result.failure(it)
        }
        Result.success(inboxes)
    }

    override suspend fun fetchInboxes(
        urls: List<Url>
    ): Flow<Result<List<RemoteNewsletterInboxDto>>> = flow {

        try {
            val articles = urls.map { url ->
                newsletterApi.fetchInbox(url).getOrThrow()
            }

            emit(Result.success(articles))

        } catch (e: IOException) {
            emit(Result.failure(e))
        }

    }.flowOn(dispatcher)

}

