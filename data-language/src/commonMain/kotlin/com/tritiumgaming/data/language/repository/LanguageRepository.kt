package com.tritiumgaming.data.language.repository

import com.tritiumgaming.core.common.datastore.DatastoreRepository
import com.tritiumgaming.data.language.model.LanguageEntity
import com.tritiumgaming.data.language.source.LanguageDatastore.LanguagePreferences

interface LanguageRepository: DatastoreRepository<LanguagePreferences> {

    fun getAvailableLanguages(): Result<List<LanguageEntity>>

    fun setDefaultLanguage(language: LanguageEntity)
    fun getDefaultLanguage(): LanguageEntity?

    suspend fun saveCurrentLanguageCode(languageCode: String)

}