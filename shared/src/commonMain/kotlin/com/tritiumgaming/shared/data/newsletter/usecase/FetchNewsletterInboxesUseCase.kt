package com.tritiumgaming.shared.data.newsletter.usecase

class FetchNewsletterInboxesUseCase(
    private val repository: com.tritiumgaming.shared.data.newsletter.repository.NewsletterRepository
) {
    suspend operator fun invoke(): Result<List<com.tritiumgaming.shared.data.newsletter.model.NewsletterInbox>> {
        val result = repository.fetchInboxes()

        result.exceptionOrNull()?.let { e ->
            throw Exception("Failed to fetch inboxes.", e)
        }

        return result
    }
}