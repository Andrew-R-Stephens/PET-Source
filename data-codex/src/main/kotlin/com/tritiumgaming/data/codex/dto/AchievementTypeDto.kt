package com.tritiumgaming.data.codex.dto

import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementIcon
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementTitle
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementVisibility
import com.tritiumgaming.shared.data.codex.model.achievements.AchievementsType

data class AchievementTypeDto(
    val name: AchievementTitle,
    val icon: AchievementIcon,
    val visibility: AchievementVisibility,
    val exclusivity: Int,
    val item: AchievementsTypeDto
)

fun AchievementTypeDto.toDomain() = AchievementsType(
    name = name,
    icon = icon,
    visibility = visibility,
    exclusivity = exclusivity,
    item = item.toDomain()
)

fun List<AchievementTypeDto>.toDomain() = map { dto ->
    dto.toDomain()
}

