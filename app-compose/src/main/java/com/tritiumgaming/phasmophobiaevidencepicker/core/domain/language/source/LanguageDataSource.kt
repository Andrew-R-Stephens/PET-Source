package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.dto.LanguageDto

interface LanguageDataSource {

    fun fetchLanguages(): List<LanguageDto>

}