package com.tritiumgaming.data.source

import com.tritiumgaming.core.common.datastore.DatastoreDataSource
import com.tritiumgaming.data.model.properties.DensityType
import com.tritiumgaming.data.palette.mappers.PaletteResources
import com.tritiumgaming.data.palette.mappers.asUuid
import com.tritiumgaming.data.source.GlobalPreferencesDatastore.GlobalPreferences
import com.tritiumgaming.data.typography.mappers.TypographyResources
import com.tritiumgaming.data.typography.mappers.asUuid

interface GlobalPreferencesDatastore:
    DatastoreDataSource<GlobalPreferences> {

    suspend fun setDisableScreenSaver(disable: Boolean)
    suspend fun setAllowCellularData(allow: Boolean)
    suspend fun setEnableRTL(enable: Boolean)
    suspend fun setEnableGhostReorder(enable: Boolean)
    suspend fun setAllowIntroduction(allow: Boolean)
    suspend fun setMaxHuntWarnFlashTime(maxTime: Long)
    suspend fun setAllowHuntWarnAudio(allowed: Boolean)
    suspend fun setUiDensityType(densityType: DensityType)
    suspend fun savePalette(uuid: String)
    suspend fun saveTypography(uuid: String)

    data class GlobalPreferences(
        val disableScreenSaver: Boolean = true,
        val allowCellularData: Boolean = true,
        val allowHuntWarnAudio: Boolean = true,
        val enableGhostReorder: Boolean = true,
        val allowIntroduction: Boolean = true,
        val enableRTL: Boolean = false,
        val uiDensity: DensityType = DensityType.COMFORTABLE,
        val maxHuntWarnFlashTime: Long = 300L,
        val uiDensityType: DensityType = DensityType.COMFORTABLE,
        val typographyUuid: String = TypographyResources.TypographyType.CLASSIC.asUuid(),
        val paletteUuid: String = PaletteResources.PaletteType.CLASSIC.asUuid()
    )

}