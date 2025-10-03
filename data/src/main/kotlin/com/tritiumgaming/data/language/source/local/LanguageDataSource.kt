package com.tritiumgaming.data.language.source.local

import com.tritiumgaming.data.language.dto.LanguageDto

interface LanguageDataSource {

    fun getAvailableLanguages(): Result<List<LanguageDto>>

}