package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source

import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.datastore.DatastoreInterface
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source.NewsletterDatastore.NewsletterPreferences
import kotlinx.coroutines.flow.Flow

interface NewsletterDatastore: DatastoreInterface<NewsletterPreferences> {
    val flow: Flow<NewsletterPreferences>

    override fun initialSetupEvent()
    override suspend fun fetchInitialPreferences(): NewsletterPreferences
    override fun mapPreferences(preferences: Preferences): NewsletterPreferences

    suspend fun setLastReadDate(key: Preferences.Key<Long>, date: Long)

    data class NewsletterPreferences(
        val lastReadDateGeneral: Long,
        val lastReadDatePET: Long,
        val lastReadDatePhasmophobia: Long
    )

    companion object PreferenceKeys {

        lateinit var KEY_INBOX_GENERAL: Preferences.Key<Long>
        lateinit var KEY_INBOX_PET: Preferences.Key<Long>
        lateinit var KEY_INBOX_PHASMOPHOBIA: Preferences.Key<Long>

        const val DATE_DEFAULT = 0L
    }

}