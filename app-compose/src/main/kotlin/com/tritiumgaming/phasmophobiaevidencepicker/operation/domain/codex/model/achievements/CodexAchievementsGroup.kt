package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.achievements

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class CodexAchievementsGroup(
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
    val item: CodexAchievementsGroupItem
)
