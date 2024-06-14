package com.TritiumGaming.phasmophobiaevidencepicker.data.utils.geometry

import java.util.Vector
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

internal class Order2(
    x0: Double,
    y0: Double,
    cx0: Double,
    cy0: Double,
    x1: Double,
    y1: Double,
    direction: Int
) : Curve(direction) {

    override val xTop: Double
    override val yTop: Double
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
    private val ycoeff0: Double
    private val ycoeff1: Double
    private val ycoeff2: Double

    val cX0: Double
    val cY0: Double

    init {
        var cy0 = cy0
        // REMIND: Better accuracy in the root finding methods would
        //  ensure that cy0 is in range.  As it stands, it is never
        //  more than "1 mantissa bit" out of range...
        if (cy0 < y0) {
            cy0 = y0
        } else if (cy0 > y1) {
            cy0 = y1
        }
        this.xTop = x0
        this.yTop = y0
        this.cX0 = cx0
        this.cY0 = cy0
        this.xBot = x1
        this.yBot = y1
        xMin = min(min(x0, x1), cx0)
        xMax = max(max(x0, x1), cx0)
        xcoeff0 = x0
        xcoeff1 = cx0 + cx0 - x0 - x0
        xcoeff2 = x0 - cx0 - cx0 + x1
        ycoeff0 = y0
        ycoeff1 = cy0 + cy0 - y0 - y0
        ycoeff2 = y0 - cy0 - cy0 + y1
    }

    override val order: Int
        get() = 2

    override fun XforY(y: Double): Double {
        if (y <= yTop) {
            return xTop
        }
        if (y >= yBot) {
            return xBot
        }
        return XforT(TforY(y))
    }

    override fun TforY(y: Double): Double {
        if (y <= yTop) {
            return 0.0
        }
        if (y >= yBot) {
            return 1.0
        }
        return TforY(y, ycoeff0, ycoeff1, ycoeff2)
    }

    override fun XforT(t: Double): Double {
        return (xcoeff2 * t + xcoeff1) * t + xcoeff0
    }

    override fun YforT(t: Double): Double {
        return (ycoeff2 * t + ycoeff1) * t + ycoeff0
    }

    override fun dXforT(t: Double, deriv: Int): Double {
        return when (deriv) {
            0 -> (xcoeff2 * t + xcoeff1) * t + xcoeff0
            1 -> 2 * xcoeff2 * t + xcoeff1
            2 -> 2 * xcoeff2
            else -> 0.0
        }
    }

    override fun dYforT(t: Double, deriv: Int): Double {
        return when (deriv) {
            0 -> (ycoeff2 * t + ycoeff1) * t + ycoeff0
            1 -> 2 * ycoeff2 * t + ycoeff1
            2 -> 2 * ycoeff2
            else -> 0.0
        }
    }

    override fun nextVertical(t0: Double, t1: Double): Double {
        val t = -xcoeff1 / (2 * xcoeff2)
        if (t > t0 && t < t1) {
            return t
        }
        return t1
    }

    override fun enlarge(r: Rectangle2D?) {
        r?.add(xTop, yTop)
        val t = -xcoeff1 / (2 * xcoeff2)
        if (t > 0 && t < 1) {
            r?.add(XforT(t), YforT(t))
        }
        r?.add(xBot, yBot)
    }

    override fun getSubCurve(ystart: Double, yend: Double, dir: Int): Curve {
        val t0: Double
        if (ystart <= yTop) {
            if (yend >= yBot) {
                return getWithDirection(dir)
            }
            t0 = 0.0
        } else {
            t0 = TforY(ystart, ycoeff0, ycoeff1, ycoeff2)
        }
        val t1 = if (yend >= yBot) {
            1.0
        } else {
            TforY(
                yend,
                ycoeff0,
                ycoeff1,
                ycoeff2
            )
        }
        val eqn = DoubleArray(10)
        eqn[0] = xTop
        eqn[1] = yTop
        eqn[2] = cX0
        eqn[3] = cY0
        eqn[4] = xBot
        eqn[5] = yBot
        if (t1 < 1) {
            split(eqn, 0, t1)
        }
        val i: Int
        if (t0 <= 0) {
            i = 0
        } else {
            split(eqn, 0, t0 / t1)
            i = 4
        }
        return Order2(
            eqn[i], ystart,
            eqn[i + 2], eqn[i + 3],
            eqn[i + 4], yend,
            dir
        )
    }

    override val reversedCurve: Curve
        get() = Order2(xTop, yTop, cX0, cY0, xBot, yBot, -direction)

    override fun getSegment(coords: DoubleArray?): Int {
        coords?.set(0, cX0)
        coords?.set(1, cY0)
        if (direction == INCREASING) {
            coords?.set(2, xBot)
            coords?.set(3, yBot)
        } else {
            coords?.set(2, xTop)
            coords?.set(3, yTop)
        }
        return PathIterator.SEG_QUADTO
    }

    override fun controlPointString(): String {
        return ("(" + round(
            cX0
        ) + ", " + round(
            cY0
        ) + "), ")
    }

    companion object {
        fun insert(
            curves: Vector<Curve?>, tmp: DoubleArray,
            x0: Double, y0: Double,
            cx0: Double, cy0: Double,
            x1: Double, y1: Double,
            direction: Int
        ) {
            val numparams = getHorizontalParams(y0, cy0, y1, tmp)
            if (numparams == 0) {
                // We are using addInstance here to avoid inserting horisontal
                // segments
                addInstance(curves, x0, y0, cx0, cy0, x1, y1, direction)
                return
            }
            // assert(numparams == 1);
            val t = tmp[0]
            tmp[0] = x0
            tmp[1] = y0
            tmp[2] = cx0
            tmp[3] = cy0
            tmp[4] = x1
            tmp[5] = y1
            split(tmp, 0, t)
            val i0 = if ((direction == INCREASING)) 0 else 4
            val i1 = 4 - i0
            addInstance(
                curves, tmp[i0], tmp[i0 + 1], tmp[i0 + 2], tmp[i0 + 3],
                tmp[i0 + 4], tmp[i0 + 5], direction
            )
            addInstance(
                curves, tmp[i1], tmp[i1 + 1], tmp[i1 + 2], tmp[i1 + 3],
                tmp[i1 + 4], tmp[i1 + 5], direction
            )
        }

        fun addInstance(
            curves: Vector<Curve?>,
            x0: Double, y0: Double,
            cx0: Double, cy0: Double,
            x1: Double, y1: Double,
            direction: Int
        ) {
            if (y0 > y1) {
                curves.add(Order2(x1, y1, cx0, cy0, x0, y0, -direction))
            } else if (y1 > y0) {
                curves.add(Order2(x0, y0, cx0, cy0, x1, y1, direction))
            }
        }

        /*
     * Return the count of the number of horizontal sections of the
     * specified quadratic Bezier curve.  Put the parameters for the
     * horizontal sections into the specified {@code ret} array.
     * <p>
     * If we examine the parametric equation in t, we have:
     *     Py(t) = C0*(1-t)^2 + 2*CP*t*(1-t) + C1*t^2
     *           = C0 - 2*C0*t + C0*t^2 + 2*CP*t - 2*CP*t^2 + C1*t^2
     *           = C0 + (2*CP - 2*C0)*t + (C0 - 2*CP + C1)*t^2
     *     Py(t) = (C0 - 2*CP + C1)*t^2 + (2*CP - 2*C0)*t + (C0)
     * If we take the derivative, we get:
     *     Py(t) = At^2 + Bt + C
     *     dPy(t) = 2At + B = 0
     *     2*(C0 - 2*CP + C1)t + 2*(CP - C0) = 0
     *     2*(C0 - 2*CP + C1)t = 2*(C0 - CP)
     *     t = 2*(C0 - CP) / 2*(C0 - 2*CP + C1)
     *     t = (C0 - CP) / (C0 - CP + C1 - CP)
     * Note that this method will return 0 if the equation is a line,
     * which is either always horizontal or never horizontal.
     * Completely horizontal curves need to be eliminated by other
     * means outside of this method.
     */
        fun getHorizontalParams(
            c0: Double, cp: Double, c1: Double,
            ret: DoubleArray
        ): Int {
            var c0 = c0
            var c1 = c1
            if (c0 <= cp && cp <= c1) {
                return 0
            }
            c0 -= cp
            c1 -= cp
            val denom = c0 + c1
            // If denom == 0 then cp == (c0+c1)/2 and we have a line.
            if (denom == 0.0) {
                return 0
            }
            val t = c0 / denom
            // No splits at t==0 and t==1
            if (t <= 0 || t >= 1) {
                return 0
            }
            ret[0] = t
            return 1
        }

        /*
     * Split the quadratic Bezier stored at coords[pos...pos+5] representing
     * the paramtric range [0..1] into two subcurves representing the
     * parametric subranges [0..t] and [t..1].  Store the results back
     * into the array at coords[pos...pos+5] and coords[pos+4...pos+9].
     */
        fun split(coords: DoubleArray, pos: Int, t: Double) {
            var x1 = coords[pos + 4]
            coords[pos + 8] = x1
            var y1 = coords[pos + 5]
            coords[pos + 9] = y1
            var cx = coords[pos + 2]
            var cy = coords[pos + 3]
            x1 = cx + (x1 - cx) * t
            y1 = cy + (y1 - cy) * t
            var x0 = coords[pos]
            var y0 = coords[pos + 1]
            x0 = x0 + (cx - x0) * t
            y0 = y0 + (cy - y0) * t
            cx = x0 + (x1 - x0) * t
            cy = y0 + (y1 - y0) * t
            coords[pos + 2] = x0
            coords[pos + 3] = y0
            coords[pos + 4] = cx
            coords[pos + 5] = cy
            coords[pos + 6] = x1
            coords[pos + 7] = y1
        }

        fun TforY(
            y: Double,
            ycoeff0: Double, ycoeff1: Double, ycoeff2: Double
        ): Double {
            // The caller should have already eliminated y values
            // outside of the y0 to y1 range.
            var ycoeff0 = ycoeff0
            ycoeff0 -= y
            if (ycoeff2 == 0.0) {
                // The quadratic parabola has degenerated to a line.
                // ycoeff1 should not be 0.0 since we have already eliminated
                // totally horizontal lines, but if it is, then we will generate
                // infinity here for the root, which will not be in the [0,1]
                // range so we will pass to the failure code below.
                val root = -ycoeff0 / ycoeff1
                if (root >= 0 && root <= 1) {
                    return root
                }
            } else {
                // From Numerical Recipes, 5.6, Quadratic and Cubic Equations
                var d = ycoeff1 * ycoeff1 - 4.0 * ycoeff2 * ycoeff0
                // If d < 0.0, then there are no roots
                if (d >= 0.0) {
                    d = sqrt(d)
                    // For accuracy, calculate one root using:
                    //     (-ycoeff1 +/- d) / 2ycoeff2
                    // and the other using:
                    //     2ycoeff0 / (-ycoeff1 +/- d)
                    // Choose the sign of the +/- so that ycoeff1+d
                    // gets larger in magnitude
                    if (ycoeff1 < 0.0) {
                        d = -d
                    }
                    val q = (ycoeff1 + d) / -2.0
                    // We already tested ycoeff2 for being 0 above
                    var root = q / ycoeff2
                    if (root >= 0 && root <= 1) {
                        return root
                    }
                    if (q != 0.0) {
                        root = ycoeff0 / q
                        if (root >= 0 && root <= 1) {
                            return root
                        }
                    }
                }
            }
            /* We failed to find a root in [0,1].  What could have gone wrong?
         * First, remember that these curves are constructed to be monotonic
         * in Y and totally horizontal curves have already been eliminated.
         * Now keep in mind that the Y coefficients of the polynomial form
         * of the curve are calculated from the Y coordinates which define
         * our curve.  They should theoretically define the same curve,
         * but they can be off by a couple of bits of precision after the
         * math is done and so can represent a slightly modified curve.
         * This is normally not an issue except when we have solutions near
         * the endpoints.  Since the answers we get from solving the polynomial
         * may be off by a few bits that means that they could lie just a
         * few bits of precision outside the [0,1] range.
         *
         * Another problem could be that while the parametric curve defined
         * by the Y coordinates has a local minima or maxima at or just
         * outside of the endpoints, the polynomial form might express
         * that same min/max just inside of and just shy of the Y coordinate
         * of that endpoint.  In that case, if we solve for a Y coordinate
         * at or near that endpoint, we may be solving for a Y coordinate
         * that is below that minima or above that maxima and we would find
         * no solutions at all.
         *
         * In either case, we can assume that y is so near one of the
         * endpoints that we can just collapse it onto the nearest endpoint
         * without losing more than a couple of bits of precision.
         */
            // First calculate the midpoint between y0 and y1 and choose to
            // return either 0.0 or 1.0 depending on whether y is above
            // or below the midpoint...
            // Note that we subtracted y from ycoeff0 above so both y0 and y1
            // will be "relative to y" so we are really just looking at where
            // zero falls with respect to the "relative midpoint" here.
            val y0 = ycoeff0
            val y1 = ycoeff0 + ycoeff1 + ycoeff2
            return if ((0 < (y0 + y1) / 2)) 0.0 else 1.0
        }
    }
}
