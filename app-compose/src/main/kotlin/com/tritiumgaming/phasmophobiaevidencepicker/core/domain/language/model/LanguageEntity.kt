package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.model

import androidx.annotation.StringRes

data class LanguageEntity(
    @StringRes val localizedName: Int,
    @StringRes val nativeName: Int,
    @StringRes val code: Int
)

