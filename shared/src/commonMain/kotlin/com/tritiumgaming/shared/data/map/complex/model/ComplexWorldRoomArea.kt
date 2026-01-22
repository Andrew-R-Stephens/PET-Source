package com.tritiumgaming.shared.data.map.complex.model

class ComplexWorldRoomArea(
    val points: List<ComplexWorldPoint>
) {

    val center: ComplexWorldPoint
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

            return ComplexWorldPoint(x, y)
        }

    override fun toString(): String {
        val s = StringBuilder()
        points.forEach { p ->
            s.append("\n\t\t[Point: x").append(p.x).append(" y").append(p.y).append("]") }

        return "[Points:$s\t]"
    }

}
