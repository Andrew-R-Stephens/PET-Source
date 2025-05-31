package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.model

import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R

data class DifficultyModel(
    val type: DifficultyType,
    @StringRes val name: Int = R.string.difficulty_title_default,
    val time: Long,
    val modifier: Float,
    val initialSanity: Float
)
