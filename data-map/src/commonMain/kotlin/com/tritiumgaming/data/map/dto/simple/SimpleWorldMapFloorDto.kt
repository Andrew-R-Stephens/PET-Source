package com.tritiumgaming.data.map.dto.simple

import com.tritiumgaming.data.map.mappers.SimpleMapResources.MapFloorImage
import com.tritiumgaming.data.map.mappers.SimpleMapResources.MapFloorTitle
import com.tritiumgaming.data.map.model.simple.SimpleWorldMapFloor

data class SimpleWorldMapFloorDto(
    val layerName: MapFloorTitle,
    val image: MapFloorImage
)

fun List<SimpleWorldMapFloorDto>.toDomain() = map { it.toDomain() }

fun SimpleWorldMapFloorDto.toDomain() = SimpleWorldMapFloor(
    layerName = layerName,
    image = image
)