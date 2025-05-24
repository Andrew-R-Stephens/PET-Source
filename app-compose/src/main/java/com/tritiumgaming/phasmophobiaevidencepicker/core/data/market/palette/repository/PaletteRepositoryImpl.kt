package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.dto.MarketPaletteDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.mapper.toLocal
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.local.PaletteLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.PaletteRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.PaletteRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source.PaletteDatastore
import kotlinx.coroutines.flow.Flow

class PaletteRepositoryImpl(
    private val remoteDataSource: PaletteRemoteDataSource,
    private val localDataSource: PaletteLocalDataSource,
    private val dataStoreSource: PaletteDatastore
): PaletteRepository {

    override suspend fun getRemotePalettes(): List<MarketPaletteDto> =
        remoteDataSource.fetchAll()

    override suspend fun getLocalPalettes(): List<MarketPaletteDto> =
        localDataSource.fetchAll().toLocal()

    override fun initialSetupEvent() {
        dataStoreSource.initialSetupEvent()
    }

    override suspend fun initFlow(): Flow<PaletteDatastore.PalettePreferences> =
        dataStoreSource.flow

    override suspend fun saveCurrentPalette(uuid: String) {
        dataStoreSource.savePalette(uuid)
    }

}
