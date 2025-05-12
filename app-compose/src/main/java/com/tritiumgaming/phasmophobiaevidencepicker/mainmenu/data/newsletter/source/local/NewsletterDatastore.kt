package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.lifecycle.liveData
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.datastore.DatastoreInterface
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local.NewsletterDatastoreInterface.NewsletterPreferences
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local.NewsletterDatastoreInterface.PreferenceKeys.DATE_DEFAULT
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local.NewsletterDatastoreInterface.PreferenceKeys.KEY_INBOX_GENERAL
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local.NewsletterDatastoreInterface.PreferenceKeys.KEY_INBOX_PET
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local.NewsletterDatastoreInterface.PreferenceKeys.KEY_INBOX_PHASMOPHOBIA
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class NewsletterDatastore(
    context: Context,
    private val dataStore: DataStore<Preferences>
): NewsletterDatastoreInterface {

    override val flow: Flow<NewsletterPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            mapPreferences(preferences)
        }

    override fun initialSetupEvent() {
        liveData {
            emit(fetchInitialPreferences())
        }
    }

    init {
        KEY_INBOX_GENERAL =
            longPreferencesKey(context.getString(R.string.preference_newsletter_lastreaddate_general))
        KEY_INBOX_PET =
            longPreferencesKey(context.getString(R.string.preference_newsletter_lastreaddate_pet))
        KEY_INBOX_PHASMOPHOBIA =
            longPreferencesKey(context.getString(R.string.preference_newsletter_lastreaddate_phas))
    }

    override suspend fun setLastReadDate(key: Preferences.Key<Long>, date: Long) {
        dataStore.edit { preferences ->
            preferences[key] = date
        }
    }

    override suspend fun fetchInitialPreferences() =
        mapPreferences(dataStore.data.first().toPreferences())

    override fun mapPreferences(preferences: Preferences): NewsletterPreferences {
        return NewsletterPreferences(
            preferences[KEY_INBOX_GENERAL] ?: DATE_DEFAULT,
            preferences[KEY_INBOX_PET] ?: DATE_DEFAULT,
            preferences[KEY_INBOX_PHASMOPHOBIA] ?: DATE_DEFAULT
        )
    }

}

interface NewsletterDatastoreInterface: DatastoreInterface<NewsletterPreferences> {
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