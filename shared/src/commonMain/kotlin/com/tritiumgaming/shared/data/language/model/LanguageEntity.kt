package com.tritiumgaming.shared.data.language.model

import com.tritiumgaming.shared.data.language.mappers.LanguageResources

data class LanguageEntity(
    val localizedName: LanguageResources.LocalizedTitle,
    val nativeName: LanguageResources.NativeTitle,
    val code: String
)

