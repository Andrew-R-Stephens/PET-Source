package com.tritiumgaming.shared.data.map.modifier.model

data class WorldMapModifier(
    val name: com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSize,
    val setupModifier: Float,
    val normalModifier: Float
)
