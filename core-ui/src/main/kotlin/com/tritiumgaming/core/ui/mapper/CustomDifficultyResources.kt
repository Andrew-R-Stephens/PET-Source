package com.tritiumgaming.core.ui.mapper

import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.data.customdifficulty.CustomDifficultyResources

@StringRes fun CustomDifficultyResources.Title.toStringResource() = when(this) {
    CustomDifficultyResources.Title.CUSTOM -> R.string.difficulty_title_custom
}