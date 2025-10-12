package com.tritiumgaming.shared.core.domain.language.repository

import com.tritiumgaming.shared.core.domain.datastore.DatastoreRepository
import com.tritiumgaming.shared.core.domain.language.model.LanguageEntity
import com.tritiumgaming.shared.core.domain.language.source.LanguageDatastore.LanguagePreferences

interface LanguageRepository: DatastoreRepository<LanguagePreferences> {

    fun getAvailableLanguages(): Result<List<LanguageEntity>>

    fun setDefaultLanguage(language: LanguageEntity)
    fun getDefaultLanguage(): LanguageEntity?

    suspend fun saveCurrentLanguageCode(languageCode: String)
    fun getCurrentLanguageCode(): String
    suspend fun loadCurrentLanguageCode()

}