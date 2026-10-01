package com.tritiumgaming.data.newsletter.usecase

import com.tritiumgaming.data.newsletter.repository.NewsletterRepository

class GetFlowNewsletterDatastoreUseCase(
    private val repository: NewsletterRepository
) {
    operator fun invoke() = repository.initDatastoreFlow()
}