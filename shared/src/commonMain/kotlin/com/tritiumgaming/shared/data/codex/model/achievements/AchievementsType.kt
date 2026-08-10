package com.tritiumgaming.shared.data.codex.model.achievements

import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementCategory
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementIcon
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementTitle
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementVisibility

data class AchievementsType(
    val name: AchievementCategory,
    val icon: AchievementIcon,
    val items: List<CodexAchievementsGroupItem>
)
