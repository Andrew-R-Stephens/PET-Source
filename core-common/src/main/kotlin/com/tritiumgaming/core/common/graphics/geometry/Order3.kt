package com.tritiumgaming.core.common.graphics.geometry

import com.tritiumgaming.core.common.graphics.geometry.QuadCurve2D.Companion.solveQuadratic
import java.util.Vector
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

internal class Order3(
    x0: Double, y0: Double,
    cx0: Double, cy0: Double,
    cx1: Double, cy1: Double,
    x1: Double, y1: Double,
    direction: Int
) : Curve(direction) {
    override val xTop: Double
    override val yTop: Double
    private var cx0: Double
        get() = if ((direction == INCREASING)) field else cx1
    private var cy0: Double
        get() = if ((direction == INCREASING)) field else cy1
    private var cx1: Double
        get() = if ((direction == DECREASING)) cx0 else field
    private var cy1: Double
        get() = if ((direction == DECREASING)) cy0 else field
    override val xBot: Double
    override val yBot: Double

    override val xMin: Double
    override val xMax: Double
    override val x0: Double
        get() = if ((direction == INCREASING)) xTop else xBot
    override val y0: Double
        get() = if ((direction == INCREASING)) yTop else yBot
    override val x1: Double
        get() = if ((direction == DECREASING)) xTop else xBot
    override val y1: Double
        get() = if ((direction == DECREASING)) yTop else yBot

    private val xcoeff0: Double
    private val xcoeff1: Double
    private val xcoeff2: Double
    private val xcoeff3: Double

    private val ycoeff0: Double
    private val ycoeff1: Double
    private val ycoeff2: Double
    private val ycoeff3: Double

    override val order: Int
        get() = 3

    private var TforY1 = 0.0
    private var YforT1: Double
    private var TforY2 = 0.0
    private var YforT2: Double
    private var TforY3 = 0.0
    private var YforT3: Double

    init {
        var cy0 = cy0
        var cy1 = cy1
        // REMIND: Better accuracy in the root finding methods would
        //  ensure that cys are in range.  As it stands, they are never
        //  more than "1 mantissa bit" out of range...
        if (cy0 < y0) cy0 = y0
        if (cy1 > y1) cy1 = y1
        this.xTop = x0
        this.yTop = y0
        this.cx0 = cx0
        this.cy0 = cy0
        this.cx1 = cx1
        this.cy1 = cy1
        this.xBot = x1
        this.yBot = y1
        xMin = min(min(x0, x1), min(cx0, cx1))
        xMax = max(max(x0, x1), max(cx0, cx1))
        xcoeff0 = x0
        xcoeff1 = (cx0 - x0) * 3.0
        xcoeff2 = (cx1 - cx0 - cx0 + x0) * 3.0
        xcoeff3 = x1 - (cx1 - cx0) * 3.0 - x0
        ycoeff0 = y0
        ycoeff1 = (cy0 - y0) * 3.0
        ycoeff2 = (cy1 - cy0 - cy0 + y0) * 3.0
        ycoeff3 = y1 - (cy1 - cy0) * 3.0 - y0
        YforT3 = y0
        YforT2 = YforT3
        YforT1 = YforT2
    }

    /**
     * Solve the cubic whose coefficients are in the a,b,c,d fields and
     * return the first root in the range [0, 1].
     * The cubic solved is represented by the equation:
     *     x^3 + (ycoeff2)x^2 + (ycoeff1)x + (ycoeff0) = y
     * @return the first valid root (in the range [0, 1])
     */
    override fun TforY(y: Double): Double {
        if (y <= yTop) return 0.0
        if (y >= yBot) return 1.0
        if (y == YforT1) return TforY1
        if (y == YforT2) return TforY2
        if (y == YforT3) return TforY3
        // From Numerical Recipes, 5.6, Quadratic and Cubic Equations
        if (ycoeff3 == 0.0) {
            // The cubic degenerated to quadratic (or line or ...).
            return Order2.TforY(y, ycoeff0, ycoeff1, ycoeff2)
        }
        val a = ycoeff2 / ycoeff3
        val b = ycoeff1 / ycoeff3
        val c = (ycoeff0 - y) / ycoeff3
        val roots = 0
        var Q = (a * a - 3.0 * b) / 9.0
        var R = (2.0 * a * a * a - 9.0 * a * b + 27.0 * c) / 54.0
        val R2 = R * R
        val Q3 = Q * Q * Q
        val a_3 = a / 3.0
        var t: Double
        if (R2 < Q3) {
            val theta = acos(R / sqrt(Q3))
            Q = -2.0 * sqrt(Q)
            t = refine(a, b, c, y, Q * cos(theta / 3.0) - a_3)
            if (t < 0) {
                t = refine(
                    a, b, c, y,
                    Q * cos((theta + Math.PI * 2.0) / 3.0) - a_3
                )
            }
            if (t < 0) {
                t = refine(
                    a, b, c, y,
                    Q * cos((theta - Math.PI * 2.0) / 3.0) - a_3
                )
            }
        } else {
            val neg = (R < 0.0)
            val S = sqrt(R2 - Q3)
            if (neg) {
                R = -R
            }
            var A: Double = (R + S).pow(1.0 / 3.0)
            if (!neg) {
                A = -A
            }
            val B = if ((A == 0.0)) 0.0 else (Q / A)
            t = refine(a, b, c, y, (A + B) - a_3)
        }
        if (t < 0) {
            //throw new InternalError("bad t");
            var t0 = 0.0
            var t1 = 1.0
            while (true) {
                t = (t0 + t1) / 2
                if (t == t0 || t == t1) {
                    break
                }
                val yt = YforT(t)
                if (yt < y) {
                    t0 = t
                } else if (yt > y) {
                    t1 = t
                } else {
                    break
                }
            }
        }
        if (t >= 0) {
            TforY3 = TforY2
            YforT3 = YforT2
            TforY2 = TforY1
            YforT2 = YforT1
            TforY1 = t
            YforT1 = y
        }
        return t
    }

    fun refine(
        a: Double, b: Double, c: Double,
        target: Double, t: Double
    ): Double {
        var t = t
        if (t < -0.1 || t > 1.1) {
            return (-1).toDouble()
        }
        var y = YforT(t)
        var t0: Double
        var t1: Double
        if (y < target) {
            t0 = t
            t1 = 1.0
        } else {
            t0 = 0.0
            t1 = t
        }
        val origt = t
        val origy = y
        var useslope = true
        while (y != target) {
            if (!useslope) {
                val t2 = (t0 + t1) / 2
                if (t2 == t0 || t2 == t1) {
                    break
                }
                t = t2
            } else {
                val slope = dYforT(t, 1)
                if (slope == 0.0) {
                    useslope = false
                    continue
                }
                val t2 = t + ((target - y) / slope)
                if (t2 == t || t2 <= t0 || t2 >= t1) {
                    useslope = false
                    continue
                }
                t = t2
            }
            y = YforT(t)
            if (y < target) {
                t0 = t
            } else if (y > target) {
                t1 = t
            } else {
                break
            }
        }
        val verbose = false
        if (false) {
            y = YforT(t)
            val tdiff = diffbits(t, origt)
            val ydiff = diffbits(y, origy)
            val yerr = diffbits(y, target)
            if (yerr > 0 || (verbose && tdiff > 0)) {
                println("target was y = $target")
                println("original was y = $origy, t = $origt")
                println("final was y = $y, t = $t")
                println("t diff is $tdiff")
                println("y diff is $ydiff")
                println("y error is $yerr")
                val tlow = prev(t)
                val ylow = YforT(tlow)
                val thi = next(t)
                val yhi = YforT(thi)
                if (abs(target - ylow) < abs(target - y) ||
                    abs(target - yhi) < abs(target - y)
                ) {
                    println("adjacent y's = [$ylow, $yhi]")
                }
            }
        }
        return if ((t > 1)) -1.0 else t
    }

    override fun XforY(y: Double): Double {
        if (y <= yTop) {
            return xTop
        }
        if (y >= yBot) {
            return xBot
        }
        return XforT(TforY(y))
    }

    override fun XforT(t: Double): Double {
        return (((xcoeff3 * t) + xcoeff2) * t + xcoeff1) * t + xcoeff0
    }

    override fun YforT(t: Double): Double {
        return (((ycoeff3 * t) + ycoeff2) * t + ycoeff1) * t + ycoeff0
    }

    override fun dXforT(t: Double, deriv: Int): Double {
        return when (deriv) {
            0 -> (((xcoeff3 * t) + xcoeff2) * t + xcoeff1) * t + xcoeff0
            1 -> ((3 * xcoeff3 * t) + 2 * xcoeff2) * t + xcoeff1
            2 -> 6 * xcoeff3 * t + 2 * xcoeff2
            3 -> 6 * xcoeff3
            else -> 0.0
        }
    }

    override fun dYforT(t: Double, deriv: Int): Double {
        return when (deriv) {
            0 -> (((ycoeff3 * t) + ycoeff2) * t + ycoeff1) * t + ycoeff0
            1 -> ((3 * ycoeff3 * t) + 2 * ycoeff2) * t + ycoeff1
            2 -> 6 * ycoeff3 * t + 2 * ycoeff2
            3 -> 6 * ycoeff3
            else -> 0.0
        }
    }

    override fun nextVertical(t0: Double, t1: Double): Double {
        var t1 = t1
        val eqn = doubleArrayOf(xcoeff1, 2 * xcoeff2, 3 * xcoeff3)
        val numroots = solveQuadratic(eqn, eqn)
        for (i in 0 until numroots) {
            if (eqn[i] > t0 && eqn[i] < t1) {
                t1 = eqn[i]
            }
        }
        return t1
    }

    override fun enlarge(r: Rectangle2D?) {
        r?.add(xTop, yTop)
        val eqn = doubleArrayOf(xcoeff1, 2 * xcoeff2, 3 * xcoeff3)
        val numroots = solveQuadratic(eqn, eqn)
        for (i in 0 until numroots) {
            val t = eqn[i]
            if (t > 0 && t < 1) {
                r?.add(XforT(t), YforT(t))
            }
        }
        r?.add(xBot, yBot)
    }

    override fun getSubCurve(ystart: Double, yend: Double, dir: Int): Curve {
        if (ystart <= yTop && yend >= yBot) {
            return getWithDirection(dir)
        }
        val eqn = DoubleArray(14)
        var t0: Double
        var t1: Double
        t0 = TforY(ystart)
        t1 = TforY(yend)
        eqn[0] = xTop
        eqn[1] = yTop
        eqn[2] = cx0
        eqn[3] = cy0
        eqn[4] = cx1
        eqn[5] = cy1
        eqn[6] = xBot
        eqn[7] = yBot
        if (t0 > t1) {
            /* This happens in only rare cases where ystart is
             * very near yend and solving for the yend root ends
             * up stepping slightly lower in t than solving for
             * the ystart root.
             * Ideally we might want to skip this tiny little
             * segment and just fudge the surrounding coordinates
             * to bridge the gap left behind, but there is no way
             * to do that from here.  Higher levels could
             * potentially eliminate these tiny "fixup" segments,
             * but not without a lot of extra work on the code that
             * coalesces chains of curves into subpaths.  The
             * simplest solution for now is to just reorder the t
             * values and chop out a miniscule curve piece.
             */
            val t = t0
            t0 = t1
            t1 = t
        }
        if (t1 < 1) {
            split(eqn, 0, t1)
        }
        val i: Int
        if (t0 <= 0) {
            i = 0
        } else {
            split(eqn, 0, t0 / t1)
            i = 6
        }
        return Order3(
            eqn[i], ystart,
            eqn[i + 2], eqn[i + 3],
            eqn[i + 4], eqn[i + 5],
            eqn[i + 6], yend,
            dir
        )
    }

    override val reversedCurve: Curve
        get() = Order3(xTop, yTop, cx0, cy0, cx1, cy1, xBot, yBot, -direction)

    override fun getSegment(coords: DoubleArray?): Int {
        if (direction == INCREASING) {
            coords!![0] = cx0
            coords[1] = cy0
            coords[2] = cx1
            coords[3] = cy1
            coords[4] = xBot
            coords[5] = yBot
        } else {
            coords!![0] = cx1
            coords[1] = cy1
            coords[2] = cx0
            coords[3] = cy0
            coords[4] = xTop
            coords[5] = yTop
        }
        return PathIterator.SEG_CUBICTO
    }

    override fun controlPointString(): String {
        return (("(" + round(
            cx0
        ) + ", " + round(
            cy0
        ) + "), ") +
                ("(" + round(
                    cx1
                ) + ", " + round(
                    cy1
                ) + "), "))
    }

    companion object {
        fun insert(
            curves: Vector<Curve?>, tmp: DoubleArray,
            x0: Double, y0: Double,
            cx0: Double, cy0: Double,
            cx1: Double, cy1: Double,
            x1: Double, y1: Double,
            direction: Int
        ) {
            var numparams = getHorizontalParams(y0, cy0, cy1, y1, tmp)
            if (numparams == 0) {
                // We are using addInstance here to avoid inserting horisontal
                // segments
                addInstance(curves, x0, y0, cx0, cy0, cx1, cy1, x1, y1, direction)
                return
            }
            // Store coordinates for splitting at tmp[3..10]
            tmp[3] = x0
            tmp[4] = y0
            tmp[5] = cx0
            tmp[6] = cy0
            tmp[7] = cx1
            tmp[8] = cy1
            tmp[9] = x1
            tmp[10] = y1
            var t = tmp[0]
            if (numparams > 1 && t > tmp[1]) {
                // Perform a "2 element sort"...
                tmp[0] = tmp[1]
                tmp[1] = t
                t = tmp[0]
            }
            split(tmp, 3, t)
            if (numparams > 1) {
                // Recalculate tmp[1] relative to the range [tmp[0]...1]
                t = (tmp[1] - t) / (1 - t)
                split(tmp, 9, t)
            }
            var index = 3
            if (direction == DECREASING) {
                index += numparams * 6
            }
            while (numparams >= 0) {
                addInstance(
                    curves,
                    tmp[index], tmp[index + 1],
                    tmp[index + 2], tmp[index + 3],
                    tmp[index + 4], tmp[index + 5],
                    tmp[index + 6], tmp[index + 7],
                    direction
                )
                numparams--
                if (direction == INCREASING) {
                    index += 6
                } else {
                    index -= 6
                }
            }
        }

        fun addInstance(
            curves: Vector<Curve?>,
            x0: Double, y0: Double,
            cx0: Double, cy0: Double,
            cx1: Double, cy1: Double,
            x1: Double, y1: Double,
            direction: Int
        ) {
            if (y0 > y1) {
                curves.add(
                    Order3(
                        x1, y1, cx1, cy1, cx0, cy0, x0, y0,
                        -direction
                    )
                )
            } else if (y1 > y0) {
                curves.add(
                    Order3(
                        x0, y0, cx0, cy0, cx1, cy1, x1, y1,
                        direction
                    )
                )
            }
        }

        /*
     * Return the count of the number of horizontal sections of the
     * specified cubic Bezier curve.  Put the parameters for the
     * horizontal sections into the specified {@code ret} array.
     * <p>
     * If we examine the parametric equation in t, we have:
     *   Py(t) = C0(1-t)^3 + 3CP0 t(1-t)^2 + 3CP1 t^2(1-t) + C1 t^3
     *         = C0 - 3C0t + 3C0t^2 - C0t^3 +
     *           3CP0t - 6CP0t^2 + 3CP0t^3 +
     *           3CP1t^2 - 3CP1t^3 +
     *           C1t^3
     *   Py(t) = (C1 - 3CP1 + 3CP0 - C0) t^3 +
     *           (3C0 - 6CP0 + 3CP1) t^2 +
     *           (3CP0 - 3C0) t +
     *           (C0)
     * If we take the derivative, we get:
     *   Py(t) = Dt^3 + At^2 + Bt + C
     *   dPy(t) = 3Dt^2 + 2At + B = 0
     *        0 = 3*(C1 - 3*CP1 + 3*CP0 - C0)t^2
     *          + 2*(3*CP1 - 6*CP0 + 3*C0)t
     *          + (3*CP0 - 3*C0)
     *        0 = 3*(C1 - 3*CP1 + 3*CP0 - C0)t^2
     *          + 3*2*(CP1 - 2*CP0 + C0)t
     *          + 3*(CP0 - C0)
     *        0 = (C1 - CP1 - CP1 - CP1 + CP0 + CP0 + CP0 - C0)t^2
     *          + 2*(CP1 - CP0 - CP0 + C0)t
     *          + (CP0 - C0)
     *        0 = (C1 - CP1 + CP0 - CP1 + CP0 - CP1 + CP0 - C0)t^2
     *          + 2*(CP1 - CP0 - CP0 + C0)t
     *          + (CP0 - C0)
     *        0 = ((C1 - CP1) - (CP1 - CP0) - (CP1 - CP0) + (CP0 - C0))t^2
     *          + 2*((CP1 - CP0) - (CP0 - C0))t
     *          + (CP0 - C0)
     * Note that this method will return 0 if the equation is a line,
     * which is either always horizontal or never horizontal.
     * Completely horizontal curves need to be eliminated by other
     * means outside of this method.
     */
        fun getHorizontalParams(
            c0: Double, cp0: Double,
            cp1: Double, c1: Double,
            ret: DoubleArray
        ): Int {
            var cp0 = cp0
            var cp1 = cp1
            var c1 = c1
            if (c0 <= cp0 && cp0 <= cp1 && cp1 <= c1) {
                return 0
            }
            c1 -= cp1
            cp1 -= cp0
            cp0 -= c0
            ret[0] = cp0
            ret[1] = (cp1 - cp0) * 2
            ret[2] = (c1 - cp1 - cp1 + cp0)
            val numroots = solveQuadratic(ret, ret)
            var j = 0
            for (i in 0 until numroots) {
                val t = ret[i]
                // No splits at t==0 and t==1
                if (t > 0 && t < 1) {
                    if (j < i) {
                        ret[j] = t
                    }
                    j++
                }
            }
            return j
        }

        /*
     * Split the cubic Bezier stored at coords[pos...pos+7] representing
     * the parametric range [0..1] into two subcurves representing the
     * parametric subranges [0..t] and [t..1].  Store the results back
     * into the array at coords[pos...pos+7] and coords[pos+6...pos+13].
     */
        fun split(coords: DoubleArray, pos: Int, t: Double) {
            var x1 = coords[pos + 6]
            coords[pos + 12] = x1
            var y1 = coords[pos + 7]
            coords[pos + 13] = y1
            var cx1 = coords[pos + 4]
            var cy1 = coords[pos + 5]
            x1 = cx1 + (x1 - cx1) * t
            y1 = cy1 + (y1 - cy1) * t
            var x0 = coords[pos]
            var y0 = coords[pos + 1]
            var cx0 = coords[pos + 2]
            var cy0 = coords[pos + 3]
            x0 = x0 + (cx0 - x0) * t
            y0 = y0 + (cy0 - y0) * t
            cx0 = cx0 + (cx1 - cx0) * t
            cy0 = cy0 + (cy1 - cy0) * t
            cx1 = cx0 + (x1 - cx0) * t
            cy1 = cy0 + (y1 - cy0) * t
            cx0 = x0 + (cx0 - x0) * t
            cy0 = y0 + (cy0 - y0) * t
            coords[pos + 2] = x0
            coords[pos + 3] = y0
            coords[pos + 4] = cx0
            coords[pos + 5] = cy0
            coords[pos + 6] = cx0 + (cx1 - cx0) * t
            coords[pos + 7] = cy0 + (cy1 - cy0) * t
            coords[pos + 8] = cx1
            coords[pos + 9] = cy1
            coords[pos + 10] = x1
            coords[pos + 11] = y1
        }
    }
}
