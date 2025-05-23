package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source.PaletteDatastore
import kotlinx.coroutines.flow.Flow

interface PaletteDatastoreRepository {

    fun initialSetupEvent()
    suspend fun initFlow(): Flow<PaletteDatastore.PalettePreferences>

    suspend fun saveCurrentPalette(uuid: String)

}