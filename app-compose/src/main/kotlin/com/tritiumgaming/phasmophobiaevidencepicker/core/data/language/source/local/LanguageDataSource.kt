package com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.local

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.dto.LanguageDto

interface LanguageDataSource {

    fun getAvailableLanguages(): Result<List<LanguageDto>>

}