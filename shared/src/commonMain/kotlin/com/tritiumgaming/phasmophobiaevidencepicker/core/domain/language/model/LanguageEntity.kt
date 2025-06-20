package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.model

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.mappers.LanguageResources.LocalizationCode
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.mappers.LanguageResources.LocalizedTitle
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.mappers.LanguageResources.NativeTitle

data class LanguageEntity(
    val localizedName: LocalizedTitle,
    val nativeName: NativeTitle,
    val code: LocalizationCode
)

