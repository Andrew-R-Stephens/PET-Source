package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.mappers.SimpleMapResources

data class SimpleWorldMapFloor(
    val layerName: SimpleMapResources.MapFloorTitle,
    val image: SimpleMapResources.MapFloorImage
)