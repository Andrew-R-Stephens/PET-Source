package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.datastore

import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.datastore.GlobalPreferencesDatastore.GlobalPreferences
import kotlinx.coroutines.flow.Flow

interface GlobalPreferencesDatastore: DatastoreInterface<GlobalPreferences> {
    val flow: Flow<GlobalPreferences>

    data class GlobalPreferences(
        val disableScreenSaver: Boolean,
        val allowCellularData: Boolean,
        val allowHuntWarnAudio: Boolean,
        val enableGhostReorder: Boolean,
        val allowIntroduction: Boolean,
        val enableRTL: Boolean,
        val maxHuntWarnFlashTime: Long
    )

    companion object {
        lateinit var KEY_DISABLE_SCREENSAVER: Preferences.Key<Boolean>
        lateinit var KEY_ALLOW_CELLULAR_DATA: Preferences.Key<Boolean>
        lateinit var KEY_ALLOW_HUNT_WARN_AUDIO: Preferences.Key<Boolean>
        lateinit var KEY_ENABLE_GHOST_REORDER: Preferences.Key<Boolean>
        lateinit var KEY_ALLOW_INTRODUCTION: Preferences.Key<Boolean>

        lateinit var KEY_ENABLE_RTL: Preferences.Key<Boolean>

        lateinit var KEY_HUNT_WARN_MAX_TIMEOUT: Preferences.Key<Long>
    }

}