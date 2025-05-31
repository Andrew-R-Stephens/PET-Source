package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.util.graphics.geometry

/**
 * @noinspection ALL
 */
internal class LineIterator(
    var line: Line2D,
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
            throw NoSuchElementException("line iterator out of bounds")
        }
        val type: Int
        if (index == 0) {
            coords[0] = line.x1.toFloat()
            coords[1] = line.y1.toFloat()
            type = PathIterator.SEG_MOVETO
        } else {
            coords[0] = line.x2.toFloat()
            coords[1] = line.y2.toFloat()
            type = PathIterator.SEG_LINETO
        }
        if (affine != null) {
            affine!!.transform(coords, 0, coords, 0, 1)
        }
        return type
    }

    override fun currentSegment(coords: DoubleArray): Int {
        if (isDone) {
            throw NoSuchElementException("line iterator out of bounds")
        }
        val type: Int
        if (index == 0) {
            coords[0] = line.x1
            coords[1] = line.y1
            type = PathIterator.SEG_MOVETO
        } else {
            coords[0] = line.x2
            coords[1] = line.y2
            type = PathIterator.SEG_LINETO
        }
        if (affine != null) {
            affine!!.transform(coords, 0, coords, 0, 1)
        }
        return type
    }
}
