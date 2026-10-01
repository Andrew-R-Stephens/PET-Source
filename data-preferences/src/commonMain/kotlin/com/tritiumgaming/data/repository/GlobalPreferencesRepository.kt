package com.tritiumgaming.data.repository

import com.tritiumgaming.core.common.datastore.DatastoreRepository
import com.tritiumgaming.data.model.properties.DensityType
import com.tritiumgaming.data.source.GlobalPreferencesDatastore

interface GlobalPreferencesRepository:
    DatastoreRepository<GlobalPreferencesDatastore.GlobalPreferences> {

    suspend fun setDisableScreenSaver(disable: Boolean)

    suspend fun setAllowCellularData(allow: Boolean)

    suspend fun setEnableRTL(enable: Boolean)

    suspend fun setUiDensityType(densityType: DensityType)

    suspend fun setEnableGhostReorder(enable: Boolean)

    suspend fun setAllowIntroduction(allow: Boolean)

    suspend fun setMaxHuntWarnFlashTime(maxTime: Long)

    suspend fun setAllowHuntWarnAudio(allowed: Boolean)

    suspend fun savePalette(uuid: String)

    suspend fun saveTypography(uuid: String)

}