package com.tritiumgaming.phasmophobiaevidencepicker.utils.geometry

import kotlin.math.sqrt

class FlatteningPathIterator(
    src: PathIterator, flatness: Double,
    limit: Int = 10
) : PathIterator {
    var src: PathIterator // The source iterator
    var squareflat: Double // Square of the flatness parameter

    // for testing against squared lengths
    var recursionLimit: Int // Maximum number of recursion levels

    var hold: DoubleArray = DoubleArray(14) // The cache of interpolated coords

    // Note that this must be long enough
    // to store a full cubic segment and
    // a relative cubic segment to avoid
    // aliasing when copying the coords
    // of a curve to the end of the array.
    // This is also serendipitously equal
    // to the size of a full quad segment
    // and 2 relative quad segments.
    var curx: Double = 0.0
    var cury: Double = 0.0 // The ending x,y of the last segment

    var movx: Double = 0.0
    var movy: Double = 0.0 // The x,y of the last move segment

    var holdType: Int = 0 // The type of the curve being held

    // for interpolation
    var holdEnd: Int = 0 // The index of the last curve segment

    // being held for interpolation
    var holdIndex: Int = 0 // The index of the curve segment

    // that was last interpolated.  This
    // is the curve segment ready to be
    // returned in the next call to
    // currentSegment().
    var levels: IntArray // The recursion level at which

    // each curve being held in storage
    // was generated.
    var levelIndex: Int = 0 // The index of the entry in the

    // levels array of the curve segment
    // at the holdIndex
    override var isDone: Boolean = false // True when iteration is done

    init {
        require(!(flatness < 0.0)) { "flatness must be >= 0" }
        require(limit >= 0) { "limit must be >= 0" }
        this.src = src
        this.squareflat = flatness * flatness
        this.recursionLimit = limit
        this.levels = IntArray(limit + 1)
        // prime the first path segment
        next(false)
    }

    val flatness: Double
        get() = sqrt(squareflat)

    override val windingRule: Int
        get() = src.windingRule

    /*
     * Ensures that the hold array can hold up to (want) more values.
     * It is currently holding (hold.length - holdIndex) values.
     */
    fun ensureHoldCapacity(want: Int) {
        if (holdIndex - want < 0) {
            val have = hold.size - holdIndex
            val newsize = hold.size + GROW_SIZE
            val newhold = DoubleArray(newsize)
            System.arraycopy(
                hold, holdIndex,
                newhold, holdIndex + GROW_SIZE,
                have
            )
            hold = newhold
            holdIndex += GROW_SIZE
            holdEnd += GROW_SIZE
        }
    }

    override fun next() {
        next(true)
    }

    private fun next(doNext: Boolean) {
        var level: Int

        if (holdIndex >= holdEnd) {
            if (doNext) {
                src.next()
            }
            if (src.isDone) {
                isDone = true
                return
            }
            holdType = src.currentSegment(hold)
            levelIndex = 0
            levels[0] = 0
        }

        when (holdType) {
            PathIterator.SEG_MOVETO, PathIterator.SEG_LINETO -> {
                curx = hold[0]
                cury = hold[1]
                if (holdType == PathIterator.SEG_MOVETO) {
                    movx = curx
                    movy = cury
                }
                holdIndex = 0
                holdEnd = 0
            }

            PathIterator.SEG_CLOSE -> {
                curx = movx
                cury = movy
                holdIndex = 0
                holdEnd = 0
            }

            PathIterator.SEG_QUADTO -> {
                if (holdIndex >= holdEnd) {
                    // Move the coordinates to the end of the array.
                    holdIndex = hold.size - 6
                    holdEnd = hold.size - 2
                    hold[holdIndex] = curx
                    hold[holdIndex + 1] = cury
                    hold[holdIndex + 2] = hold[0]
                    hold[holdIndex + 3] = hold[1]
                    curx = hold[2]
                    hold[holdIndex + 4] = curx
                    cury = hold[3]
                    hold[holdIndex + 5] = cury
                }

                level = levels[levelIndex]
                while (level < recursionLimit) {
                    if (QuadCurve2D.getFlatnessSq(hold, holdIndex) < squareflat) {
                        break
                    }

                    ensureHoldCapacity(4)
                    QuadCurve2D.subdivide(
                        hold, holdIndex,
                        hold, holdIndex - 4,
                        hold, holdIndex
                    )
                    holdIndex -= 4

                    // Now that we have subdivided, we have constructed
                    // two curves of one depth lower than the original
                    // curve.  One of those curves is in the place of
                    // the former curve and one of them is in the next
                    // set of held coordinate slots.  We now set both
                    // curves level values to the next higher level.
                    level++
                    levels[levelIndex] = level
                    levelIndex++
                    levels[levelIndex] = level
                }

                // This curve segment is flat enough, or it is too deep
                // in recursion levels to try to flatten any more.  The
                // two coordinates at holdIndex+4 and holdIndex+5 now
                // contain the endpoint of the curve which can be the
                // endpoint of an approximating line segment.
                holdIndex += 4
                levelIndex--
            }

            PathIterator.SEG_CUBICTO -> {
                if (holdIndex >= holdEnd) {
                    // Move the coordinates to the end of the array.
                    holdIndex = hold.size - 8
                    holdEnd = hold.size - 2
                    hold[holdIndex] = curx
                    hold[holdIndex + 1] = cury
                    hold[holdIndex + 2] = hold[0]
                    hold[holdIndex + 3] = hold[1]
                    hold[holdIndex + 4] = hold[2]
                    hold[holdIndex + 5] = hold[3]
                    curx = hold[4]
                    hold[holdIndex + 6] = curx
                    cury = hold[5]
                    hold[holdIndex + 7] = cury
                }

                level = levels[levelIndex]
                while (level < recursionLimit) {
                    if (CubicCurve2D.getFlatnessSq(hold, holdIndex) < squareflat) {
                        break
                    }

                    ensureHoldCapacity(6)
                    CubicCurve2D.subdivide(
                        hold, holdIndex,
                        hold, holdIndex - 6,
                        hold, holdIndex
                    )
                    holdIndex -= 6

                    // Now that we have subdivided, we have constructed
                    // two curves of one depth lower than the original
                    // curve.  One of those curves is in the place of
                    // the former curve and one of them is in the next
                    // set of held coordinate slots.  We now set both
                    // curves level values to the next higher level.
                    level++
                    levels[levelIndex] = level
                    levelIndex++
                    levels[levelIndex] = level
                }

                // This curve segment is flat enough, or it is too deep
                // in recursion levels to try to flatten any more.  The
                // two coordinates at holdIndex+6 and holdIndex+7 now
                // contain the endpoint of the curve which can be the
                // endpoint of an approximating line segment.
                holdIndex += 6
                levelIndex--
            }
        }
    }

    override fun currentSegment(coords: FloatArray): Int {
        if (isDone) {
            throw NoSuchElementException("flattening iterator out of bounds")
        }
        var type = holdType
        if (type != PathIterator.SEG_CLOSE) {
            coords[0] = hold[holdIndex].toFloat()
            coords[1] = hold[holdIndex + 1].toFloat()
            if (type != PathIterator.SEG_MOVETO) {
                type = PathIterator.SEG_LINETO
            }
        }
        return type
    }

    override fun currentSegment(coords: DoubleArray): Int {
        if (isDone) {
            throw NoSuchElementException("flattening iterator out of bounds")
        }
        var type = holdType
        if (type != PathIterator.SEG_CLOSE) {
            coords[0] = hold[holdIndex]
            coords[1] = hold[holdIndex + 1]
            if (type != PathIterator.SEG_MOVETO) {
                type = PathIterator.SEG_LINETO
            }
        }
        return type
    }

    companion object {
        const val GROW_SIZE: Int = 24 // Multiple of cubic & quad curve size
    }
}
