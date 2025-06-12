package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model

import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R

data class MapSizeModel(
    @StringRes val name: Int = R.string.difficulty_title_default,
    val setupModifier: Float = 0f,
    val normalModifier: Float = 0f
)