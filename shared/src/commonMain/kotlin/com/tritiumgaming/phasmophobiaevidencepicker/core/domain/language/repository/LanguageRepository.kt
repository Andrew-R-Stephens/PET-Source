package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.datastore.DatastoreRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.model.LanguageEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDatastore.LanguagePreferences

interface LanguageRepository: DatastoreRepository<LanguagePreferences> {

    fun getAvailableLanguages(): Result<List<LanguageEntity>>

    suspend fun saveCurrentLanguageCode(languageCode: String)
    fun getCurrentLanguageCode(): String
    suspend fun loadCurrentLanguageCode()

}