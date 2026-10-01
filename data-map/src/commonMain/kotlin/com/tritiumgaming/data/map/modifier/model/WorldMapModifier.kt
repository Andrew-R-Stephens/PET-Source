package com.tritiumgaming.data.map.modifier.model

import com.tritiumgaming.data.map.modifier.mappers.MapModifierResources.MapSize
import com.tritiumgaming.data.map.modifier.mappers.MapModifierResources.MapSizePhaseModifier

data class WorldMapModifier(
    val name: MapSize,
    val setupModifier: MapSizePhaseModifier,
    val actionModifier: MapSizePhaseModifier
)
