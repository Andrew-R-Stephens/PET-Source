package com.tritiumgaming.shared.data.newsletter.usecase

import com.tritiumgaming.shared.data.newsletter.repository.NewsletterRepository

class GetFlowNewsletterDatastoreUseCase(
    private val repository: NewsletterRepository
) {
    operator fun invoke() = repository.initDatastoreFlow()
}