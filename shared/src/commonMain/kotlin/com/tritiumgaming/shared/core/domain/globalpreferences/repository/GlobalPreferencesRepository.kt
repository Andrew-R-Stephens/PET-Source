package com.tritiumgaming.shared.core.domain.globalpreferences.repository

import com.tritiumgaming.shared.core.domain.datastore.DatastoreRepository
import com.tritiumgaming.shared.core.domain.globalpreferences.source.GlobalPreferencesDatastore.GlobalPreferences

interface GlobalPreferencesRepository: DatastoreRepository<GlobalPreferences> {

    suspend fun setDisableScreenSaver(disable: Boolean)
    fun getDisableScreenSaver(): Boolean
    suspend fun loadDisableScreenSaver()

    suspend fun setAllowCellularData(allow: Boolean)
    fun getAllowCellularData(): Boolean
    suspend fun loadAllowCellularData()

    suspend fun setEnableRTL(enable: Boolean)
    fun getEnableRTL(): Boolean
    suspend fun loadEnableRTL()

    suspend fun setEnableGhostReorder(enable: Boolean)
    fun getEnableGhostReorder(): Boolean
    suspend fun loadEnableGhostReorder()

    suspend fun setAllowIntroduction(allow: Boolean)
    fun getAllowIntroduction(): Boolean
    suspend fun loadAllowIntroduction()

    suspend fun setMaxHuntWarnFlashTime(maxTime: Long)
    fun getMaxHuntWarnFlashTime(): Long
    suspend fun loadMaxHuntWarnFlashTime()

    suspend fun setAllowHuntWarnAudio(allowed: Boolean)
    fun getAllowHuntWarnAudio(): Boolean
    suspend fun loadAllowHuntWarnAudio()

}