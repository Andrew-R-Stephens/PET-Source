package com.tritiumgaming.shared.home.domain.newsletter.usecase

import com.tritiumgaming.shared.home.domain.newsletter.repository.NewsletterRepository

class SetupNewsletterUseCase(
    private val repository: NewsletterRepository
) {
    operator fun invoke() = repository.initializeDatastoreLiveData()
}

    