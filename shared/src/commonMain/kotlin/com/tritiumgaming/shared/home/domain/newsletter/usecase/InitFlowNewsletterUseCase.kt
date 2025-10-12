package com.tritiumgaming.shared.home.domain.newsletter.usecase

import com.tritiumgaming.shared.home.domain.newsletter.repository.NewsletterRepository
import com.tritiumgaming.shared.home.domain.newsletter.source.NewsletterDatastore.NewsletterPreferences

class InitFlowNewsletterUseCase(
    private val repository: NewsletterRepository
) {
    suspend operator fun invoke(onUpdate: (NewsletterPreferences) -> Unit) =
        repository.initDatastoreFlow(onUpdate)
}