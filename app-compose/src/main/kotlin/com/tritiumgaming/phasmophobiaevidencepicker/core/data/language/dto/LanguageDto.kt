package com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.dto

import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.model.LanguageEntity

data class LanguageDto(
    @StringRes val localizedName: Int,
    @StringRes val nativeName: Int,
    @StringRes val code: Int
)

fun LanguageDto.toDomain() = LanguageEntity(
    localizedName = localizedName,
    nativeName = nativeName,
    code = code
)

fun List<LanguageDto>.toDomain(): List<LanguageEntity> =
    map ( LanguageDto::toDomain )