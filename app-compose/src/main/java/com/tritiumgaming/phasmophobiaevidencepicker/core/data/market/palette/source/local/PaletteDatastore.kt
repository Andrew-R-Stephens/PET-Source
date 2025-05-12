package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.liveData
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.datastore.PaletteDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.datastore.PaletteDatastore.ColorPreferences
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.datastore.PaletteDatastore.PreferenceKeys.KEY_PALETTE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PaletteDatastore(
    context: Context,
    private val dataStore: DataStore<Preferences>
): PaletteDatastore {

    override val flow: Flow<ColorPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) { emit(emptyPreferences()) }
            else { throw exception }
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
        KEY_PALETTE = stringPreferencesKey(
            context.resources.getString(R.string.preference_savedTheme)
        )
    }

    override suspend fun savePalette(uuid: String) {
        dataStore.edit { preferences ->
            preferences[KEY_PALETTE] = uuid
        }
    }

    override suspend fun fetchInitialPreferences() =
        mapPreferences(dataStore.data.first().toPreferences())

    override fun mapPreferences(preferences: Preferences): ColorPreferences {
        return ColorPreferences(
            preferences[KEY_PALETTE] ?: ""
        )
    }

}
