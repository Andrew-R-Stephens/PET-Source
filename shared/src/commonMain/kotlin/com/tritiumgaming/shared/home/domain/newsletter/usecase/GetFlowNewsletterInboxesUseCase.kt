package com.tritiumgaming.shared.home.domain.newsletter.usecase

import com.tritiumgaming.shared.home.domain.newsletter.repository.NewsletterRepository
import com.tritiumgaming.shared.home.domain.newsletter.source.NewsletterDatastore.NewsletterPreferences

class GetFlowNewsletterInboxesUseCase(
    private val repository: NewsletterRepository
) {
    operator fun invoke() = repository.getInboxFlow()
}