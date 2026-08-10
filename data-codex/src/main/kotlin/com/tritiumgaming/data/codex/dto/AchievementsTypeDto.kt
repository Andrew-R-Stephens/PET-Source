package com.tritiumgaming.data.codex.dto

import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementContent
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementTitle
import com.tritiumgaming.shared.data.codex.model.achievements.CodexAchievementsGroupItem

data class AchievementsTypeDto(
    val title: AchievementTitle,
    val infoText: AchievementContent,
    val icon: AchievementsResources.AchievementIcon,
    val visibility: AchievementsResources.AchievementVisibility,
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