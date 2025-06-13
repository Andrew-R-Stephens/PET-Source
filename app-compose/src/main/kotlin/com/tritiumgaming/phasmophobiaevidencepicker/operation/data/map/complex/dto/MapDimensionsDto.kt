package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps.MapDimensions

class MapDimensionsDto(
    val w: Int,
    val h: Int
) {
    @Synchronized
    fun print() {
        Log.d("Maps", "[ W: $w H: $h ]")
    }
}

fun MapDimensionsDto.toDomain() = MapDimensions(
    w = w,
    h = h
)

