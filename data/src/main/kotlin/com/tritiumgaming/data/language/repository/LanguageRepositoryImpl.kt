package com.tritiumgaming.data.language.repository

import com.tritiumgaming.data.language.dto.toDomain
import com.tritiumgaming.data.language.source.local.LanguageDataSource
import com.tritiumgaming.shared.core.domain.language.model.LanguageEntity
import com.tritiumgaming.shared.core.domain.language.repository.LanguageRepository
import com.tritiumgaming.shared.core.domain.language.source.LanguageDatastore

class LanguageRepositoryImpl(
    private val localDataSource: LanguageDataSource,
    private val dataStoreSource: LanguageDatastore
): LanguageRepository {

    override fun initializeDatastoreLiveData() = dataStoreSource.initializeDatastoreLiveData()

    override suspend fun initDatastoreFlow(
        onUpdate: (LanguageDatastore.LanguagePreferences) -> Unit
    ) = dataStoreSource.initDatastoreFlow(onUpdate)

    override fun getAvailableLanguages(): Result<List<LanguageEntity>> {
        return localDataSource.getAvailableLanguages().map { dto -> dto.toDomain() }
    }
    override suspend fun saveCurrentLanguageCode(languageCode: String) =
        dataStoreSource.saveCurrentLanguageCode(languageCode)
    override fun getCurrentLanguageCode(): String = dataStoreSource.getCurrentLanguageCode()
    override suspend fun loadCurrentLanguageCode() {
        dataStoreSource.saveCurrentLanguageCode(
            dataStoreSource.getCurrentLanguageCode())
    }

}
