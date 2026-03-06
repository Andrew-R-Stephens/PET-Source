package com.tritiumgaming.shared.data.newsletter.usecase

import com.tritiumgaming.shared.data.newsletter.repository.NewsletterRepository

class GetFlowNewsletterInboxesUseCase(
    private val repository: NewsletterRepository
) {
    operator fun invoke() = repository.getInboxFlow()
}