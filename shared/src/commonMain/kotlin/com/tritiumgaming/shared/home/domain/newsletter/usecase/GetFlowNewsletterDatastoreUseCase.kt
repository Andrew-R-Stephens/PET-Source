package com.tritiumgaming.shared.home.domain.newsletter.usecase

import com.tritiumgaming.shared.home.domain.newsletter.repository.NewsletterRepository

class GetFlowNewsletterDatastoreUseCase(
    private val repository: NewsletterRepository
) {
    operator fun invoke() = repository.initDatastoreFlow()
}