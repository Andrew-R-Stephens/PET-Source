package com.tritiumgaming.data.newsletter.source

import com.tritiumgaming.core.common.datastore.DatastoreDataSource
import com.tritiumgaming.data.newsletter.source.NewsletterDatastore.NewsletterPreferences

interface NewsletterDatastore: DatastoreDataSource<NewsletterPreferences> {

    suspend fun setLastReadDate(id: String, date: Long)

    data class NewsletterPreferences(
        val data: Map<String, Long>
    )

}