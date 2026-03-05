package com.tritiumgaming.shared.data.map.modifier.model

import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources

data class WorldMapModifier(
    val name: MapModifierResources.MapSize,
    val setupModifier: MapModifierResources.MapSizePhaseModifier,
    val actionModifier: MapModifierResources.MapSizePhaseModifier
)
