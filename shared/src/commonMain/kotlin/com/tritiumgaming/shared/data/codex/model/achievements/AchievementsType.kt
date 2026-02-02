package com.tritiumgaming.shared.data.codex.model.achievements

import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementIcon
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementTitle
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementVisibility
import com.tritiumgaming.shared.data.codex.model.CategoryType

data class AchievementsType(
    val name: AchievementTitle,
    val icon: AchievementIcon,
    val visibility: AchievementVisibility,
    val exclusivity: Int,
    val item: CodexAchievementsGroupItem
)
