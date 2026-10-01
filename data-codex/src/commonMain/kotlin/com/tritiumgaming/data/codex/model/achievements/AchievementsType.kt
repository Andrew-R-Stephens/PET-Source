package com.tritiumgaming.data.codex.model.achievements

import com.tritiumgaming.data.codex.mappers.AchievementsResources.AchievementCategory
import com.tritiumgaming.data.codex.mappers.AchievementsResources.AchievementIcon

data class AchievementsType(
    val name: AchievementCategory,
    val icon: AchievementIcon,
    val items: List<CodexAchievementsGroupItem>
)
