package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.datastore.language

import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.datastore.DatastoreInterface
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.datastore.language.LanguageDatastore.LanguagePreferences
import kotlinx.coroutines.flow.Flow

interface LanguageDatastore: DatastoreInterface<LanguagePreferences> {
    val flow: Flow<LanguagePreferences>

    data class LanguagePreferences(
        val languageCode: String
    )

    companion object PreferenceKeys {
        lateinit var KEY_PALETTE: Preferences.Key<String>
    }

}