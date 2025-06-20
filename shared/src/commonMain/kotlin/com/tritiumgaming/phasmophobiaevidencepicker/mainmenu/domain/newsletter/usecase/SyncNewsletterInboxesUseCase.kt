package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.repository.NewsletterRepository

class SyncNewsletterInboxesUseCase(
    private val repository: NewsletterRepository
) {
    suspend operator fun invoke(forceUpdate: Boolean = false) {

        val result = repository.synchronizeInboxes(forceUpdate)
        result.exceptionOrNull()?.printStackTrace()

    }
}