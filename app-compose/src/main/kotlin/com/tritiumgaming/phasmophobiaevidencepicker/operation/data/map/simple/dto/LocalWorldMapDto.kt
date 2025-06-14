package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.dto

import androidx.annotation.DrawableRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.mapviewer.LocalWorldMap

data class LocalWorldMapDto(
    val mapId: String,
    val mapName: Int,
    val mapSize: WorldMapSizeTypeDto,
    @DrawableRes val thumbnailImage: Int,
    val mapFloors: List<LocalWorldMapFloorDto>,
    val defaultFloor: Int,
)

fun List<LocalWorldMapDto>.toDomain() = map { it.toDomain() }

fun LocalWorldMapDto.toDomain() = LocalWorldMap(
    mapId = mapId,
    mapName = mapName,
    mapSize = mapSize.toDomain(),
    thumbnailImage = thumbnailImage
)