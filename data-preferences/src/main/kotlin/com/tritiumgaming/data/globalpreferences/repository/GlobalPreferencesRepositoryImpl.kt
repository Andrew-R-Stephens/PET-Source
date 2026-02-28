package com.tritiumgaming.data.globalpreferences.repository

import com.tritiumgaming.shared.data.preferences.DensityType
import com.tritiumgaming.shared.data.preferences.repository.GlobalPreferencesRepository
import com.tritiumgaming.shared.data.preferences.source.GlobalPreferencesDatastore
import kotlinx.coroutines.flow.Flow

class GlobalPreferencesRepositoryImpl(
    private val dataStoreSource: GlobalPreferencesDatastore
): GlobalPreferencesRepository {

    override fun initDatastoreFlow(): Flow<GlobalPreferencesDatastore.GlobalPreferences> =
        dataStoreSource.initDatastoreFlow()

    override suspend fun setDisableScreenSaver(disable: Boolean) =
        dataStoreSource.setDisableScreenSaver(disable)

    override suspend fun setAllowCellularData(allow: Boolean) =
        dataStoreSource.setAllowCellularData(allow)

    override suspend fun setEnableRTL(enable: Boolean) =
        dataStoreSource.setEnableRTL(enable)

    override suspend fun setUiDensityType(densityType: DensityType) =
        dataStoreSource.setUiDensityType(densityType)


    override suspend fun setEnableGhostReorder(enable: Boolean) =
        dataStoreSource.setEnableGhostReorder(enable)

    override suspend fun setAllowIntroduction(allow: Boolean) =
        dataStoreSource.setAllowIntroduction(allow)

    override suspend fun setMaxHuntWarnFlashTime(maxTime: Long) =
        dataStoreSource.setMaxHuntWarnFlashTime(maxTime)

    override suspend fun setAllowHuntWarnAudio(allowed: Boolean) =
        dataStoreSource.setAllowHuntWarnAudio(allowed)

    override suspend fun savePalette(uuid: String) = dataStoreSource.savePalette(uuid)

    override suspend fun saveTypography(uuid: String) = dataStoreSource.saveTypography(uuid)

}
