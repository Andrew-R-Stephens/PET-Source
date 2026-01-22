package com.tritiumgaming.shared.data.market.palette.source

import com.tritiumgaming.shared.data.datastore.DatastoreDataSource
import com.tritiumgaming.shared.data.market.palette.source.PaletteDatastore.PalettePreferences

interface PaletteDatastore: DatastoreDataSource<PalettePreferences> {

    suspend fun savePalette(uuid: String)

    data class PalettePreferences(
        val currentUUID: String
    )

}