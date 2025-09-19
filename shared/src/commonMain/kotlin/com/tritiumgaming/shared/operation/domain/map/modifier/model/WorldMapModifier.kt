package com.tritiumgaming.shared.operation.domain.map.modifier.model

import com.tritiumgaming.shared.operation.domain.map.modifier.mappers.MapModifierResources

data class WorldMapModifier(
    val name: MapModifierResources.MapSize,
    val setupModifier: Float = 0f,
    val normalModifier: Float = 0f
)
