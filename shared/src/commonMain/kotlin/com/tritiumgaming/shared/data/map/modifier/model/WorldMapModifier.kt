package com.tritiumgaming.shared.data.map.modifier.model

import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources

data class WorldMapModifier(
    val name: MapModifierResources.MapSize,
    val setupModifier: Float,
    val normalModifier: Float
)
