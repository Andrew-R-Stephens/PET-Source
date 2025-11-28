package com.tritiumgaming.shared.data.newsletter.repository

import com.tritiumgaming.shared.data.datastore.DatastoreRepository
import com.tritiumgaming.shared.data.newsletter.source.NewsletterDatastore
import kotlinx.coroutines.flow.Flow

interface NewsletterRepository: DatastoreRepository<NewsletterDatastore.NewsletterPreferences> {

    suspend fun saveInboxLastReadDate(id: String, date: Long)

    suspend fun fetchInboxes(): Result<List<com.tritiumgaming.shared.data.newsletter.model.NewsletterInbox>>

    //suspend fun synchronizeInboxes(): Result<List<NewsletterInbox>>

    fun getInboxFlow(): Flow<List<com.tritiumgaming.shared.data.newsletter.model.NewsletterInbox>>

}