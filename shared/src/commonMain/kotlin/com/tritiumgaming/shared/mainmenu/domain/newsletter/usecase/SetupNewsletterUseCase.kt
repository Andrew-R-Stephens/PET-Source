package com.tritiumgaming.shared.mainmenu.domain.newsletter.usecase

import com.tritiumgaming.shared.mainmenu.domain.newsletter.repository.NewsletterRepository

class SetupNewsletterUseCase(
    private val repository: NewsletterRepository
) {
    operator fun invoke() = repository.initializeDatastoreLiveData()
}

    