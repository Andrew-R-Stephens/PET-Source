package com.tritiumstudios.data.operation.model

import com.tritiumgaming.data.map.mappers.MapModifierResources.MapSize
import com.tritiumgaming.data.map.mappers.MapModifierResources.MapSizePhaseModifier
import com.tritiumgaming.data.map.mappers.SimpleMapResources.MapTitle

data class MapData(
    val mapId: String = "",
    val name: MapTitle = MapTitle.BLEASDALE_FARMHOUSE,
    val size: MapSize = MapSize.SMALL,
    val setupModifier: MapSizePhaseModifier = MapSizePhaseModifier.SETUP_SMALL,
    val actionModifier: MapSizePhaseModifier = MapSizePhaseModifier.ACTION_SMALL,
)
