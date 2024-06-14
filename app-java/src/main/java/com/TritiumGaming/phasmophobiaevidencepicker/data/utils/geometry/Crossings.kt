package com.TritiumGaming.phasmophobiaevidencepicker.data.utils.geometry

import com.TritiumGaming.phasmophobiaevidencepicker.data.utils.geometry.Curve.Companion.insertCubic
import com.TritiumGaming.phasmophobiaevidencepicker.data.utils.geometry.Curve.Companion.insertQuad
import java.util.Vector
import kotlin.math.max
import kotlin.math.min

abstract class Crossings(
    var xLo: Double, var yLo: Double, var xHi: Double, var yHi: Double
) {
    var limit: Int = 0
    var yranges: DoubleArray = DoubleArray(10)

    abstract fun record(ystart: Double, yend: Double, direction: Int)

    fun print() {
        println("Crossings [")
        println("  bounds = [" + yLo + ", " + yHi + "]")
        var i = 0
        while (i < limit) {
            println("  [" + yranges[i] + ", " + yranges[i + 1] + "]")
            i += 2
        }
        println("]")
    }

    val isEmpty: Boolean
        get() = (limit == 0)

    abstract fun covers(ystart: Double, yend: Double): Boolean

    fun accumulateLine(
        x0: Double, y0: Double,
        x1: Double, y1: Double
    ): Boolean {
        return if (y0 <= y1) {
            accumulateLine(x0, y0, x1, y1, 1)
        } else {
            accumulateLine(x1, y1, x0, y0, -1)
        }
    }

    fun accumulateLine(
        x0: Double, y0: Double,
        x1: Double, y1: Double,
        direction: Int
    ): Boolean {
        if (yHi <= y0 || yLo >= y1) {
            return false
        }
        if (x0 >= xHi && x1 >= xHi) {
            return false
        }
        if (y0 == y1) {
            return (x0 >= xLo || x1 >= xLo)
        }
        val xstart: Double
        val ystart: Double
        val xend: Double
        val yend: Double
        val dx = (x1 - x0)
        val dy = (y1 - y0)
        if (y0 < yLo) {
            xstart = x0 + (yLo - y0) * dx / dy
            ystart = yLo
        } else {
            xstart = x0
            ystart = y0
        }
        if (yHi < y1) {
            xend = x0 + (yHi - y0) * dx / dy
            yend = yHi
        } else {
            xend = x1
            yend = y1
        }
        if (xstart >= xHi && xend >= xHi) {
            return false
        }
        if (xstart > xLo || xend > xLo) {
            return true
        }
        record(ystart, yend, direction)
        return false
    }

    private val tmp = Vector<Curve?>()

    fun accumulateQuad(x0: Double, y0: Double, coords: DoubleArray): Boolean {
        if (y0 < yLo && coords[1] < yLo && coords[3] < yLo) {
            return false
        }
        if (y0 > yHi && coords[1] > yHi && coords[3] > yHi) {
            return false
        }
        if (x0 > xHi && coords[0] > xHi && coords[2] > xHi) {
            return false
        }
        if (x0 < xLo && coords[0] < xLo && coords[2] < xLo) {
            if (y0 < coords[3]) {
                record(max(y0, yLo), min(coords[3], yHi), 1)
            } else if (y0 > coords[3]) {
                record(max(coords[3], yLo), min(y0, yHi), -1)
            }
            return false
        }
        insertQuad(tmp, x0, y0, coords)
        val enum_ = tmp.elements()
        while (enum_.hasMoreElements()) {
            val c = enum_.nextElement()
            if (c!!.accumulateCrossings(this)) {
                return true
            }
        }
        tmp.clear()
        return false
    }

    fun accumulateCubic(x0: Double, y0: Double, coords: DoubleArray): Boolean {
        if (y0 < yLo && coords[1] < yLo && coords[3] < yLo && coords[5] < yLo) {
            return false
        }
        if (y0 > yHi && coords[1] > yHi && coords[3] > yHi && coords[5] > yHi) {
            return false
        }
        if (x0 > xHi && coords[0] > xHi && coords[2] > xHi && coords[4] > xHi) {
            return false
        }
        if (x0 < xLo && coords[0] < xLo && coords[2] < xLo && coords[4] < xLo) {
            if (y0 <= coords[5]) {
                record(max(y0, yLo), min(coords[5], yHi), 1)
            } else {
                record(max(coords[5], yLo), min(y0, yHi), -1)
            }
            return false
        }
        insertCubic(tmp, x0, y0, coords)
        val enum_ = tmp.elements()
        while (enum_.hasMoreElements()) {
            val c = enum_.nextElement()
            if (c!!.accumulateCrossings(this)) {
                return true
            }
        }
        tmp.clear()
        return false
    }

    class EvenOdd(xlo: Double, ylo: Double, xhi: Double, yhi: Double) :
        Crossings(xlo, ylo, xhi, yhi) {
        override fun covers(ystart: Double, yend: Double): Boolean {
            return (limit == 2 && yranges[0] <= ystart && yranges[1] >= yend)
        }

        override fun record(ystart: Double, yend: Double, direction: Int) {
            var ystart = ystart
            var yend = yend
            if (ystart >= yend) {
                return
            }
            var from = 0
            // Quickly jump over all pairs that are completely "above"
            while (from < limit && ystart > yranges[from + 1]) {
                from += 2
            }
            var to = from
            while (from < limit) {
                val yrlo = yranges[from++]
                val yrhi = yranges[from++]
                if (yend < yrlo) {
                    // Quickly handle insertion of the new range
                    yranges[to++] = ystart
                    yranges[to++] = yend
                    ystart = yrlo
                    yend = yrhi
                    continue
                }
                // The ranges overlap - sort, collapse, insert, iterate
                var yll: Double
                var ylh: Double
                var yhl: Double
                var yhh: Double
                if (ystart < yrlo) {
                    yll = ystart
                    ylh = yrlo
                } else {
                    yll = yrlo
                    ylh = ystart
                }
                if (yend < yrhi) {
                    yhl = yend
                    yhh = yrhi
                } else {
                    yhl = yrhi
                    yhh = yend
                }
                if (ylh == yhl) {
                    ystart = yll
                    yend = yhh
                } else {
                    if (ylh > yhl) {
                        ystart = yhl
                        yhl = ylh
                        ylh = ystart
                    }
                    if (yll != ylh) {
                        yranges[to++] = yll
                        yranges[to++] = ylh
                    }
                    ystart = yhl
                    yend = yhh
                }
                if (ystart >= yend) {
                    break
                }
            }
            if (to < from && from < limit) {
                System.arraycopy(yranges, from, yranges, to, limit - from)
            }
            to += (limit - from)
            if (ystart < yend) {
                if (to >= yranges.size) {
                    val newranges = DoubleArray(to + 10)
                    System.arraycopy(yranges, 0, newranges, 0, to)
                    yranges = newranges
                }
                yranges[to++] = ystart
                yranges[to++] = yend
            }
            limit = to
        }
    }

    class NonZero(xlo: Double, ylo: Double, xhi: Double, yhi: Double) :
        Crossings(xlo, ylo, xhi, yhi) {
        private var crosscounts: IntArray

        init {
            crosscounts = IntArray(yranges.size / 2)
        }

        override fun covers(ystart: Double, yend: Double): Boolean {
            var ystart = ystart
            var i = 0
            while (i < limit) {
                val ylo = yranges[i++]
                val yhi = yranges[i++]
                if (ystart >= yhi) {
                    continue
                }
                if (ystart < ylo) {
                    return false
                }
                if (yend <= yhi) {
                    return true
                }
                ystart = yhi
            }
            return (ystart >= yend)
        }

        fun remove(cur: Int) {
            limit -= 2
            val rem = limit - cur
            if (rem > 0) {
                System.arraycopy(yranges, cur + 2, yranges, cur, rem)
                System.arraycopy(
                    crosscounts, cur / 2 + 1,
                    crosscounts, cur / 2,
                    rem / 2
                )
            }
        }

        fun insert(cur: Int, lo: Double, hi: Double, dir: Int) {
            val rem = limit - cur
            val oldranges = yranges
            val oldcounts = crosscounts
            if (limit >= yranges.size) {
                yranges = DoubleArray(limit + 10)
                System.arraycopy(oldranges, 0, yranges, 0, cur)
                crosscounts = IntArray((limit + 10) / 2)
                System.arraycopy(oldcounts, 0, crosscounts, 0, cur / 2)
            }
            if (rem > 0) {
                System.arraycopy(oldranges, cur, yranges, cur + 2, rem)
                System.arraycopy(
                    oldcounts, cur / 2,
                    crosscounts, cur / 2 + 1,
                    rem / 2
                )
            }
            yranges[cur] = lo
            yranges[cur + 1] = hi
            crosscounts[cur / 2] = dir
            limit += 2
        }

        override fun record(ystart: Double, yend: Double, direction: Int) {
            var ystart = ystart
            if (ystart >= yend) {
                return
            }
            var cur = 0
            // Quickly jump over all pairs that are completely "above"
            while (cur < limit && ystart > yranges[cur + 1]) {
                cur += 2
            }
            if (cur < limit) {
                var rdir = crosscounts[cur / 2]
                var yrlo = yranges[cur]
                var yrhi = yranges[cur + 1]
                if (yrhi == ystart && rdir == direction) {
                    // Remove the range from the list and collapse it
                    // into the range being inserted.  Note that the
                    // new combined range may overlap the following range
                    // so we must not simply combine the ranges in place
                    // unless we are at the last range.
                    if (cur + 2 == limit) {
                        yranges[cur + 1] = yend
                        return
                    }
                    remove(cur)
                    ystart = yrlo
                    rdir = crosscounts[cur / 2]
                    yrlo = yranges[cur]
                    yrhi = yranges[cur + 1]
                }
                if (yend < yrlo) {
                    // Just insert the new range at the current location
                    insert(cur, ystart, yend, direction)
                    return
                }
                if (yend == yrlo && rdir == direction) {
                    // Just prepend the new range to the current one
                    yranges[cur] = ystart
                    return
                }
                // The ranges must overlap - (yend > yrlo && yrhi > ystart)
                if (ystart < yrlo) {
                    insert(cur, ystart, yrlo, direction)
                    cur += 2
                    ystart = yrlo
                } else if (yrlo < ystart) {
                    insert(cur, yrlo, ystart, rdir)
                    cur += 2
                    yrlo = ystart
                }
                // assert(yrlo == ystart);
                val newdir = rdir + direction
                val newend = min(yend, yrhi)
                if (newdir == 0) {
                    remove(cur)
                } else {
                    crosscounts[cur / 2] = newdir
                    yranges[cur++] = ystart
                    yranges[cur++] = newend
                }
                yrlo = newend
                ystart = yrlo
                if (yrlo < yrhi) {
                    insert(cur, yrlo, yrhi, rdir)
                }
            }
            if (ystart < yend) {
                insert(cur, ystart, yend, direction)
            }
        }
    }

    companion object {
        const val debug: Boolean = false

        fun findCrossings(
            curves: Vector<out Curve>,
            xlo: Double, ylo: Double,
            xhi: Double, yhi: Double
        ): Crossings? {
            val cross: Crossings = EvenOdd(xlo, ylo, xhi, yhi)
            val enum_ = curves.elements()
            while (enum_.hasMoreElements()) {
                val c = enum_.nextElement()
                if (c.accumulateCrossings(cross)) {
                    return null
                }
            }
            if (debug) {
                cross.print()
            }
            return cross
        }

        fun findCrossings(
            pi: PathIterator,
            xlo: Double, ylo: Double,
            xhi: Double, yhi: Double
        ): Crossings? {
            val cross = if (pi.windingRule == PathIterator.WIND_EVEN_ODD) {
                EvenOdd(
                    xlo,
                    ylo,
                    xhi,
                    yhi
                )
            } else {
                NonZero(
                    xlo,
                    ylo,
                    xhi,
                    yhi
                )
            }
            // coords array is big enough for holding:
            //     coordinates returned from currentSegment (6)
            //     OR
            //         two subdivided quadratic curves (2+4+4=10)
            //         AND
            //             0-1 horizontal splitting parameters
            //             OR
            //             2 parametric equation derivative coefficients
            //     OR
            //         three subdivided cubic curves (2+6+6+6=20)
            //         AND
            //             0-2 horizontal splitting parameters
            //             OR
            //             3 parametric equation derivative coefficients
            val coords = DoubleArray(23)
            var movx = 0.0
            var movy = 0.0
            var curx = 0.0
            var cury = 0.0
            var newx: Double
            var newy: Double
            while (!pi.isDone) {
                val type = pi.currentSegment(coords)
                when (type) {
                    PathIterator.SEG_MOVETO -> {
                        if (movy != cury &&
                            cross.accumulateLine(curx, cury, movx, movy)
                        ) {
                            return null
                        }
                        run {
                            curx = coords[0]
                            movx = curx
                        }
                        run {
                            cury = coords[1]
                            movy = cury
                        }
                    }

                    PathIterator.SEG_LINETO -> {
                        newx = coords[0]
                        newy = coords[1]
                        if (cross.accumulateLine(curx, cury, newx, newy)) {
                            return null
                        }
                        curx = newx
                        cury = newy
                    }

                    PathIterator.SEG_QUADTO -> {
                        newx = coords[2]
                        newy = coords[3]
                        if (cross.accumulateQuad(curx, cury, coords)) {
                            return null
                        }
                        curx = newx
                        cury = newy
                    }

                    PathIterator.SEG_CUBICTO -> {
                        newx = coords[4]
                        newy = coords[5]
                        if (cross.accumulateCubic(curx, cury, coords)) {
                            return null
                        }
                        curx = newx
                        cury = newy
                    }

                    PathIterator.SEG_CLOSE -> {
                        if (movy != cury &&
                            cross.accumulateLine(curx, cury, movx, movy)
                        ) {
                            return null
                        }
                        curx = movx
                        cury = movy
                    }
                }
                pi.next()
            }
            if (movy != cury) {
                if (cross.accumulateLine(curx, cury, movx, movy)) {
                    return null
                }
            }
            if (debug) {
                cross.print()
            }
            return cross
        }
    }
}
