package com.tritiumgaming.shared.data.newsletter.usecase

import com.tritiumgaming.shared.data.newsletter.repository.NewsletterRepository

class GetNewsletterLastFetchDateFlowUseCase(
    private val repository: NewsletterRepository
) {
    operator fun invoke() = repository.getLastFetchDateFlow()
}