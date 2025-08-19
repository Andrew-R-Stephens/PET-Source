package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterInbox
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.repository.NewsletterRepository

class FetchNewsletterInboxesUseCase(
    private val repository: NewsletterRepository
) {
    suspend operator fun invoke(): Result<List<NewsletterInbox>> {

        val result = repository.fetchInboxes()

        result.exceptionOrNull()?.let { e ->
            throw Exception("Failed to fetch inboxes.", e)}

        return result
    }
}