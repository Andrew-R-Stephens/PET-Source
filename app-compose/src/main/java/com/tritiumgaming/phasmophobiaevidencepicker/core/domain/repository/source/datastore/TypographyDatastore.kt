package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.repository.source.datastore

import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.repository.source.datastore.TypographyDatastore.TypographyPreferences
import kotlinx.coroutines.flow.Flow

interface TypographyDatastore: DatastoreInterface<TypographyPreferences> {
    val flow: Flow<TypographyPreferences>

    suspend fun saveTypography(uuid: String)

    data class TypographyPreferences(
        val uuid: String
    )

    companion object PreferencesKeys {
        lateinit var KEY_TYPOGRAPHY: Preferences.Key<String>
    }

}