package com.tritiumgaming.shared.data.codex.model.achievements

import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementContent
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementIcon
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementTitle
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementVisibility

data class CodexAchievementsGroupItem(
    val title: AchievementTitle,
    val infoText: AchievementContent,
    val icon: AchievementIcon,
    val visibility: AchievementVisibility,
    val exclusivity: Int,
)