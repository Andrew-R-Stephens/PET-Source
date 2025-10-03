package com.tritiumgaming.data.codex.dto

import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexPossessionsResources.PossessionTitle
import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexPossessionsResources.PossessionsIcon
import com.tritiumgaming.shared.operation.domain.codex.model.possessions.CodexPossessionsGroup

data class CodexPossessionGroupDto(
    val name: PossessionTitle,
    val icon: PossessionsIcon,
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

