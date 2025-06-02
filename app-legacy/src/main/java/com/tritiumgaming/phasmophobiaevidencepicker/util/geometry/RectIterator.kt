package com.tritiumgaming.phasmophobiaevidencepicker.util.geometry

internal class RectIterator(
    r: Rectangle2D,
    var affine: AffineTransform?
) : PathIterator {

    var x: Double = r.x
    var y: Double = r.y
    var w: Double = r.width
    var h: Double = r.height
    var index: Int = 0

    init {
        if (w < 0 || h < 0) {
            index = 6
        }
    }

    override val windingRule: Int
        get() = PathIterator.WIND_NON_ZERO
    override val isDone: Boolean
        get() = index > 5

    override fun next() {
        index++
    }

    override fun currentSegment(coords: FloatArray): Int {
        if (isDone) {
            throw NoSuchElementException("rect iterator out of bounds")
        }
        if (index == 5) {
            return PathIterator.SEG_CLOSE
        }
        coords[0] = x.toFloat()
        coords[1] = y.toFloat()
        if (index == 1 || index == 2) {
            coords[0] += w.toFloat()
        }
        if (index == 2 || index == 3) {
            coords[1] += h.toFloat()
        }
        affine?.transform(coords, 0, coords, 0, 1)

        return (if (index == 0) PathIterator.SEG_MOVETO else PathIterator.SEG_LINETO)
    }

    override fun currentSegment(coords: DoubleArray): Int {
        if (isDone) {
            throw NoSuchElementException("rect iterator out of bounds")
        }
        if (index == 5) {
            return PathIterator.SEG_CLOSE
        }
        coords[0] = x
        coords[1] = y
        if (index == 1 || index == 2) {
            coords[0] += w
        }
        if (index == 2 || index == 3) {
            coords[1] += h
        }
        affine?.transform(coords, 0, coords, 0, 1)

        return (if (index == 0) PathIterator.SEG_MOVETO else PathIterator.SEG_LINETO)
    }
}
