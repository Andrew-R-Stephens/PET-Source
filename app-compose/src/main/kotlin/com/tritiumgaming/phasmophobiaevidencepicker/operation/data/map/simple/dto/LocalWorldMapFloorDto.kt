package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.dto

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.WorldMapFloor

data class LocalWorldMapFloorDto(
    @StringRes internal val layerName: Int,
    @DrawableRes internal val image: Int
)

fun LocalWorldMapFloorDto.toDomain() = WorldMapFloor(
    layerName = layerName,
    image = image
)