package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.liveData
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.datastore.TypographyDatastore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.io.IOException

class TypographyDatastore(
    context: Context,
    private val dataStore: DataStore<Preferences>
): TypographyDatastore {

    override val flow: Flow<TypographyDatastore.TypographyPreferences> = dataStore.data
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
        TypographyDatastore.PreferencesKeys.KEY_TYPOGRAPHY = stringPreferencesKey(
            context.resources.getString(R.string.preference_savedFont)
        )
    }

    override suspend fun saveTypography(uuid: String) {
        dataStore.edit { preferences ->
            preferences[TypographyDatastore.PreferencesKeys.KEY_TYPOGRAPHY] = uuid
        }
    }

    override suspend fun fetchInitialPreferences() =
        mapPreferences(dataStore.data.first().toPreferences())

    override fun mapPreferences(preferences: Preferences): TypographyDatastore.TypographyPreferences {
        return TypographyDatastore.TypographyPreferences(
            preferences[TypographyDatastore.PreferencesKeys.KEY_TYPOGRAPHY] ?: ""
        )
    }

}