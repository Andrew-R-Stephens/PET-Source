package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.achievements

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexAchievementsResources.AchievementIcon
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexAchievementsResources.AchievementTitle

data class CodexAchievementsGroup(
    val name: AchievementTitle,
    val icon: AchievementIcon,
    val item: CodexAchievementsGroupItem
)
