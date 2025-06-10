package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.dto

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.possessions.CodexPossessionsGroup

data class CodexPossessionGroupDto(
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
    val items: List<CodexPossessionGroupItemDto>
)

fun CodexPossessionGroupDto.toDomain() = CodexPossessionsGroup(
    name = name,
    icon = icon,
    items = items.map { it.toDomain() }
)

fun List<CodexPossessionGroupDto>.toDomain() = map { dto ->
    dto.toDomain()
}

