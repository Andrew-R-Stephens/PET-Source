package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.datastore

import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.datastore.PaletteDatastore.ColorPreferences
import kotlinx.coroutines.flow.Flow

interface PaletteDatastore: DatastoreInterface<ColorPreferences> {
    val flow: Flow<ColorPreferences>

    suspend fun savePalette(uuid: String)

    data class ColorPreferences(
        val uuid: String
    )

    companion object PreferenceKeys {
        lateinit var KEY_PALETTE: Preferences.Key<String>
    }

}