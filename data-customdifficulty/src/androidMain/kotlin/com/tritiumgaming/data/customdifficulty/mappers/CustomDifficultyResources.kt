package com.tritiumgaming.data.customdifficulty.mappers

import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.data.customdifficulty.CustomDifficultyResources

@StringRes fun CustomDifficultyResources.Title.toStringResource() = when(this) {
    CustomDifficultyResources.Title.CUSTOM -> R.string.difficulty_title_custom
}