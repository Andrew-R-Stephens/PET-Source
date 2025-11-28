package com.tritiumgaming.shared.data.newsletter.usecase

class SaveNewsletterInboxLastReadDateUseCase(
    private val repository: com.tritiumgaming.shared.data.newsletter.repository.NewsletterRepository
) {
    suspend operator fun invoke(inboxId: String, date: Long) {
        repository.saveInboxLastReadDate(inboxId, date)
    }
}