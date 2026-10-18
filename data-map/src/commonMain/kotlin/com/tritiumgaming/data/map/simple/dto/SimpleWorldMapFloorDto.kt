package com.tritiumgaming.data.map.simple.dto

import com.tritiumgaming.data.map.simple.mappers.SimpleMapResources.MapFloorImage
import com.tritiumgaming.data.map.simple.mappers.SimpleMapResources.MapFloorTitle
import com.tritiumgaming.data.map.simple.model.SimpleWorldMapFloor

data class SimpleWorldMapFloorDto(
    val layerName: MapFloorTitle,
    val image: MapFloorImage
)

fun List<SimpleWorldMapFloorDto>.toDomain() = map { it.toDomain() }

fun SimpleWorldMapFloorDto.toDomain() = SimpleWorldMapFloor(
    layerName = layerName,
    image = image
)