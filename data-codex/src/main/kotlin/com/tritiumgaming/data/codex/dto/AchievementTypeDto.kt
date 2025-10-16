package com.tritiumgaming.data.codex.dto

import com.tritiumgaming.shared.operation.domain.codex.mappers.AchievementsResources.AchievementIcon
import com.tritiumgaming.shared.operation.domain.codex.mappers.AchievementsResources.AchievementTitle
import com.tritiumgaming.shared.operation.domain.codex.model.achievements.AchievementsType

data class AchievementTypeDto(
    val name: AchievementTitle,
    val icon: AchievementIcon,
    val item: AchievementsTypeDto
)

fun AchievementTypeDto.toDomain() = AchievementsType(
    name = name,
    icon = icon,
    item = item.toDomain()
)

fun List<AchievementTypeDto>.toDomain() = map { dto ->
    dto.toDomain()
}

