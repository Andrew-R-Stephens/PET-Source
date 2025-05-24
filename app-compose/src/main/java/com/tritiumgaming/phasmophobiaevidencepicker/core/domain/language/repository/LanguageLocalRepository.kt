package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.model.LanguageEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDataSource
import java.util.Locale

interface LanguageLocalRepository {
    val dataSource: LanguageDataSource

    fun getAvailableLanguages(): List<LanguageEntity>

    companion object {
        var DEFAULT_LANGUAGE: String = Locale.ENGLISH.language
    }

}