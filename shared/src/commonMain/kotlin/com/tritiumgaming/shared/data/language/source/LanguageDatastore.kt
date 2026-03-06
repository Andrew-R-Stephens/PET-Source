package com.tritiumgaming.shared.data.language.source

import com.tritiumgaming.shared.data.datastore.DatastoreDataSource
import com.tritiumgaming.shared.data.language.source.LanguageDatastore.*

interface LanguageDatastore: DatastoreDataSource<LanguagePreferences> {

    suspend fun saveCurrentLanguageCode(languageCode: String)

    data class LanguagePreferences(
        val languageCode: String
    )

}