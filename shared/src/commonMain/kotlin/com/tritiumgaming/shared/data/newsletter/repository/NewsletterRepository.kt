package com.tritiumgaming.shared.data.newsletter.repository

import com.tritiumgaming.shared.data.datastore.DatastoreRepository
import com.tritiumgaming.shared.data.newsletter.model.NewsletterInbox
import com.tritiumgaming.shared.data.newsletter.source.NewsletterDatastore
import kotlinx.coroutines.flow.Flow

interface NewsletterRepository: DatastoreRepository<NewsletterDatastore.NewsletterPreferences> {

    suspend fun saveInboxLastReadDate(id: String, date: Long)

    suspend fun fetchInboxes(
        forceRefresh: Boolean = false,
        onRefreshFailure: () -> Unit = {}
    ): Result<List<NewsletterInbox>>

    fun getInboxFlow(): Flow<List<NewsletterInbox>>

}