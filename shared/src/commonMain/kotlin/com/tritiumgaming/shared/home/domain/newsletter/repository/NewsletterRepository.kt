package com.tritiumgaming.shared.home.domain.newsletter.repository

import com.tritiumgaming.shared.core.domain.datastore.DatastoreRepository
import com.tritiumgaming.shared.home.domain.newsletter.model.NewsletterInbox
import com.tritiumgaming.shared.home.domain.newsletter.source.NewsletterDatastore

interface NewsletterRepository: DatastoreRepository<NewsletterDatastore.NewsletterPreferences> {

    suspend fun saveInboxLastReadDate(id: String, date: Long)

    suspend fun fetchInboxes(): Result<List<NewsletterInbox>>

}