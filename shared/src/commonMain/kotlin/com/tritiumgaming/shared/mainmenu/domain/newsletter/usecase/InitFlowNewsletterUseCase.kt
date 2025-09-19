package com.tritiumgaming.shared.mainmenu.domain.newsletter.usecase

import com.tritiumgaming.shared.mainmenu.domain.newsletter.repository.NewsletterRepository
import com.tritiumgaming.shared.mainmenu.domain.newsletter.source.NewsletterDatastore.NewsletterPreferences

class InitFlowNewsletterUseCase(
    private val repository: NewsletterRepository
) {
    suspend operator fun invoke(onUpdate: (NewsletterPreferences) -> Unit) =
        repository.initDatastoreFlow(onUpdate)
}