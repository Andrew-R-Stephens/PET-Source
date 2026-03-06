package com.tritiumgaming.shared.data.language.model

import com.tritiumgaming.shared.data.language.mappers.LanguageResources
import com.tritiumgaming.shared.data.language.mappers.LanguageResources.*

data class LanguageEntity(
    val localizedName: LocalizedTitle,
    val nativeName: NativeTitle,
    val code: String
)

