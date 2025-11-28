package com.tritiumgaming.shared.data.newsletter.usecase

class GetFlowNewsletterDatastoreUseCase(
    private val repository: com.tritiumgaming.shared.data.newsletter.repository.NewsletterRepository
) {
    operator fun invoke() = repository.initDatastoreFlow()
}