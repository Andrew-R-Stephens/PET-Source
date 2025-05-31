package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.dto.MarketTypographyDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.mapper.toLocal
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.local.MarketTypographyLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.typography.source.remote.MarketTypographyRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.repository.MarketTypographyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.MarketTypographyDatastore
import kotlinx.coroutines.flow.Flow

class MarketTypographyRepositoryImpl(
    private val localDataSource: MarketTypographyLocalDataSource,
    private val remoteDataSource: MarketTypographyRemoteDataSource,
    private val dataStoreSource: MarketTypographyDatastore
): MarketTypographyRepository {

    override fun initialSetupEvent() {
        dataStoreSource.initialSetupEvent()
    }

    override suspend fun initFlow(): Flow<MarketTypographyDatastore.TypographyPreferences> =
        dataStoreSource.flow

    override suspend fun saveCurrentTypography(uuid: String) {
        dataStoreSource.saveTypography(uuid)
    }

    override suspend fun getRemoteTypographies(): List<MarketTypographyDto> =
        remoteDataSource.fetchAll()

    override suspend fun getLocalTypographies(): List<MarketTypographyDto> =
        localDataSource.fetchAll().toLocal()

}