package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.dto

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.achievements.CodexAchievementsGroup

data class CodexAchievementsGroupDto(
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
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

