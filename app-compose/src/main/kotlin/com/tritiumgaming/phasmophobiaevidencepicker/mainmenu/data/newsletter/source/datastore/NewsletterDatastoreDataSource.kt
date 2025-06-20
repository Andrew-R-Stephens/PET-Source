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

class NewsletterDatastoreDataSource(
    context: Context,
    private val dataStore: DataStore<Preferences>
): NewsletterDatastore {

    private val keyList: Map<String, Preferences.Key<Long>> = mapOf()

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

    override fun initialSetupEvent() {
        liveData { emit(fetchInitialPreferences()) }
    }

    override suspend fun initFlow(onUpdate: (NewsletterDatastore.NewsletterPreferences) -> Unit) =
        flow.collect { onUpdate(it) }

    override suspend fun fetchInitialPreferences() =
        mapPreferences(dataStore.data.first().toPreferences())

    fun mapPreferences(preferences: Preferences): NewsletterDatastore.NewsletterPreferences {
        return NewsletterDatastore.NewsletterPreferences(
            data = keyList.mapValues { preferences[it.value] ?: 0L }
        )
    }

}