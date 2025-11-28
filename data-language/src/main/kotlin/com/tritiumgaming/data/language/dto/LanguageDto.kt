package com.tritiumgaming.data.language.dto

import com.tritiumgaming.shared.data.language.mappers.LanguageResources.LocalizedTitle
import com.tritiumgaming.shared.data.language.mappers.LanguageResources.NativeTitle
import com.tritiumgaming.shared.data.language.model.LanguageEntity

data class LanguageDto(
    val localizedName: LocalizedTitle,
    val nativeName: NativeTitle,
    val code: String
)

fun LanguageDto.toDomain() = LanguageEntity(
    localizedName = localizedName,
    nativeName = nativeName,
    code = code
)

fun List<LanguageDto>.toDomain(): List<LanguageEntity> =
    map ( LanguageDto::toDomain )