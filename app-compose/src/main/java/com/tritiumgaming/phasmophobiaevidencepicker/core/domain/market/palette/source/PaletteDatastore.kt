package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source

import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.datastore.DatastoreInterface
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source.PaletteDatastore.PalettePreferences
import kotlinx.coroutines.flow.Flow

interface PaletteDatastore: DatastoreInterface<PalettePreferences> {
    val flow: Flow<PalettePreferences>

    suspend fun savePalette(uuid: String)

    data class PalettePreferences(
        val uuid: String
    )

    companion object PreferenceKeys {
        lateinit var KEY_PALETTE: Preferences.Key<String>
    }

}