package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source

import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.datastore.DatastoreInterface
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDatastore.LanguagePreferences
import kotlinx.coroutines.flow.Flow

interface LanguageDatastore: DatastoreInterface<LanguagePreferences> {

    data class LanguagePreferences(
        val languageCode: String
    )

    companion object PreferenceKeys {
        lateinit var KEY_CURRENT_LANGUAGE_CODE: Preferences.Key<String>
    }

}