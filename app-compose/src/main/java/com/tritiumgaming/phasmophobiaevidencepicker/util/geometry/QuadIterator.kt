package com.tritiumgaming.phasmophobiaevidencepicker.util.geometry

internal class QuadIterator(
    var quad: QuadCurve2D,
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
            throw NoSuchElementException("quad iterator iterator out of bounds")
        }
        val type: Int
        if (index == 0) {
            coords[0] = quad.x1.toFloat()
            coords[1] = quad.y1.toFloat()
            type = PathIterator.SEG_MOVETO
        } else {
            coords[0] = quad.ctrlX.toFloat()
            coords[1] = quad.ctrlY.toFloat()
            coords[2] = quad.x2.toFloat()
            coords[3] = quad.y2.toFloat()
            type = PathIterator.SEG_QUADTO
        }
        if (affine != null) {
            affine!!.transform(coords, 0, coords, 0, if (index == 0) 1 else 2)
        }
        return type
    }

    override fun currentSegment(coords: DoubleArray): Int {
        if (isDone) {
            throw NoSuchElementException("quad iterator iterator out of bounds")
        }
        val type: Int
        if (index == 0) {
            coords[0] = quad.x1
            coords[1] = quad.y1
            type = PathIterator.SEG_MOVETO
        } else {
            coords[0] = quad.ctrlX
            coords[1] = quad.ctrlY
            coords[2] = quad.x2
            coords[3] = quad.y2
            type = PathIterator.SEG_QUADTO
        }
        if (affine != null) {
            affine!!.transform(coords, 0, coords, 0, if (index == 0) 1 else 2)
        }
        return type
    }
}
