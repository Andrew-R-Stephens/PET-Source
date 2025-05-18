package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.local.PaletteLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.remote.PaletteRemoteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.PaletteEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.toExternal
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.PaletteRepository

class PaletteRepositoryImpl(
    private val remotePaletteSource: PaletteRemoteDataSource,
    private val localPaletteSource: PaletteLocalDataSource
): PaletteRepository {

    override suspend fun getRemotePalettes(): List<PaletteEntity> =
        remotePaletteSource.fetchAll().toExternal()

    override suspend fun getLocalPalettes(): List<PaletteEntity> =
        localPaletteSource.fetchAll().toExternal()

}
