package com.tritiumgaming.data.codex.dto

import com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.PossessionTitle
import com.tritiumgaming.shared.data.codex.mappers.PossessionsResources.PossessionsIcon
import com.tritiumgaming.shared.data.codex.model.possessions.PossessionsType

data class PossessionTypeDto(
    val name: PossessionTitle,
    val icon: PossessionsIcon,
    val items: List<PossessionTypeMemberDto>
)

fun PossessionTypeDto.toDomain() = PossessionsType(
    name = name,
    icon = icon,
    items = items.map { it.toDomain() }
)

fun List<PossessionTypeDto>.toDomain() = map { dto ->
    dto.toDomain()
}

