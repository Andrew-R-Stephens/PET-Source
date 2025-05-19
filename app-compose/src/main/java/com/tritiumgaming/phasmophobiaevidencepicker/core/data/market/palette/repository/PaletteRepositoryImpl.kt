package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.dto.MarketPaletteDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.mapper.toLocal
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.local.PaletteLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.PaletteRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.PaletteRepository

class PaletteRepositoryImpl(
    private val remotePaletteSource: PaletteRemoteDataSource,
    private val localPaletteSource: PaletteLocalDataSource
): PaletteRepository {

    override suspend fun getRemotePalettes(): List<MarketPaletteDto> =
        remotePaletteSource.fetchAll()

    override suspend fun getLocalPalettes(): List<MarketPaletteDto> =
        localPaletteSource.fetchAll().toLocal()

}
