package com.tritiumgaming.shared.data.preferences.source

import com.tritiumgaming.shared.data.datastore.DatastoreDataSource
import com.tritiumgaming.shared.data.preferences.source.GlobalPreferencesDatastore.GlobalPreferences

interface GlobalPreferencesDatastore:
    DatastoreDataSource<GlobalPreferences> {

    suspend fun setDisableScreenSaver(disable: Boolean)
    suspend fun setAllowCellularData(allow: Boolean)
    suspend fun setEnableRTL(enable: Boolean)
    suspend fun setEnableGhostReorder(enable: Boolean)
    suspend fun setAllowIntroduction(allow: Boolean)
    suspend fun setMaxHuntWarnFlashTime(maxTime: Long)
    suspend fun setAllowHuntWarnAudio(allowed: Boolean)
    suspend fun savePalette(uuid: String)
    suspend fun saveTypography(uuid: String)

    data class GlobalPreferences(
        val disableScreenSaver: Boolean = true,
        val allowCellularData: Boolean = true,
        val allowHuntWarnAudio: Boolean = true,
        val enableGhostReorder: Boolean = true,
        val allowIntroduction: Boolean = true,
        val enableRTL: Boolean = false,
        val maxHuntWarnFlashTime: Long = 300L,
        val uiDensity: Int = 0,
        val typographyUuid: String,
        val paletteUuid: String
    )

}