package com.tritiumgaming.data.map.complex.dto

import android.util.Log
import com.tritiumgaming.shared.data.map.complex.model.ComplexWorldMapDimensions

data class ComplexMapDimensionsDto(
    val w: Int,
    val h: Int
) {
    @Synchronized
    fun print() {
        Log.d("Maps", "[ W: $w H: $h ]")
    }
}

fun ComplexMapDimensionsDto.toDomain() = ComplexWorldMapDimensions(
    w = w,
    h = h
)

