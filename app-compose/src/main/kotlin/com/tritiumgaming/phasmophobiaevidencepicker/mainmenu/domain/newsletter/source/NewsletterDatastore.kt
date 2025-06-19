package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source

import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.datastore.DatastoreInterface
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source.NewsletterDatastore.NewsletterPreferences

interface NewsletterDatastore: DatastoreInterface<NewsletterPreferences> {

    suspend fun setLastReadDate(key: Preferences.Key<Long>, date: Long)
    suspend fun setLastReadDate(id: String, date: Long)

    data class NewsletterPreferences(
        val data: Map<String, Long>
    )

}