package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.usecase

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterInbox
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.repository.NewsletterRepository

class FetchNewsletterInboxesUseCase(
    private val repository: NewsletterRepository
) {
    operator fun invoke(): List<NewsletterInbox> =
        repository.getInboxes()
}
