package com.tritiumgaming.data.palette.source

import com.tritiumgaming.core.common.datastore.DatastoreDataSource
import com.tritiumgaming.data.palette.source.PaletteDatastore.PalettePreferences

interface PaletteDatastore: DatastoreDataSource<PalettePreferences> {

    suspend fun savePalette(uuid: String)

    data class PalettePreferences(
        val uuid: String
    )

}