package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.repository.NewsletterRepository
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source.NewsletterDatastore.NewsletterPreferences

class InitFlowNewsletterUseCase(
    private val repository: NewsletterRepository
) {
    suspend operator fun invoke(onUpdate: (NewsletterPreferences) -> Unit) =
        repository.initFlow(onUpdate)
}