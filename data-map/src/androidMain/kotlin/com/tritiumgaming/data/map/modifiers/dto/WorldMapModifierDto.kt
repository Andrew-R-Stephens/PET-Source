package com.tritiumgaming.data.map.modifiers.dto

import com.tritiumgaming.data.map.mappers.MapModifierResources.MapSize
import com.tritiumgaming.data.map.mappers.MapModifierResources.MapSizePhaseModifier
import com.tritiumgaming.data.map.model.modifier.WorldMapModifier

data class WorldMapModifierDto(
    val name: MapSize,
    val setupModifier: MapSizePhaseModifier,
    val actionModifier: MapSizePhaseModifier
)

fun List<WorldMapModifierDto>.toDomain() = map { it.toDomain() }

fun WorldMapModifierDto.toDomain() = WorldMapModifier(
    name = name,
    setupModifier = setupModifier,
    actionModifier = actionModifier
)