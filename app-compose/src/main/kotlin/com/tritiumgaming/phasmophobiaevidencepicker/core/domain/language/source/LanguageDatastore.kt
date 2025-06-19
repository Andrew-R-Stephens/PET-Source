package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source

import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.datastore.DatastoreInterface
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDatastore.LanguagePreferences

interface LanguageDatastore: DatastoreInterface<LanguagePreferences> {

    suspend fun saveCurrentLanguageCode(languageCode: String)
    fun getCurrentLanguageCode(): String

    data class LanguagePreferences(
        val languageCode: String
    )

    companion object PreferenceKeys {
        lateinit var KEY_CURRENT_LANGUAGE_CODE: Preferences.Key<String>
    }

}