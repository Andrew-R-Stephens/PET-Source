package com.tritiumgaming.shared.mainmenu.domain.newsletter.source

import com.tritiumgaming.shared.core.domain.datastore.DatastoreDataSource
import com.tritiumgaming.shared.mainmenu.domain.newsletter.source.NewsletterDatastore.NewsletterPreferences

interface NewsletterDatastore: DatastoreDataSource<NewsletterPreferences> {

    suspend fun setLastReadDate(id: String, date: Long)

    data class NewsletterPreferences(
        val data: Map<String, Long>
    )

}