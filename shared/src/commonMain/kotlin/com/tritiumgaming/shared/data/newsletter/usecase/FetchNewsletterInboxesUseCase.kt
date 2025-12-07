package com.tritiumgaming.shared.data.newsletter.usecase

import com.tritiumgaming.shared.data.newsletter.model.NewsletterInbox
import com.tritiumgaming.shared.data.newsletter.repository.NewsletterRepository

class FetchNewsletterInboxesUseCase(
    private val repository: NewsletterRepository
) {
    suspend operator fun invoke(
        forceRefresh: Boolean = false,
        onRefreshFailure: () -> Unit = {}
    ): Result<List<NewsletterInbox>> {
        val result = repository.fetchInboxes(forceRefresh) {
            onRefreshFailure()
        }

        result.exceptionOrNull()?.let { e ->
            throw Exception("Failed to fetch inboxes.", e)
        }

        return result
    }
}