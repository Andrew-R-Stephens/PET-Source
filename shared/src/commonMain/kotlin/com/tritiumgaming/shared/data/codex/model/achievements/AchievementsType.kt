package com.tritiumgaming.shared.data.codex.model.achievements

import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementCategory
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementIcon

data class AchievementsType(
    val name: AchievementCategory,
    val icon: AchievementIcon,
    val items: List<CodexAchievementsGroupItem>
)
