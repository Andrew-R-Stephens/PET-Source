package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.datastore.DatastoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source.PaletteDatastore.PalettePreferences

interface PaletteDatastore: DatastoreDataSource<PalettePreferences> {

    suspend fun savePalette(uuid: String)

    data class PalettePreferences(
        val uuid: String
    )

}