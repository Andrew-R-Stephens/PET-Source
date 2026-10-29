package com.tritiumgaming.data.map.model.simple

import com.tritiumgaming.data.map.mappers.SimpleMapResources.MapFloorImage
import com.tritiumgaming.data.map.mappers.SimpleMapResources.MapFloorTitle

data class SimpleWorldMapFloor(
    val layerName: MapFloorTitle,
    val image: MapFloorImage
)