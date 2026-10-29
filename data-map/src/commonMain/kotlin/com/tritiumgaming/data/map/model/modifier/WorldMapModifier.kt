package com.tritiumgaming.data.map.model.modifier

import com.tritiumgaming.data.map.mappers.MapModifierResources.MapSize
import com.tritiumgaming.data.map.mappers.MapModifierResources.MapSizePhaseModifier

data class WorldMapModifier(
    val name: MapSize,
    val setupModifier: MapSizePhaseModifier,
    val actionModifier: MapSizePhaseModifier
)
