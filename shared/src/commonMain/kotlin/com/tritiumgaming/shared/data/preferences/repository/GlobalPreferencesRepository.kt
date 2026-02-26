package com.tritiumgaming.shared.data.preferences.repository

import com.tritiumgaming.shared.data.datastore.DatastoreRepository
import com.tritiumgaming.shared.data.preferences.source.GlobalPreferencesDatastore

interface GlobalPreferencesRepository:
    DatastoreRepository<GlobalPreferencesDatastore.GlobalPreferences> {

    suspend fun setDisableScreenSaver(disable: Boolean)

    suspend fun setAllowCellularData(allow: Boolean)

    suspend fun setEnableRTL(enable: Boolean)

    suspend fun setEnableGhostReorder(enable: Boolean)

    suspend fun setAllowIntroduction(allow: Boolean)

    suspend fun setMaxHuntWarnFlashTime(maxTime: Long)

    suspend fun setAllowHuntWarnAudio(allowed: Boolean)

    suspend fun savePalette(uuid: String)

    suspend fun saveTypography(uuid: String)

}