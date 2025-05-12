package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.datastore

import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.datastore.LanguageDatastore.LanguagePreferences
import kotlinx.coroutines.flow.Flow

interface LanguageDatastore: DatastoreInterface<LanguagePreferences> {
    val flow: Flow<LanguagePreferences>

    override fun initialSetupEvent()
    override suspend fun fetchInitialPreferences(): LanguagePreferences
    override fun mapPreferences(preferences: Preferences): LanguagePreferences

    data class LanguagePreferences(
        val languageCode: String
    )

    companion object PreferenceKeys {
        lateinit var KEY_PALETTE: Preferences.Key<String>
    }

}