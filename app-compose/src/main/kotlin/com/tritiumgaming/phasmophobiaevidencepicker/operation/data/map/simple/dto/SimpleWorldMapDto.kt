package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.dto

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.mappers.SimpleMapResources
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.SimpleWorldMap

data class SimpleWorldMapDto(
    val mapId: String,
    val mapName: SimpleMapResources.MapTitle,
    val mapSize: SimpleMapResources.MapSize,
    val thumbnailImage: SimpleMapResources.MapThumbnail,
    val mapFloors: List<SimpleWorldMapFloorDto>,
    val defaultFloor: Int
) {
    @Synchronized
    fun print() {
        Log.d("Maps", "Map ID: $mapId, Map Name: $mapName, Map Size: $mapSize, Thumbnail: " +
                "$thumbnailImage, Default Floor: $defaultFloor")
    }
}

fun List<SimpleWorldMapDto>.toDomain() = map { it.toDomain() }

fun SimpleWorldMapDto.toDomain() = SimpleWorldMap(
    mapId = mapId,
    mapName = mapName,
    mapSize = mapSize,
    thumbnailImage = thumbnailImage,
    mapFloors = mapFloors,
    defaultFloor = defaultFloor
)