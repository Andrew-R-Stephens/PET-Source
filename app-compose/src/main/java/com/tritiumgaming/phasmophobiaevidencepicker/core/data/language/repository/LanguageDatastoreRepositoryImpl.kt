package com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDatastore
import kotlinx.coroutines.flow.Flow

class LanguageDatastoreRepositoryImpl(
    private val dataStoreSource: LanguageDatastore,
): LanguageDatastoreRepository {

    override fun initialSetupEvent() {
        dataStoreSource.initialSetupEvent()
    }

    override suspend fun initFlow(): Flow<LanguageDatastore.LanguagePreferences> =
        dataStoreSource.flow

    override suspend fun saveCurrentLanguageCode(languageCode: String) =
        dataStoreSource.saveCurrentLanguageCode(languageCode)
    override fun getCurrentLanguageCode(): String = dataStoreSource.getCurrentLanguageCode()
    override suspend fun loadCurrentLanguageCode() {
        dataStoreSource.saveCurrentLanguageCode(dataStoreSource.getCurrentLanguageCode())
    }

}

interface LanguageDatastoreRepository {

    fun initialSetupEvent()
    suspend fun initFlow(): Flow<LanguageDatastore.LanguagePreferences>

    suspend fun saveCurrentLanguageCode(languageCode: String)
    fun getCurrentLanguageCode(): String
    suspend fun loadCurrentLanguageCode()

}