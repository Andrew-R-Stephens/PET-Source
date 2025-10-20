package com.tritiumgaming.shared.operation.domain.codex.model.achievements

import com.tritiumgaming.shared.operation.domain.codex.mappers.AchievementsResources.AchievementIcon
import com.tritiumgaming.shared.operation.domain.codex.mappers.AchievementsResources.AchievementTitle
import com.tritiumgaming.shared.operation.domain.codex.mappers.AchievementsResources.AchievementVisibility

data class AchievementsType(
    val name: AchievementTitle,
    val icon: AchievementIcon,
    val visibility: AchievementVisibility,
    val exclusivity: Int,
    val item: CodexAchievementsGroupItem
)
