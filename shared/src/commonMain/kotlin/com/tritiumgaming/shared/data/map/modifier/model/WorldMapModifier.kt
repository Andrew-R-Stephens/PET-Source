package com.tritiumgaming.shared.data.map.modifier.model

import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.*

data class WorldMapModifier(
    val name: MapSize,
    val setupModifier: MapSizePhaseModifier,
    val actionModifier: MapSizePhaseModifier
)
