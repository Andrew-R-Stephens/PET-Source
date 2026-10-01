package com.tritiumgaming.data.newsletter.usecase

import com.tritiumgaming.data.newsletter.repository.NewsletterRepository

class SaveNewsletterInboxLastReadDateUseCase(
    private val repository: NewsletterRepository
) {
    suspend operator fun invoke(inboxId: String, date: Long) {
        repository.saveInboxLastReadDate(inboxId, date)
    }
}