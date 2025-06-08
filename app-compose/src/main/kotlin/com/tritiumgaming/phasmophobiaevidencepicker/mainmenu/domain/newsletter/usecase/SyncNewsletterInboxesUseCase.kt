package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.usecase

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.repository.NewsletterRepository

class SyncNewsletterInboxesUseCase(
    private val repository: NewsletterRepository
) {
    suspend operator fun invoke(forceUpdate: Boolean = false) {
        Log.d("NewsletterRepository", "Fetching Newsletter")

        val result = repository.synchronizeInboxes(forceUpdate)
        result.exceptionOrNull()?.printStackTrace()

        val wasUpdated = result.getOrDefault(false)

        Log.d("NewsletterRepository", "Newsletter fetch finished. Completed sync: $wasUpdated")
    }
}