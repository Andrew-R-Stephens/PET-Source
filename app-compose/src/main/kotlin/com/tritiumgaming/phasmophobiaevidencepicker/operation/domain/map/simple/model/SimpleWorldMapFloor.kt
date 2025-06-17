package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.mappers.SimpleMapResources.MapFloorTitle
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.mappers.SimpleMapResources.MapFloorImage

data class SimpleWorldMapFloor(
    val layerName: MapFloorTitle,
    val image: MapFloorImage
)