package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.modifiers.dto

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.mappers.MapModifierResources
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.model.WorldMapModifier

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