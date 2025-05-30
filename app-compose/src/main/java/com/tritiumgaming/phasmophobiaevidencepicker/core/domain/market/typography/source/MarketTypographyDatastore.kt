package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source

import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.datastore.DatastoreInterface
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.MarketTypographyDatastore.TypographyPreferences

interface MarketTypographyDatastore: DatastoreInterface<TypographyPreferences> {

    suspend fun saveTypography(uuid: String)

    data class TypographyPreferences(
        val uuid: String
    )

    companion object PreferencesKeys {
        lateinit var KEY_TYPOGRAPHY: Preferences.Key<String>
    }

}