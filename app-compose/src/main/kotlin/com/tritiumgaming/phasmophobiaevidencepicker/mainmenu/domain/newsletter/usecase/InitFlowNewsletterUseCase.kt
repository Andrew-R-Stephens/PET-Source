package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDatastore.LanguagePreferences
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.NewsletterDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.NewsletterDatastore.NewsletterPreferences
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.repository.NewsletterRepository
import kotlinx.coroutines.flow.Flow

class InitFlowNewsletterUseCase(
    private val repository: NewsletterRepository
) {
    suspend operator fun invoke(onUpdate: (NewsletterPreferences) -> Unit) =
        repository.initFlow(onUpdate)
}