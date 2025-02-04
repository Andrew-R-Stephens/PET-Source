package com.tritiumgaming.phasmophobiaevidencepicker.util.geometry

internal class CubicIterator(
    var cubic: CubicCurve2D,
    var affine: AffineTransform?
) : PathIterator {
    var index: Int = 0

    override val windingRule: Int
        get() = PathIterator.WIND_NON_ZERO

    override val isDone: Boolean
        get() = (index > 1)

    override fun next() {
        index++
    }

    override fun currentSegment(coords: FloatArray): Int {
        if (isDone) {
            throw NoSuchElementException("cubic iterator iterator out of bounds")
        }
        val type: Int
        if (index == 0) {
            coords[0] = cubic.x1.toFloat()
            coords[1] = cubic.y1.toFloat()
            type = PathIterator.SEG_MOVETO
        } else {
            coords[0] = cubic.ctrlX1.toFloat()
            coords[1] = cubic.ctrlY1.toFloat()
            coords[2] = cubic.ctrlX2.toFloat()
            coords[3] = cubic.ctrlY2.toFloat()
            coords[4] = cubic.x2.toFloat()
            coords[5] = cubic.y2.toFloat()
            type = PathIterator.SEG_CUBICTO
        }

        affine?.transform(coords, 0, coords, 0, if (index == 0) 1 else 3)

        return type
    }

    override fun currentSegment(coords: DoubleArray): Int {
        if (isDone) {
            throw NoSuchElementException("cubic iterator iterator out of bounds")
        }
        val type: Int
        if (index == 0) {
            coords[0] = cubic.x1
            coords[1] = cubic.y1
            type = PathIterator.SEG_MOVETO
        } else {
            coords[0] = cubic.ctrlX1
            coords[1] = cubic.ctrlY1
            coords[2] = cubic.ctrlX2
            coords[3] = cubic.ctrlY2
            coords[4] = cubic.x2
            coords[5] = cubic.y2
            type = PathIterator.SEG_CUBICTO
        }

        affine?.transform(coords, 0, coords, 0, if (index == 0) 1 else 3)

        return type
    }
}
