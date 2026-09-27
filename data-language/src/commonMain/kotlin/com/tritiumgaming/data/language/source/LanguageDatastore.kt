package com.tritiumgaming.data.language.source

import com.tritiumgaming.core.common.datastore.DatastoreDataSource
import com.tritiumgaming.data.language.source.LanguageDatastore.LanguagePreferences

interface LanguageDatastore: DatastoreDataSource<LanguagePreferences> {

    suspend fun saveCurrentLanguageCode(languageCode: String)

    data class LanguagePreferences(
        val languageCode: String
    )

}