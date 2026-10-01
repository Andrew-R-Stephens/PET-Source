package com.tritiumgaming.data.codex.dto

import com.tritiumgaming.data.codex.mappers.AchievementsResources.AchievementContent
import com.tritiumgaming.data.codex.mappers.AchievementsResources.AchievementIcon
import com.tritiumgaming.data.codex.mappers.AchievementsResources.AchievementTitle
import com.tritiumgaming.data.codex.mappers.AchievementsResources.AchievementVisibility
import com.tritiumgaming.data.codex.model.achievements.CodexAchievementsGroupItem

data class AchievementsTypeDto(
    val title: AchievementTitle,
    val infoText: AchievementContent,
    val icon: AchievementIcon,
    val visibility: AchievementVisibility,
    val exclusivity: Int,
)

fun AchievementsTypeDto.toDomain() =
    CodexAchievementsGroupItem(
        title = title,
        infoText = infoText,
        icon = icon,
        visibility = visibility,
        exclusivity = exclusivity
    )

fun List<AchievementsTypeDto>.toDomain() = map {
    it.toDomain()
}