package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.dto

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexAchievementsResources.AchievementIcon
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexAchievementsResources.AchievementTitle
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.achievements.CodexAchievementsGroup
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.achievements.CodexAchievementsGroupItem

data class CodexAchievementsGroupDto(
    val name: AchievementTitle,
    val icon: AchievementIcon,
    val item: CodexAchievementsGroupItem
)

fun CodexAchievementsGroupDto.toDomain() = CodexAchievementsGroup(
    name = name,
    icon = icon,
    item = item
)

fun List<CodexAchievementsGroupDto>.toDomain() = map { dto ->
    dto.toDomain()
}

