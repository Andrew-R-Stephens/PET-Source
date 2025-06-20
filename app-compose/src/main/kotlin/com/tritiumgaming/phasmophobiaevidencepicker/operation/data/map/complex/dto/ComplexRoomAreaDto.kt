package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.dto

import android.graphics.PointF
import android.util.Log

data class ComplexRoomAreaDto(
    val points: List<PointF>
) {

    override fun toString(): String {
        val s = StringBuilder()
        for (p in points) {
            s.append("\n\t\t[Point: x").append(p.x).append(" y").append(p.y).append("]")
        }
        return "[Points:$s\t]"
    }

    @Synchronized
    fun print() {
        for (p in points) {
            Log.d("Maps", "[Point: x" + p.x + " y" + p.y + "]")
        }
    }
}

fun ComplexRoomAreaDto.toDomain() = ComplexWorldRoomArea(
    points = points.map { point -> ComplexWorldPoint(point.x, point.y) }
)