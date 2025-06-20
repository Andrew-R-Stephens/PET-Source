package com.tritiumgaming.phasmophobiaevidencepicker.core.data.globalpreferences.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.repository.GlobalPreferencesRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.source.GlobalPreferencesDatastore

class GlobalPreferencesRepositoryImpl(
    private val dataStoreSource: GlobalPreferencesDatastore
): GlobalPreferencesRepository {

    override fun initialSetupEvent() = dataStoreSource.initialSetupEvent()

    override suspend fun initFlow(
        onUpdate: (preferences: GlobalPreferencesDatastore.GlobalPreferences) -> Unit
    ) = dataStoreSource.initFlow(onUpdate)

    override suspend fun setDisableScreenSaver(disable: Boolean) =
        dataStoreSource.setDisableScreenSaver(disable)

    override fun getDisableScreenSaver(): Boolean =
        dataStoreSource.getDisableScreenSaver()

    override suspend fun loadDisableScreenSaver() =
        dataStoreSource.setDisableScreenSaver(dataStoreSource.getDisableScreenSaver())

    override suspend fun setAllowCellularData(allow: Boolean) =
        dataStoreSource.setAllowCellularData(allow)

    override fun getAllowCellularData(): Boolean =
        dataStoreSource.getAllowCellularData()

    override suspend fun loadAllowCellularData() =
        dataStoreSource.setAllowCellularData(dataStoreSource.getAllowCellularData())

    override suspend fun setEnableRTL(enable: Boolean) =
        dataStoreSource.setEnableRTL(enable)

    override fun getEnableRTL(): Boolean =
        dataStoreSource.getEnableRTL()

    override suspend fun loadEnableRTL() =
        dataStoreSource.setEnableRTL(dataStoreSource.getEnableRTL())

    override suspend fun setEnableGhostReorder(enable: Boolean) =
        dataStoreSource.setEnableGhostReorder(enable)

    override fun getEnableGhostReorder(): Boolean =
        dataStoreSource.getEnableGhostReorder()

    override suspend fun loadEnableGhostReorder() =
        dataStoreSource.setEnableGhostReorder(dataStoreSource.getEnableGhostReorder())

    override suspend fun setAllowIntroduction(allow: Boolean) =
        dataStoreSource.setAllowIntroduction(allow)

    override fun getAllowIntroduction(): Boolean =
        dataStoreSource.getAllowIntroduction()

    override suspend fun loadAllowIntroduction() =
        dataStoreSource.setAllowIntroduction(dataStoreSource.getAllowIntroduction())

    override suspend fun setMaxHuntWarnFlashTime(maxTime: Long) =
        dataStoreSource.setMaxHuntWarnFlashTime(maxTime)

    override fun getMaxHuntWarnFlashTime(): Long =
        dataStoreSource.getMaxHuntWarnFlashTime()

    override suspend fun loadMaxHuntWarnFlashTime() =
        dataStoreSource.setMaxHuntWarnFlashTime(dataStoreSource.getMaxHuntWarnFlashTime())

    override suspend fun setAllowHuntWarnAudio(allowed: Boolean) =
        dataStoreSource.setAllowHuntWarnAudio(allowed)

    override fun getAllowHuntWarnAudio(): Boolean =
        dataStoreSource.getAllowHuntWarnAudio()

    override suspend fun loadAllowHuntWarnAudio() =
        dataStoreSource.setAllowHuntWarnAudio(dataStoreSource.getAllowHuntWarnAudio())

}
