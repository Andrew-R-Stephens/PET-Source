package com.tritiumgaming.shared.data.preferences.source

import com.tritiumgaming.shared.data.preferences.source.GlobalPreferencesDatastore.GlobalPreferences

interface GlobalPreferencesDatastore:
    com.tritiumgaming.shared.data.datastore.DatastoreDataSource<GlobalPreferences> {

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
    suspend fun savePalette(uuid: String)
    fun getPalette(): String
    suspend fun saveTypography(uuid: String)
    fun getTypography(): String

    data class GlobalPreferences(
        val disableScreenSaver: Boolean = true,
        val allowCellularData: Boolean = true,
        val allowHuntWarnAudio: Boolean = true,
        val enableGhostReorder: Boolean = true,
        val allowIntroduction: Boolean = true,
        val enableRTL: Boolean = false,
        val maxHuntWarnFlashTime: Long = 300L,
        val typographyUuid: String,
        val paletteUuid: String
    )

}