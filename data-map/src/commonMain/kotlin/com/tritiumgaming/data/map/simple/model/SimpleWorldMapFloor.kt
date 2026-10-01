package com.tritiumgaming.data.map.simple.model

import com.tritiumgaming.data.map.simple.mappers.SimpleMapResources.MapFloorImage
import com.tritiumgaming.data.map.simple.mappers.SimpleMapResources.MapFloorTitle

data class SimpleWorldMapFloor(
    val layerName: MapFloorTitle,
    val image: MapFloorImage
)