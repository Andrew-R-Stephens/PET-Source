package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.dto

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps.simple.LocalWorldMap

data class LocalWorldMapDto(
    val mapId: String,
    @StringRes val mapName: Int,
    val mapSize: WorldMapSizeTypeDto,
    @DrawableRes val thumbnailImage: Int,
    val mapFloors: List<LocalWorldMapFloorDto>,
    val defaultFloor: Int,
) {
    @Synchronized
    fun print() {
        Log.d("Maps", "Map ID: $mapId, Map Name: $mapName, Map Size: $mapSize, Thumbnail: " +
                "$thumbnailImage, Default Floor: $defaultFloor")
    }
}

fun List<LocalWorldMapDto>.toDomain() = map { it.toDomain() }

fun LocalWorldMapDto.toDomain() = LocalWorldMap(
    mapId = mapId,
    mapName = mapName,
    mapSize = mapSize.toDomain(),
    thumbnailImage = thumbnailImage
)