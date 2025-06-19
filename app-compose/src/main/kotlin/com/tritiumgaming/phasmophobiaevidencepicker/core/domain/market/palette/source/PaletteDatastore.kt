package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source

import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.datastore.DatastoreInterface
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source.PaletteDatastore.PalettePreferences

interface PaletteDatastore: DatastoreInterface<PalettePreferences> {

    suspend fun savePalette(uuid: String)

    data class PalettePreferences(
        val uuid: String
    )

    companion object PreferenceKeys {
        lateinit var KEY_PALETTE: Preferences.Key<String>
    }

}