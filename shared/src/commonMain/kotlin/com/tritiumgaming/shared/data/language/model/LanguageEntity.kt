package com.tritiumgaming.shared.data.language.model

import com.tritiumgaming.shared.data.language.mappers.LanguageResources.LocalizedTitle
import com.tritiumgaming.shared.data.language.mappers.LanguageResources.NativeTitle

data class LanguageEntity(
    val localizedName: LocalizedTitle,
    val nativeName: NativeTitle,
    val code: String
)

