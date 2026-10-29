package com.tritiumgaming.data.map.dto.simple

import com.tritiumgaming.data.map.mappers.MapModifierResources.MapSize
import com.tritiumgaming.data.map.mappers.SimpleMapResources.MapThumbnail
import com.tritiumgaming.data.map.mappers.SimpleMapResources.MapTitle
import com.tritiumgaming.data.map.model.simple.SimpleWorldMap

data class SimpleWorldMapDto(
    val mapId: String,
    val mapName: MapTitle,
    val mapSize: MapSize,
    val thumbnailImage: MapThumbnail,
    val mapFloors: List<SimpleWorldMapFloorDto>,
    val defaultFloor: Int
)

fun List<SimpleWorldMapDto>.toDomain() = map { it.toDomain() }

fun SimpleWorldMapDto.toDomain() = SimpleWorldMap(
    mapId = mapId,
    mapName = mapName,
    mapSize = mapSize,
    thumbnailImage = thumbnailImage,
    mapFloors = mapFloors.toDomain(),
    defaultFloor = defaultFloor
)