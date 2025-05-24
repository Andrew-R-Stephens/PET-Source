package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.dto.MarketTypographyDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.mapper.toLocal
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.local.TypographyLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.remote.TypographyRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.TypographyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.TypographyDatastore
import kotlinx.coroutines.flow.Flow

class TypographyRepositoryImpl(
    private val localDataSource: TypographyLocalDataSource,
    private val remoteDataSource: TypographyRemoteDataSource,
    private val dataStoreSource: TypographyDatastore
): TypographyRepository {

    override fun initialSetupEvent() {
        dataStoreSource.initialSetupEvent()
    }

    override suspend fun initFlow(): Flow<TypographyDatastore.TypographyPreferences> =
        dataStoreSource.flow

    override suspend fun saveCurrentTypography(uuid: String) {
        dataStoreSource.saveTypography(uuid)
    }

    override suspend fun getRemoteTypographies(): List<MarketTypographyDto> =
        remoteDataSource.fetchAll()

    override suspend fun getLocalTypographies(): List<MarketTypographyDto> =
        localDataSource.fetchAll().toLocal()

}