package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.MarketTypographyDatastore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.io.IOException

class MarketTypographyDatastoreDataSource(
    context: Context,
    private val dataStore: DataStore<Preferences>
): MarketTypographyDatastore {

    override val flow: Flow<MarketTypographyDatastore.TypographyPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) { emit(emptyPreferences()) }
            else { throw exception }
        }
        .map { preferences ->
            mapPreferences(preferences)
        }

    init {
        MarketTypographyDatastore.PreferencesKeys.KEY_TYPOGRAPHY = stringPreferencesKey(
            context.resources.getString(R.string.preference_savedFont)
        )
    }

    override suspend fun saveTypography(uuid: String) {
        dataStore.edit { preferences ->
            preferences[MarketTypographyDatastore.PreferencesKeys.KEY_TYPOGRAPHY] = uuid
        }
    }

    override suspend fun fetchInitialPreferences() =
        mapPreferences(dataStore.data.first().toPreferences())

    override fun mapPreferences(preferences: Preferences): MarketTypographyDatastore.TypographyPreferences {
        return MarketTypographyDatastore.TypographyPreferences(
            preferences[MarketTypographyDatastore.PreferencesKeys.KEY_TYPOGRAPHY] ?: ""
        )
    }

}