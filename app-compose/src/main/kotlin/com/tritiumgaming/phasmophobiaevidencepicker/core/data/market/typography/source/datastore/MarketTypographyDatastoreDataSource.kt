package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.liveData
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source.PaletteDatastore
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

    val flow: Flow<MarketTypographyDatastore.TypographyPreferences> = dataStore.data
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

    fun initialSetupEvent() = liveData { emit(fetchInitialPreferences()) }

    override suspend fun initFlow(onUpdate: (MarketTypographyDatastore.TypographyPreferences) -> Unit) =
        flow.collect { onUpdate(it) }

    override suspend fun fetchInitialPreferences() =
        mapPreferences(dataStore.data.first().toPreferences())

    private fun mapPreferences(preferences: Preferences): MarketTypographyDatastore.TypographyPreferences {
        return MarketTypographyDatastore.TypographyPreferences(
            preferences[MarketTypographyDatastore.PreferencesKeys.KEY_TYPOGRAPHY] ?: ""
        )
    }

}