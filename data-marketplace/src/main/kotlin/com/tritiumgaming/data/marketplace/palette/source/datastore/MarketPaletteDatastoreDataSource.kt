package com.tritiumgaming.data.marketplace.palette.source.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.liveData
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.core.domain.market.palette.source.PaletteDatastore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class MarketPaletteDatastoreDataSource(
    context: Context,
    private val dataStore: DataStore<Preferences>
): PaletteDatastore {

    val flow: Flow<PaletteDatastore.PalettePreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) { emit(emptyPreferences()) }
            else { throw exception }
        }
        .map { preferences ->
            mapPreferences(preferences)
        }

    init {
        KEY_PALETTE = stringPreferencesKey(
            context.resources.getString(R.string.preference_savedTheme)
        )
    }

    override suspend fun savePalette(uuid: String) {
        dataStore.edit { preferences ->
            preferences[KEY_PALETTE] = uuid
        }
    }

    override fun initializeDatastoreLiveData() {
        liveData { emit(fetchDatastoreInitialPreferences()) }
    }

    override suspend fun initDatastoreFlow(onUpdate: (PaletteDatastore.PalettePreferences) -> Unit) =
        flow.collect { onUpdate(it) }

    override suspend fun fetchDatastoreInitialPreferences() =
        mapPreferences(dataStore.data.first().toPreferences())

    private fun mapPreferences(preferences: Preferences): PaletteDatastore.PalettePreferences {
        return PaletteDatastore.PalettePreferences(
            uuid = preferences[KEY_PALETTE] ?: ""
        )
    }

    companion object PreferenceKeys {
        lateinit var KEY_PALETTE: Preferences.Key<String>
    }

}