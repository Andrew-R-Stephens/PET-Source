package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers

import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.mapper.DifficultyResources.DifficultyTitle

@StringRes fun DifficultyTitle.toStringResource(): Int =
    when (this) {
        DifficultyTitle.AMATEUR -> R.string.difficulty_title_amateur
        DifficultyTitle.INTERMEDIATE -> R.string.difficulty_title_intermediate
        DifficultyTitle.PROFESSIONAL -> R.string.difficulty_title_professional
        DifficultyTitle.NIGHTMARE -> R.string.difficulty_title_nightmare
        DifficultyTitle.INSANITY -> R.string.difficulty_title_insanity
    }