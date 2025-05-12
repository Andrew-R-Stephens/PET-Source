package com.tritiumgaming.phasmophobiaevidencepicker.core.data.globalpreferences.source.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.lifecycle.liveData
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.datastore.GlobalPreferencesDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.datastore.GlobalPreferencesDatastore.Companion.KEY_ALLOW_CELLULAR_DATA
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.datastore.GlobalPreferencesDatastore.Companion.KEY_ALLOW_HUNT_WARN_AUDIO
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.datastore.GlobalPreferencesDatastore.Companion.KEY_ALLOW_INTRODUCTION
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.datastore.GlobalPreferencesDatastore.Companion.KEY_DISABLE_SCREENSAVER
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.datastore.GlobalPreferencesDatastore.Companion.KEY_ENABLE_GHOST_REORDER
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.datastore.GlobalPreferencesDatastore.Companion.KEY_ENABLE_RTL
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.datastore.GlobalPreferencesDatastore.Companion.KEY_HUNT_WARN_MAX_TIMEOUT
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.datastore.GlobalPreferencesDatastore.GlobalPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class GlobalPreferencesDatastore(
    context: Context,
    private val dataStore: DataStore<Preferences>
): GlobalPreferencesDatastore {

    override val flow: Flow<GlobalPreferences> = dataStore.data
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
        Log.d("GlobalPreferences Repository", "Initializing")

        KEY_DISABLE_SCREENSAVER =
            booleanPreferencesKey(context.resources.getString(R.string.preference_isAlwaysOn))
        KEY_ALLOW_CELLULAR_DATA =
            booleanPreferencesKey(context.resources.getString(R.string.preference_network))
        KEY_ALLOW_HUNT_WARN_AUDIO =
            booleanPreferencesKey(context.resources.getString(R.string.preference_isHuntAudioWarningAllowed))
        KEY_ENABLE_GHOST_REORDER =
            booleanPreferencesKey(context.resources.getString(R.string.preference_enableReorderGhostViews))
        KEY_ALLOW_INTRODUCTION =
            booleanPreferencesKey(context.resources.getString(R.string.tutorialTracking_canShowIntroduction))

        KEY_ENABLE_RTL =
            booleanPreferencesKey(context.resources.getString(R.string.preference_isLeftHandSupportEnabled))

        KEY_HUNT_WARN_MAX_TIMEOUT =
            longPreferencesKey(context.resources.getString(R.string.preference_huntWarningFlashTimeout))
    }

    // Generic settings
    suspend fun setDisableScreenSaver(disable: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_DISABLE_SCREENSAVER] = disable
        }
    }

    suspend fun setAllowCellularData(allow: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_ALLOW_CELLULAR_DATA] = allow
        }
    }

    suspend fun setEnableRTL(enable: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_ENABLE_RTL] = enable
        }
    }

    suspend fun setEnableGhostReorder(enable: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_ENABLE_GHOST_REORDER] = enable
        }
    }

    suspend fun setAllowIntroduction(allow: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_ALLOW_INTRODUCTION] = allow
        }
    }

    suspend fun setMaxHuntWarnFlashTime(maxTime: Long) {
        dataStore.edit { preferences ->
            preferences[KEY_HUNT_WARN_MAX_TIMEOUT] = maxTime
        }
    }

    suspend fun setAllowHuntWarnAudio(allowed: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_ALLOW_HUNT_WARN_AUDIO] = allowed
        }
    }

    override suspend fun fetchInitialPreferences() =
        mapPreferences(dataStore.data.first().toPreferences())

    override fun mapPreferences(preferences: Preferences): GlobalPreferences {
        return GlobalPreferences(
            preferences[KEY_DISABLE_SCREENSAVER] == true,
            preferences[KEY_ALLOW_CELLULAR_DATA] != false,
            preferences[KEY_ALLOW_HUNT_WARN_AUDIO] != false,
            preferences[KEY_ENABLE_GHOST_REORDER] != false,
            preferences[KEY_ALLOW_INTRODUCTION] != false,
            preferences[KEY_ENABLE_RTL] == true,
            preferences[KEY_HUNT_WARN_MAX_TIMEOUT] ?: 300
        )
    }


}