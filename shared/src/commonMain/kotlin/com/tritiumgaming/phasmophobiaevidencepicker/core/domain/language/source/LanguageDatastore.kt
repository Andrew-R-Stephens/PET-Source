package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.datastore.DatastoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDatastore.LanguagePreferences

interface LanguageDatastore: DatastoreDataSource<LanguagePreferences> {

    suspend fun saveCurrentLanguageCode(languageCode: String)
    fun getCurrentLanguageCode(): String

    data class LanguagePreferences(
        val languageCode: String
    )

}