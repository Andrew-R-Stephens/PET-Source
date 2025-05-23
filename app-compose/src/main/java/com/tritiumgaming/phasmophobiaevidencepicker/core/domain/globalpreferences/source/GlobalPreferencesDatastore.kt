package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.source

import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.datastore.DatastoreInterface
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.source.GlobalPreferencesDatastore.GlobalPreferences

interface GlobalPreferencesDatastore: DatastoreInterface<GlobalPreferences> {

    suspend fun setDisableScreenSaver(disable: Boolean)
    fun getDisableScreenSaver(): Boolean
    suspend fun setAllowCellularData(allow: Boolean)
    fun getAllowCellularData(): Boolean
    suspend fun setEnableRTL(enable: Boolean)
    fun getEnableRTL(): Boolean
    suspend fun setEnableGhostReorder(enable: Boolean)
    fun getEnableGhostReorder(): Boolean
    suspend fun setAllowIntroduction(allow: Boolean)
    fun getAllowIntroduction(): Boolean
    suspend fun setMaxHuntWarnFlashTime(maxTime: Long)
    fun getMaxHuntWarnFlashTime(): Long
    suspend fun setAllowHuntWarnAudio(allowed: Boolean)
    fun getAllowHuntWarnAudio(): Boolean

    data class GlobalPreferences(
        val disableScreenSaver: Boolean,
        val allowCellularData: Boolean,
        val allowHuntWarnAudio: Boolean,
        val enableGhostReorder: Boolean,
        val allowIntroduction: Boolean,
        val enableRTL: Boolean,
        val maxHuntWarnFlashTime: Long
    )

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