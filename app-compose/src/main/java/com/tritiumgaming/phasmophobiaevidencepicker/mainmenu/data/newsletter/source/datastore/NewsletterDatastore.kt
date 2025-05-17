package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.lifecycle.liveData
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source.NewsletterDatastore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class NewsletterDatastore(
    context: Context,
    private val dataStore: DataStore<Preferences>
): NewsletterDatastore {

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

    override fun initialSetupEvent() {
        liveData {
            emit(fetchInitialPreferences())
        }
    }

    init {
        NewsletterDatastore.PreferenceKeys.KEY_INBOX_GENERAL =
            longPreferencesKey(context.getString(R.string.preference_newsletter_lastreaddate_general))
        NewsletterDatastore.PreferenceKeys.KEY_INBOX_PET =
            longPreferencesKey(context.getString(R.string.preference_newsletter_lastreaddate_pet))
        NewsletterDatastore.PreferenceKeys.KEY_INBOX_PHASMOPHOBIA =
            longPreferencesKey(context.getString(R.string.preference_newsletter_lastreaddate_phas))
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
            preferences[NewsletterDatastore.PreferenceKeys.KEY_INBOX_GENERAL]
                ?: NewsletterDatastore.PreferenceKeys.DATE_DEFAULT,
            preferences[NewsletterDatastore.PreferenceKeys.KEY_INBOX_PET]
                ?: NewsletterDatastore.PreferenceKeys.DATE_DEFAULT,
            preferences[NewsletterDatastore.PreferenceKeys.KEY_INBOX_PHASMOPHOBIA]
                ?: NewsletterDatastore.PreferenceKeys.DATE_DEFAULT
        )
    }

}