package com.tritiumgaming.shared.mainmenu.domain.newsletter.usecase

import com.tritiumgaming.shared.mainmenu.domain.newsletter.repository.NewsletterRepository

class SaveNewsletterInboxLastReadDateUseCase(
    private val repository: NewsletterRepository
) {
    suspend operator fun invoke(inboxId: String, date: Long) {
        repository.saveInboxLastReadDate(inboxId, date)
    }
}