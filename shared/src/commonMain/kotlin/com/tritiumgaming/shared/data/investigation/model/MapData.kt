package com.tritiumgaming.shared.data.investigation.model

import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSize
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSize.SMALL
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSizePhaseModifier
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSizePhaseModifier.ACTION_SMALL
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSizePhaseModifier.SETUP_SMALL
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources.MapTitle
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources.MapTitle.BLEASDALE_FARMHOUSE

data class MapData(
    val index: Int = 0,
    val name: MapTitle = BLEASDALE_FARMHOUSE,
    val size: MapSize = SMALL,
    val setupModifier: MapSizePhaseModifier = SETUP_SMALL,
    val actionModifier: MapSizePhaseModifier = ACTION_SMALL,
)
