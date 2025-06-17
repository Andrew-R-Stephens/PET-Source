package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.dto

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.mappers.SimpleMapResources.MapFloorImage
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.mappers.SimpleMapResources.MapFloorTitle
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.SimpleWorldMapFloor

data class SimpleWorldMapFloorDto(
    val layerName: MapFloorTitle,
    val image: MapFloorImage
)

fun SimpleWorldMapFloorDto.toDomain() = SimpleWorldMapFloor(
    layerName = layerName,
    image = image
)