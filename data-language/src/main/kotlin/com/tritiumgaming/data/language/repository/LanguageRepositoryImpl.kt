package com.tritiumgaming.data.language.repository

import com.tritiumgaming.data.language.dto.toDomain
import com.tritiumgaming.data.language.source.local.LanguageDataSource
import com.tritiumgaming.shared.data.language.model.LanguageEntity
import com.tritiumgaming.shared.data.language.repository.LanguageRepository
import com.tritiumgaming.shared.data.language.source.LanguageDatastore

class LanguageRepositoryImpl(
    private val localDataSource: LanguageDataSource,
    private val dataStoreSource: LanguageDatastore
): LanguageRepository {

    private var defaultLanguage: LanguageEntity? = null

    override fun initDatastoreFlow() = dataStoreSource.initDatastoreFlow()

    override fun getAvailableLanguages(): Result<List<LanguageEntity>> {
        return localDataSource.getAvailableLanguages().map { dto -> dto.toDomain() }
    }

    override fun setDefaultLanguage(language: LanguageEntity) {
        defaultLanguage = language
    }
    override fun getDefaultLanguage(): LanguageEntity? = defaultLanguage

    override suspend fun saveCurrentLanguageCode(languageCode: String) =
        dataStoreSource.saveCurrentLanguageCode(languageCode)
    override fun getCurrentLanguageCode(): String = dataStoreSource.getCurrentLanguageCode()
    override suspend fun loadCurrentLanguageCode() {
        dataStoreSource.saveCurrentLanguageCode(
            dataStoreSource.getCurrentLanguageCode())
    }

}
