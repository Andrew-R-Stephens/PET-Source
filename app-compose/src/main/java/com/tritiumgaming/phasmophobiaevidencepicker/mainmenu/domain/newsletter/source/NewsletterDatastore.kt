package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source

import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.datastore.DatastoreInterface
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source.NewsletterDatastore.NewsletterPreferences

interface NewsletterDatastore: DatastoreInterface<NewsletterPreferences> {

    suspend fun setLastReadDate(key: Preferences.Key<Long>, date: Long)
    suspend fun setLastReadDate(id: String, date: Long)

    data class NewsletterPreferences(
        val data: Map<String, Long>
    )

    /*data class NewsletterPreferences(
        val lastReadDateGeneral: Long,
        val lastReadDatePET: Long,
        val lastReadDatePhasmophobia: Long
    )

    companion object PferenceKeys {

        lateinit var KEY_INBOX_GENERAL: Preferences.Key<Long>
        lateinit var KEY_INBOX_PET: Preferences.Key<Long>
        lateinit var KEY_INBOX_PHASMOPHOBIA: Preferences.Key<Long>

        const val DATE_DEFAULT = 0L
    }*/

}