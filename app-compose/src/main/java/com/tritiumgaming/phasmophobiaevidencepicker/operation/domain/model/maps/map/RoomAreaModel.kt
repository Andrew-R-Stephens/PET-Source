package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.maps.map

import android.graphics.PointF
import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.maps.io.factory.MapDesBlueprint

class RoomAreaModel {
    var points = mutableListOf<PointF>()

    fun setPoints(tempPoints: ArrayList<PointF>) {
        for (tempPoint in tempPoints) {
            points.add(
                PointF(tempPoint.x, tempPoint.y)
            )
        }
    }

    fun setPoints(tempPoints: MapDesBlueprint.WorldMap.Floor.Room.RoomPoints?) {
        tempPoints?.points?.forEach() {
            points.add(
                PointF(it.x, it.y)
            )
        }
    }

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

    fun reset() {
        points = ArrayList()
    }

    val lastPoint: PointF
        get() {
            points.isEmpty()

            return points[points.size - 1]
        }

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
