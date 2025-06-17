package com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.dto

import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.mappers.LanguageResources.LocalizationCode
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.mappers.LanguageResources.LocalizedTitle
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.mappers.LanguageResources.NativeTitle
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.model.LanguageEntity

data class LanguageDto(
    val localizedName: LocalizedTitle,
    val nativeName: NativeTitle,
    val code: LocalizationCode
)

fun LanguageDto.toDomain() = LanguageEntity(
    localizedName = localizedName,
    nativeName = nativeName,
    code = code
)

fun List<LanguageDto>.toDomain(): List<LanguageEntity> =
    map ( LanguageDto::toDomain )