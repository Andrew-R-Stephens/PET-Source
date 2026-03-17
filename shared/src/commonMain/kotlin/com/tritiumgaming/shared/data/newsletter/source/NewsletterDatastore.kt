package com.tritiumgaming.shared.data.newsletter.source

import com.tritiumgaming.shared.data.datastore.DatastoreDataSource
import com.tritiumgaming.shared.data.newsletter.source.NewsletterDatastore.NewsletterPreferences

interface NewsletterDatastore: DatastoreDataSource<NewsletterPreferences> {

    suspend fun setLastReadDate(id: String, date: Long)

    data class NewsletterPreferences(
        val data: Map<String, Long>
    )

}