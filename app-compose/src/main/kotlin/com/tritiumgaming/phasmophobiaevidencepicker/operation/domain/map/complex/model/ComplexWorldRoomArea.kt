package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model

import android.graphics.PointF
import android.util.Log

class ComplexWorldRoomArea(
    val points: List<PointF>
) {

    val center: PointF
        get() {
            var x = 0f
            var y = 0f
            val pointCount = points.size
            for (i in 0 until pointCount - 1) {
                val point = points[i]
                x += point.x
                y += point.y
            }

            x /= pointCount
            y /= pointCount

            return PointF(x, y)
        }

    val lastPoint: PointF
        get() = points.last()

    override fun toString(): String {
        val s = StringBuilder()
        points.forEach { p ->
            s.append("\n\t\t[Point: x").append(p.x).append(" y").append(p.y).append("]") }

        return "[Points:$s\t]"
    }

    @Synchronized
    fun print() {
        points.forEach { p ->
            Log.d("Maps", "[Point: x" + p.x + " y" + p.y + "]") }
    }
}
