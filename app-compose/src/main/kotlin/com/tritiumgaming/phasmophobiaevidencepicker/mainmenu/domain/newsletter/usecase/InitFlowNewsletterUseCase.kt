package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.repository.NewsletterRepository
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source.NewsletterDatastore
import kotlinx.coroutines.flow.Flow

class InitFlowNewsletterUseCase(
    private val repository: NewsletterRepository
) {
    suspend operator fun invoke(): Flow<NewsletterDatastore.NewsletterPreferences> =
        repository.initFlow()
}