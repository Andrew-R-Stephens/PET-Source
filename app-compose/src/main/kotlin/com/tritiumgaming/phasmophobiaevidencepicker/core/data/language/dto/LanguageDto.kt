package com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.dto

import androidx.annotation.StringRes

data class LanguageDto(
    @StringRes val localizedName: Int,
    @StringRes val nativeName: Int,
    @StringRes val code: Int
)