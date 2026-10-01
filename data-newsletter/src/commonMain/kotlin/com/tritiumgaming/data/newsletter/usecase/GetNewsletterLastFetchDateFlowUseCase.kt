package com.tritiumgaming.data.newsletter.usecase

import com.tritiumgaming.data.newsletter.repository.NewsletterRepository

class GetNewsletterLastFetchDateFlowUseCase(
    private val repository: NewsletterRepository
) {
    operator fun invoke() = repository.getLastFetchDateFlow()
}