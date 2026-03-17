package com.tritiumgaming.shared.data.map.modifier.model

import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSize
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSizePhaseModifier

data class WorldMapModifier(
    val name: MapSize,
    val setupModifier: MapSizePhaseModifier,
    val actionModifier: MapSizePhaseModifier
)
