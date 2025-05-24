package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.model.LanguageEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDatastore
import kotlinx.coroutines.flow.Flow
import java.util.Locale

interface LanguageRepository {
    fun getAvailableLanguages(): List<LanguageEntity>

    fun initialSetupEvent()
    suspend fun initFlow(): Flow<LanguageDatastore.LanguagePreferences>

    suspend fun saveCurrentLanguageCode(languageCode: String)
    fun getCurrentLanguageCode(): String
    suspend fun loadCurrentLanguageCode()

    companion object {
        var DEFAULT_LANGUAGE: String = Locale.ENGLISH.language
    }

}