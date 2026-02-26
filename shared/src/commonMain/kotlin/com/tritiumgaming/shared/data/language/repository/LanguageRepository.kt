package com.tritiumgaming.shared.data.language.repository

import com.tritiumgaming.shared.data.datastore.DatastoreRepository
import com.tritiumgaming.shared.data.language.model.LanguageEntity
import com.tritiumgaming.shared.data.language.source.LanguageDatastore.LanguagePreferences

interface LanguageRepository: DatastoreRepository<LanguagePreferences> {

    fun getAvailableLanguages(): Result<List<LanguageEntity>>

    fun setDefaultLanguage(language: LanguageEntity)
    fun getDefaultLanguage(): LanguageEntity?

    suspend fun saveCurrentLanguageCode(languageCode: String)

}