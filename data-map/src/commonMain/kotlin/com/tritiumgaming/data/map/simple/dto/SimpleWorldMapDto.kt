package com.tritiumgaming.data.map.simple.dto

import com.tritiumgaming.data.map.modifier.mappers.MapModifierResources.MapSize
import com.tritiumgaming.data.map.simple.mappers.SimpleMapResources.MapThumbnail
import com.tritiumgaming.data.map.simple.mappers.SimpleMapResources.MapTitle
import com.tritiumgaming.data.map.simple.model.SimpleWorldMap

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