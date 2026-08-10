package com.tritiumgaming.data.codex.dto

import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementCategory
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementIcon
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementTitle
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementVisibility
import com.tritiumgaming.shared.data.codex.model.achievements.AchievementsType

data class AchievementTypeDto(
    val name: AchievementCategory,
    val icon: AchievementIcon,
    val items: List<AchievementsTypeDto>
)

fun AchievementTypeDto.toDomain() = AchievementsType(
    name = name,
    icon = icon,
    items = items.toDomain()
)

fun List<AchievementTypeDto>.toDomain() = map { dto ->
    dto.toDomain()
}

