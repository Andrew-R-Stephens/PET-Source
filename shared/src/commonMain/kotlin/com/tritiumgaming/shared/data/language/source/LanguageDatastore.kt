package com.tritiumgaming.shared.data.language.source

import com.tritiumgaming.shared.data.datastore.DatastoreDataSource

interface LanguageDatastore: DatastoreDataSource<LanguageDatastore.LanguagePreferences> {

    suspend fun saveCurrentLanguageCode(languageCode: String)

    data class LanguagePreferences(
        val languageCode: String
    )

}