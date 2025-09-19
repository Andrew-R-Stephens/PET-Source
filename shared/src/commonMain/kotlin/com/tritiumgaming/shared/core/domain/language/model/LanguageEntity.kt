package com.tritiumgaming.shared.core.domain.language.model

import com.tritiumgaming.shared.core.domain.language.mappers.LanguageResources.LocalizationCode
import com.tritiumgaming.shared.core.domain.language.mappers.LanguageResources.LocalizedTitle
import com.tritiumgaming.shared.core.domain.language.mappers.LanguageResources.NativeTitle

data class LanguageEntity(
    val localizedName: LocalizedTitle,
    val nativeName: NativeTitle,
    val code: LocalizationCode
)

