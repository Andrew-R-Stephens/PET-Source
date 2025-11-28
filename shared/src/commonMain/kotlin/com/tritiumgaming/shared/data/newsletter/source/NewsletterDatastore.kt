package com.tritiumgaming.shared.data.newsletter.source

import com.tritiumgaming.shared.data.datastore.DatastoreDataSource

interface NewsletterDatastore: DatastoreDataSource<NewsletterDatastore.NewsletterPreferences> {

    suspend fun setLastReadDate(id: String, date: Long)

    data class NewsletterPreferences(
        val data: Map<String, Long>
    )

}