package com.tritiumgaming.shared.operation.domain.codex.model.achievements

import com.tritiumgaming.shared.operation.domain.codex.mappers.AchievementsResources.AchievementIcon
import com.tritiumgaming.shared.operation.domain.codex.mappers.AchievementsResources.AchievementTitle

data class AchievementsType(
    val name: AchievementTitle,
    val icon: AchievementIcon,
    val item: CodexAchievementsGroupItem
)
