package com.tritiumgaming.data.marketplace.typography.source.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.liveData
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.core.domain.market.typography.source.TypographyDatastore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class MarketTypographyDatastoreDataSource(
    context: Context,
    private val dataStore: DataStore<Preferences>
): TypographyDatastore {

    val flow: Flow<TypographyDatastore.TypographyPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) { emit(emptyPreferences()) }
            else { throw exception }
        }
        .map { preferences ->
            mapPreferences(preferences)
        }

    init {
        KEY_TYPOGRAPHY = stringPreferencesKey(
            context.resources.getString(R.string.preference_savedFont)
        )
    }

    override suspend fun saveTypography(uuid: String) {
        dataStore.edit { preferences ->
            preferences[KEY_TYPOGRAPHY] = uuid
        }
    }

    override fun initializeDatastoreLiveData() {
        liveData { emit(fetchDatastoreInitialPreferences()) }
    }

    override fun initDatastoreFlow(): Flow<TypographyDatastore.TypographyPreferences> {
        return flow
    }

    override suspend fun fetchDatastoreInitialPreferences() =
        mapPreferences(dataStore.data.first().toPreferences())

    private fun mapPreferences(preferences: Preferences): TypographyDatastore.TypographyPreferences {
        return TypographyDatastore.TypographyPreferences(
            preferences[KEY_TYPOGRAPHY] ?: ""
        )
    }

    companion object PreferencesKeys {
        lateinit var KEY_TYPOGRAPHY: Preferences.Key<String>
    }

}