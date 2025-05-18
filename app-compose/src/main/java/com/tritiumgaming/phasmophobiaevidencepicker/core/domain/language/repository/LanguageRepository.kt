package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.model.LanguageObject
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDataSource
import java.util.Locale

interface LanguageRepository {
    val dataSource: LanguageDataSource

    fun getLanguages(): List<LanguageObject>

    companion object {
        var DEFAULT_LANGUAGE: String = Locale.ENGLISH.language
    }

}