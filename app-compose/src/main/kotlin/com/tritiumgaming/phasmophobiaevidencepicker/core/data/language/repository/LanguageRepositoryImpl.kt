package com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.mapper.toDomain
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.model.LanguageEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDatastore
import kotlinx.coroutines.flow.Flow

class LanguageRepositoryImpl(
    private val localDataSource: LanguageDataSource,
    private val dataStoreSource: LanguageDatastore
): LanguageRepository {

    override fun getAvailableLanguages(): Result<List<LanguageEntity>> =
        localDataSource.getAvailableLanguages().map { dto -> dto.toDomain() }

    override fun initialSetupEvent() = dataStoreSource.initialSetupEvent()

    override suspend fun initFlow(): Flow<LanguageDatastore.LanguagePreferences> =
        dataStoreSource.flow

    override suspend fun saveCurrentLanguageCode(languageCode: String) =
        dataStoreSource.saveCurrentLanguageCode(languageCode)
    override fun getCurrentLanguageCode(): String = dataStoreSource.getCurrentLanguageCode()
    override suspend fun loadCurrentLanguageCode() {
        dataStoreSource.saveCurrentLanguageCode(dataStoreSource.getCurrentLanguageCode())
    }

}
