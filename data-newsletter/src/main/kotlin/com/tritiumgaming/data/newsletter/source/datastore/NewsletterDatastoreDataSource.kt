package com.tritiumgaming.data.newsletter.source.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.data.newsletter.source.NewsletterDatastore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class NewsletterDatastoreDataSource(
    context: Context,
    private val dataStore: DataStore<Preferences>
): NewsletterDatastore {

    private val keyList: Map<String, Preferences.Key<Long>> = mapOf(
        context.getString(R.string.newsletter_inbox_id_general) to
                longPreferencesKey(context.getString(R.string.preference_newsletter_lastreaddate_general)),
        context.getString(R.string.newsletter_inbox_id_pet) to
                longPreferencesKey(context.getString(R.string.preference_newsletter_lastreaddate_pet)),
        context.getString(R.string.newsletter_inbox_id_phasmophobia) to
                longPreferencesKey(context.getString(R.string.preference_newsletter_lastreaddate_phas))
    )

    val flow: Flow<NewsletterDatastore.NewsletterPreferences> = dataStore.data
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

    override suspend fun setLastReadDate(id: String, date: Long) {
        val key = keyList[id]
        key?.let {
            dataStore.edit { preferences ->
                preferences[it] = date
            }
        }
    }

    override fun initDatastoreFlow(): Flow<NewsletterDatastore.NewsletterPreferences> = flow

    override suspend fun fetchDatastoreInitialPreferences() =
        mapPreferences(dataStore.data.first().toPreferences())

    fun mapPreferences(preferences: Preferences): NewsletterDatastore.NewsletterPreferences {
        return NewsletterDatastore.NewsletterPreferences(
            data = keyList.mapValues { preferences[it.value] ?: 0L }
        )
    }

}