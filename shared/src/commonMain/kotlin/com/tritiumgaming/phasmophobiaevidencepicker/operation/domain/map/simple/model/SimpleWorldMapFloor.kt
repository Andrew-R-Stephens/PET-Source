package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.mappers.SimpleMapResources.MapFloorImage
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.mappers.SimpleMapResources.MapFloorTitle

data class SimpleWorldMapFloor(
    val layerName: MapFloorTitle,
    val image: MapFloorImage
)