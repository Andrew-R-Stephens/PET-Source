package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source.NewsletterDatastore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class NewsletterDatastoreDataSource(
    context: Context,
    private val dataStore: DataStore<Preferences>
): NewsletterDatastore {

    private val keyList: Map<String, Preferences.Key<Long>> = mapOf()

    override val flow: Flow<NewsletterDatastore.NewsletterPreferences> = dataStore.data
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

    init {
        keyList.apply {
            "1" to longPreferencesKey(context.getString(R.string.preference_newsletter_lastreaddate_general))
            "2" to longPreferencesKey(context.getString(R.string.preference_newsletter_lastreaddate_pet))
            "3" to longPreferencesKey(context.getString(R.string.preference_newsletter_lastreaddate_phas))
        }

    }

    override suspend fun setLastReadDate(id: String, date: Long) {
        val key = keyList[id]
        key?.let { it ->
            dataStore.edit { preferences ->
                preferences[it] = date
            }
        }
    }

    override suspend fun setLastReadDate(key: Preferences.Key<Long>, date: Long) {
        dataStore.edit { preferences ->
            preferences[key] = date
        }
    }

    override suspend fun fetchInitialPreferences() =
        mapPreferences(dataStore.data.first().toPreferences())

    override fun mapPreferences(preferences: Preferences): NewsletterDatastore.NewsletterPreferences {
        return NewsletterDatastore.NewsletterPreferences(
            data = keyList.mapValues { preferences[it.value] ?: 0L }
        )
    }

    /*override fun mapPreferences(preferences: Preferences): NewsletterDatastore.NewsletterPreferences {
        return NewsletterDatastore.NewsletterPreferences(
            preferences[KEY_INBOX_GENERAL] ?: NewsletterDatastore.PreferenceKeys.DATE_DEFAULT,
            preferences[KEY_INBOX_PET] ?: NewsletterDatastore.PreferenceKeys.DATE_DEFAULT,
            preferences[KEY_INBOX_PHASMOPHOBIA] ?: NewsletterDatastore.PreferenceKeys.DATE_DEFAULT
        )
    }*/

}