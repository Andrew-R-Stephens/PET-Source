package com.tritiumgaming.data.map.modifiers.dto

import com.tritiumgaming.shared.operation.domain.map.modifier.mappers.MapModifierResources
import com.tritiumgaming.shared.operation.domain.map.modifier.mappers.MapModifierResources.SizePhaseModifier
import com.tritiumgaming.shared.operation.domain.map.modifier.model.WorldMapModifier

data class WorldMapModifierDto(
    val name: MapModifierResources.MapSize,
    val setupModifier: Float,
    val normalModifier: Float
)

fun List<WorldMapModifierDto>.toDomain() = map { it.toDomain() }

fun WorldMapModifierDto.toDomain() = WorldMapModifier(
    name = name,
    setupModifier = setupModifier,
    normalModifier = normalModifier
)