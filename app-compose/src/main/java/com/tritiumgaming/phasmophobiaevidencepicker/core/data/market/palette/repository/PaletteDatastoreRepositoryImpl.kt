package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.PaletteDatastoreRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source.PaletteDatastore
import kotlinx.coroutines.flow.Flow

class PaletteDatastoreRepositoryImpl(
    private val dataStoreSource: PaletteDatastore
): PaletteDatastoreRepository {

    override fun initialSetupEvent() {
        dataStoreSource.initialSetupEvent()
    }

    override suspend fun initFlow(): Flow<PaletteDatastore.PalettePreferences> =
        dataStoreSource.flow

    override suspend fun saveCurrentPalette(uuid: String) {
        dataStoreSource.savePalette(uuid)
    }

}