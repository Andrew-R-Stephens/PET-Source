package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.repository.NewsletterRepository

class SetupNewsletterUseCase(
    private val repository: NewsletterRepository
) {
    operator fun invoke() = repository.initialSetupEvent()
}

    