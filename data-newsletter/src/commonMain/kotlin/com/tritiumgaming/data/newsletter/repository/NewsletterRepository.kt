package com.tritiumgaming.data.newsletter.repository

import com.tritiumgaming.core.common.datastore.DatastoreRepository
import com.tritiumgaming.data.newsletter.model.NewsletterInbox
import com.tritiumgaming.data.newsletter.source.NewsletterDatastore.NewsletterPreferences
import kotlinx.coroutines.flow.Flow

interface NewsletterRepository: DatastoreRepository<NewsletterPreferences> {

    suspend fun saveInboxLastReadDate(id: String, date: Long)

    suspend fun fetchInboxes(
        forceRefresh: Boolean = false,
        onRefreshFailure: () -> Unit = {}
    ): Result<List<NewsletterInbox>>

    fun getInboxFlow(): Flow<List<NewsletterInbox>>

    fun getLastFetchDateFlow(): Flow<Long>

}