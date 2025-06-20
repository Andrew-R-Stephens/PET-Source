package com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.dto.toDomain
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.model.LanguageEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.typography.source.MarketTypographyDatastore
import kotlinx.coroutines.flow.Flow

class LanguageRepositoryImpl(
    private val localDataSource: LanguageDataSource,
    private val dataStoreSource: LanguageDatastore
): LanguageRepository {

    override fun initialSetupEvent() = dataStoreSource.initialSetupEvent()

    override suspend fun initFlow(
        onUpdate: (LanguageDatastore.LanguagePreferences) -> Unit
    ) = dataStoreSource.initFlow(onUpdate)

    override fun getAvailableLanguages(): Result<List<LanguageEntity>> =
        localDataSource.getAvailableLanguages().map { dto -> dto.toDomain() }

    override suspend fun saveCurrentLanguageCode(languageCode: String) =
        dataStoreSource.saveCurrentLanguageCode(languageCode)
    override fun getCurrentLanguageCode(): String = dataStoreSource.getCurrentLanguageCode()
    override suspend fun loadCurrentLanguageCode() {
        dataStoreSource.saveCurrentLanguageCode(dataStoreSource.getCurrentLanguageCode())
    }

}
