package com.tritiumgaming.data.codex.dto

import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexAchievementsResources.AchievementIcon
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexAchievementsResources.AchievementTitle
import com.tritiumgaming.shared.operation.domain.codex.model.achievements.CodexAchievementsGroup

data class CodexAchievementsGroupDto(
    val name: AchievementTitle,
    val icon: AchievementIcon,
    val item: CodexAchievementsGroupItemDto
)

fun CodexAchievementsGroupDto.toDomain() = CodexAchievementsGroup(
    name = name,
    icon = icon,
    item = item.toDomain()
)

fun List<CodexAchievementsGroupDto>.toDomain() = map { dto ->
    dto.toDomain()
}

