package com.tritiumgaming.core.common.graphics.geometry

import kotlin.math.max
import kotlin.math.min

internal class Order1(
    override val xTop: Double,
    override val yTop: Double,
    override val xBot: Double,
    override val yBot: Double,
    direction: Int
) : Curve(direction) {

    override val order: Int
        get() = 1

    override var xMin: Double = 0.0
    override var xMax: Double = 0.0
    override val x0: Double
        get() = if ((direction == INCREASING)) xTop else xBot
    override val y0: Double
        get() = if ((direction == INCREASING)) yTop else yBot
    override val x1: Double
        get() = if ((direction == DECREASING)) xTop else xBot
    override val y1: Double
        get() = if ((direction == DECREASING)) yTop else yBot

    init {
        if (xTop < xBot) {
            this.xMin = xTop
            this.xMax = xBot
        } else {
            this.xMin = xBot
            this.xMax = xTop
        }
    }

    override fun XforY(y: Double): Double {
        if (xTop == xBot || y <= yTop) {
            return xTop
        }
        if (y >= yBot) {
            return xBot
        }
        // assert(y0 != y1); /* No horizontal lines... */
        return (xTop + (y - yTop) * (xBot - xTop) / (yBot - yTop))
    }

    override fun TforY(y: Double): Double {
        if (y <= yTop) {
            return 0.0
        }
        if (y >= yBot) {
            return 1.0
        }
        return (y - yTop) / (yBot - yTop)
    }

    override fun XforT(t: Double): Double {
        return xTop + t * (xBot - xTop)
    }

    override fun YforT(t: Double): Double {
        return yTop + t * (yBot - yTop)
    }

    override fun dXforT(t: Double, deriv: Int): Double {
        return when (deriv) {
            0 -> xTop + t * (xBot - xTop)
            1 -> xBot - xTop
            else -> 0.0
        }
    }

    override fun dYforT(t: Double, deriv: Int): Double {
        return when (deriv) {
            0 -> yTop + t * (yBot - yTop)
            1 -> yBot - yTop
            else -> 0.0
        }
    }

    override fun nextVertical(t0: Double, t1: Double): Double {
        return t1
    }

    override fun accumulateCrossings(c: Crossings): Boolean {
        val xlo = c.xLo
        val ylo = c.yLo
        val xhi = c.xHi
        val yhi = c.yHi
        if (xMin >= xhi) {
            return false
        }
        val xstart: Double
        val ystart: Double
        val xend: Double
        val yend: Double
        if (yTop < ylo) {
            if (yBot <= ylo) {
                return false
            }
            ystart = ylo
            xstart = XforY(ylo)
        } else {
            if (yTop >= yhi) {
                return false
            }
            ystart = yTop
            xstart = xTop
        }
        if (yBot > yhi) {
            yend = yhi
            xend = XforY(yhi)
        } else {
            yend = yBot
            xend = xBot
        }
        if (xstart >= xhi && xend >= xhi) {
            return false
        }
        if (xstart > xlo || xend > xlo) {
            return true
        }
        c.record(ystart, yend, direction)
        return false
    }

    override fun enlarge(r: Rectangle2D?) {
        r?.add(xTop, yTop)
        r?.add(xBot, yBot)
    }

    override fun getSubCurve(ystart: Double, yend: Double, dir: Int): Curve {
        if (ystart == yTop && yend == yBot) {
            return getWithDirection(dir)
        }
        if (xTop == xBot) {
            return Order1(xTop, ystart, xBot, yend, dir)
        }
        val num = xTop - xBot
        val denom = yTop - yBot
        val xstart = (xTop + (ystart - yTop) * num / denom)
        val xend = (xTop + (yend - yTop) * num / denom)
        return Order1(xstart, ystart, xend, yend, dir)
    }

    override val reversedCurve: Curve
        get() = Order1(xTop, yTop, xBot, yBot, -direction)

    /**
     * @noinspection PatternVariableCanBeUsed
     */
    override fun compareTo(other: Curve, yrange: DoubleArray): Int {
        if (other !is Order1) {
            return super.compareTo(other, yrange)
        }
        val c1 = other
        if (yrange[1] <= yrange[0]) {
            throw InternalError("yrange already screwed up...")
        }
        yrange[1] = min(min(yrange[1], yBot), c1.yBot)
        if (yrange[1] <= yrange[0]) {
            throw InternalError("backstepping from " + yrange[0] + " to " + yrange[1])
        }
        if (xMax <= c1.xMin) {
            return if ((xMin == c1.xMax)) 0 else -1
        }
        if (xMin >= c1.xMax) {
            return 1
        }
        /*
         * If "this" is curve A and "other" is curve B, then...
         * xA(y) = x0A + (y - y0A) (x1A - x0A) / (y1A - y0A)
         * xB(y) = x0B + (y - y0B) (x1B - x0B) / (y1B - y0B)
         * xA(y) == xB(y)
         * x0A + (y - y0A) (x1A - x0A) / (y1A - y0A)
         *    == x0B + (y - y0B) (x1B - x0B) / (y1B - y0B)
         * 0 == x0A (y1A - y0A) (y1B - y0B) + (y - y0A) (x1A - x0A) (y1B - y0B)
         *    - x0B (y1A - y0A) (y1B - y0B) - (y - y0B) (x1B - x0B) (y1A - y0A)
         * 0 == (x0A - x0B) (y1A - y0A) (y1B - y0B)
         *    + (y - y0A) (x1A - x0A) (y1B - y0B)
         *    - (y - y0B) (x1B - x0B) (y1A - y0A)
         * If (dxA == x1A - x0A), etc...
         * 0 == (x0A - x0B) * dyA * dyB
         *    + (y - y0A) * dxA * dyB
         *    - (y - y0B) * dxB * dyA
         * 0 == (x0A - x0B) * dyA * dyB
         *    + y * dxA * dyB - y0A * dxA * dyB
         *    - y * dxB * dyA + y0B * dxB * dyA
         * 0 == (x0A - x0B) * dyA * dyB
         *    + y * dxA * dyB - y * dxB * dyA
         *    - y0A * dxA * dyB + y0B * dxB * dyA
         * 0 == (x0A - x0B) * dyA * dyB
         *    + y * (dxA * dyB - dxB * dyA)
         *    - y0A * dxA * dyB + y0B * dxB * dyA
         * y == ((x0A - x0B) * dyA * dyB
         *       - y0A * dxA * dyB + y0B * dxB * dyA)
         *    / (-(dxA * dyB - dxB * dyA))
         * y == ((x0A - x0B) * dyA * dyB
         *       - y0A * dxA * dyB + y0B * dxB * dyA)
         *    / (dxB * dyA - dxA * dyB)
         */
        val dxa = xBot - xTop
        val dya = yBot - yTop
        val dxb = c1.xBot - c1.xTop
        val dyb = c1.yBot - c1.yTop
        val denom = dxb * dya - dxa * dyb
        var y: Double
        if (denom != 0.0) {
            val num = ((xTop - c1.xTop) * dya * dyb
                    - this.yTop * dxa * dyb
                    + c1.yTop * dxb * dya)
            y = num / denom
            if (y <= yrange[0]) {
                // intersection is above us
                // Use bottom-most common y for comparison
                y = min(yBot, c1.yBot)
            } else {
                // intersection is below the top of our range
                if (y < yrange[1]) {
                    // If intersection is in our range, adjust valid range
                    yrange[1] = y
                }
                // Use top-most common y for comparison
                y = max(yTop, c1.yTop)
            }
        } else {
            // lines are parallel, choose any common y for comparison
            // Note - prefer an endpoint for speed of calculating the X
            // (see shortcuts in Order1.XforY())
            y = max(yTop, c1.yTop)
        }
        return orderof(XforY(y), c1.XforY(y))
    }

    override fun getSegment(coords: DoubleArray?): Int {
        if (direction == INCREASING) {
            coords!![0] = xBot
            coords[1] = yBot
        } else {
            coords!![0] = xTop
            coords[1] = yTop
        }
        return PathIterator.SEG_LINETO
    }
}
