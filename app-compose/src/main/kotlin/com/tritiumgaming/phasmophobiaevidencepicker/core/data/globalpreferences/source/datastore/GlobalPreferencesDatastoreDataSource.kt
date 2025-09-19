package com.tritiumgaming.phasmophobiaevidencepicker.core.data.globalpreferences.source.datastore

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
import com.tritiumgaming.shared.core.domain.globalpreferences.source.GlobalPreferencesDatastore
import com.tritiumgaming.shared.core.domain.globalpreferences.source.GlobalPreferencesDatastore.GlobalPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class GlobalPreferencesDatastoreDataSource(
    context: Context,
    private val dataStore: DataStore<Preferences>
): GlobalPreferencesDatastore {

    val flow: Flow<GlobalPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) { emit(emptyPreferences()) }
            else { throw exception }
        }
        .map { preferences ->
            mapPreferences(preferences)
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
    override suspend fun setDisableScreenSaver(disable: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_DISABLE_SCREENSAVER] = disable
        }
    }
    override fun getDisableScreenSaver(): Boolean {
        var disabled = false
        dataStore.data.map { preferences ->
            preferences[KEY_DISABLE_SCREENSAVER]?.let { disabled = it }
        }
        return disabled
    }

    override suspend fun setAllowCellularData(allow: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_ALLOW_CELLULAR_DATA] = allow
        }
    }
    override fun getAllowCellularData(): Boolean {
        var allowed = false
        dataStore.data.map { preferences ->
            preferences[KEY_ALLOW_CELLULAR_DATA]?.let { allowed = it }
        }
        return allowed
    }

    override suspend fun setEnableRTL(enable: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_ENABLE_RTL] = enable
        }
    }
    override fun getEnableRTL(): Boolean {
        var enabled = false
        dataStore.data.map { preferences ->
            preferences[KEY_ENABLE_RTL]?.let { enabled = it }
        }
        return enabled
    }

    override suspend fun setEnableGhostReorder(enable: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_ENABLE_GHOST_REORDER] = enable
        }
    }
    override fun getEnableGhostReorder(): Boolean {
        var enabled = false
        dataStore.data.map { preferences ->
            preferences[KEY_ENABLE_GHOST_REORDER]?.let { enabled = it }
        }
        return enabled
    }

    override suspend fun setAllowIntroduction(allow: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_ALLOW_INTRODUCTION] = allow
        }
    }
    override fun getAllowIntroduction(): Boolean {
        var allowed = false
        dataStore.data.map { preferences ->
            preferences[KEY_ALLOW_INTRODUCTION]?.let { allowed = it }
        }
        return allowed
    }

    override suspend fun setMaxHuntWarnFlashTime(maxTime: Long) {
        dataStore.edit { preferences ->
            preferences[KEY_HUNT_WARN_MAX_TIMEOUT] = maxTime
        }
    }
    override fun getMaxHuntWarnFlashTime(): Long {
        var maxTime = 0L
        dataStore.data.map { preferences ->
            preferences[KEY_HUNT_WARN_MAX_TIMEOUT]?.let { maxTime = it }
        }
        return maxTime
    }

    override suspend fun setAllowHuntWarnAudio(allowed: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_ALLOW_HUNT_WARN_AUDIO] = allowed
        }
    }
    override fun getAllowHuntWarnAudio(): Boolean {
        var allowed = false
        dataStore.data.map { preferences ->
            preferences[KEY_ALLOW_HUNT_WARN_AUDIO]?.let { allowed = it }
        }
        return allowed
    }

    override fun initializeDatastoreLiveData() {
        liveData { emit(fetchDatastoreInitialPreferences()) }
    }

    override suspend fun initDatastoreFlow(onUpdate: (preferences: GlobalPreferences) -> Unit) {
        flow.collect { preferences ->
            onUpdate(preferences)
        }
    }

    override suspend fun fetchDatastoreInitialPreferences() =
        mapPreferences(dataStore.data.first().toPreferences())

    private fun mapPreferences(preferences: Preferences): GlobalPreferences {
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

    companion object PreferenceKeys {
        lateinit var KEY_DISABLE_SCREENSAVER: Preferences.Key<Boolean>
        lateinit var KEY_ALLOW_CELLULAR_DATA: Preferences.Key<Boolean>
        lateinit var KEY_ALLOW_HUNT_WARN_AUDIO: Preferences.Key<Boolean>
        lateinit var KEY_ENABLE_GHOST_REORDER: Preferences.Key<Boolean>
        lateinit var KEY_ALLOW_INTRODUCTION: Preferences.Key<Boolean>

        lateinit var KEY_ENABLE_RTL: Preferences.Key<Boolean>

        lateinit var KEY_HUNT_WARN_MAX_TIMEOUT: Preferences.Key<Long>
    }

}