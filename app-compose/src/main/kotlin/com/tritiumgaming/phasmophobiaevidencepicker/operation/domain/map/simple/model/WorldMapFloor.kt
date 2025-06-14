package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class WorldMapFloor(
    @StringRes private val layerName: Int,
    @DrawableRes private val image: Int
)