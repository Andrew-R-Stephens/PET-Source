package com.tritiumgaming.phasmophobiaevidencepicker.utils.geometry

import java.util.Vector
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * @noinspection CanBeFinal
 */
abstract class Curve(
    var direction: Int
) {

    fun getWithDirection(direction: Int): Curve {
        return (if (this.direction == direction) this else reversedCurve!!)
    }

    override fun toString(): String {
        return ("Curve[" +
                order + ", " +
                ("(" + round(
                    x0
                ) + ", " + round(
                    y0
                ) + "), ") +
                controlPointString() +
                ("(" + round(
                    x1
                ) + ", " + round(
                    y1
                ) + "), ") +
                (if (direction == INCREASING) "D" else "U") +
                "]")
    }

    open fun controlPointString(): String {
        return ""
    }

    abstract val order: Int

    abstract val xTop: Double
    abstract val yTop: Double

    abstract val xBot: Double
    abstract val yBot: Double

    abstract val xMin: Double
    abstract val xMax: Double

    abstract val x0: Double
    abstract val y0: Double

    abstract val x1: Double
    abstract val y1: Double

    abstract fun XforY(y: Double): Double

    abstract fun TforY(y: Double): Double

    abstract fun XforT(t: Double): Double

    abstract fun YforT(t: Double): Double

    abstract fun dXforT(t: Double, deriv: Int): Double

    abstract fun dYforT(t: Double, deriv: Int): Double

    abstract fun nextVertical(t0: Double, t1: Double): Double

    open fun crossingsFor(x: Double, y: Double): Int {
        if (y >= yTop && y < yBot) {
            if (x < xMax && (x < xMin || x < XforY(y))) {
                return 1
            }
        }
        return 0
    }

    open fun accumulateCrossings(c: Crossings): Boolean {
        val xhi = c.xHi
        if (xMin >= xhi) {
            return false
        }
        val xlo = c.xLo
        val ylo = c.yLo
        val yhi = c.yHi
        val y0 = yTop
        val y1 = yBot
        var tstart: Double
        val ystart: Double
        val tend: Double
        val yend: Double
        if (y0 < ylo) {
            if (y1 <= ylo) {
                return false
            }
            ystart = ylo
            tstart = TforY(ylo)
        } else {
            if (y0 >= yhi) {
                return false
            }
            ystart = y0
            tstart = 0.0
        }
        if (y1 > yhi) {
            yend = yhi
            tend = TforY(yhi)
        } else {
            yend = y1
            tend = 1.0
        }
        var hitLo = false
        var hitHi = false
        while (true) {
            val x = XforT(tstart)
            if (x < xhi) {
                if (hitHi || x > xlo) {
                    return true
                }
                hitLo = true
            } else {
                if (hitLo) {
                    return true
                }
                hitHi = true
            }
            if (tstart >= tend) {
                break
            }
            tstart = nextVertical(tstart, tend)
        }
        if (hitLo) {
            c.record(ystart, yend, direction)
        }
        return false
    }

    abstract fun enlarge(r: Rectangle2D?)

    fun getSubCurve(ystart: Double, yend: Double): Curve {
        return getSubCurve(ystart, yend, direction)
    }

    abstract val reversedCurve: Curve?

    abstract fun getSubCurve(ystart: Double, yend: Double, dir: Int): Curve

    open fun compareTo(that: Curve, yrange: DoubleArray): Int {
        /*
        System.out.println(this+".compareTo("+that+")");
        System.out.println("target range = "+yrange[0]+"=>"+yrange[1]);
        */
        val y0 = yrange[0]
        var y1 = yrange[1]
        y1 = min(min(y1, this.yBot), that.yBot)
        if (y1 <= yrange[0]) {
            System.err.println("this == $this")
            System.err.println("that == $that")
            println("target range = " + yrange[0] + "=>" + yrange[1])
            throw InternalError("backstepping from " + yrange[0] + " to " + y1)
        }
        yrange[1] = y1
        if (this.xMax <= that.xMin) {
            if (this.xMin == that.xMax) {
                return 0
            }
            return -1
        }
        if (this.xMin >= that.xMax) {
            return 1
        }
        // Parameter s for thi(s) curve and t for tha(t) curve
        // [st]0 = parameters for top of current section of interest
        // [st]1 = parameters for bottom of valid range
        // [st]h = parameters for hypothesis point
        // [d][xy]s = valuations of thi(s) curve at sh
        // [d][xy]t = valuations of tha(t) curve at th
        var s0 = this.TforY(y0)
        var ys0 = this.YforT(s0)
        if (ys0 < y0) {
            s0 = refineTforY(s0, ys0, y0)
            ys0 = this.YforT(s0)
        }
        var s1 = this.TforY(y1)
        if (this.YforT(s1) < y0) {
            s1 = refineTforY(s1, this.YforT(s1), y0)
            //System.out.println("s1 problem!");
        }
        var t0 = that.TforY(y0)
        var yt0 = that.YforT(t0)
        if (yt0 < y0) {
            t0 = that.refineTforY(t0, yt0, y0)
            yt0 = that.YforT(t0)
        }
        var t1 = that.TforY(y1)
        if (that.YforT(t1) < y0) {
            t1 = that.refineTforY(t1, that.YforT(t1), y0)
            //System.out.println("t1 problem!");
        }
        var xs0 = this.XforT(s0)
        var xt0 = that.XforT(t0)
        val scale = max(abs(y0), abs(y1))
        val ymin = max(scale * 1E-14, 1E-300)
        if (fairlyClose(xs0, xt0)) {
            var bump = ymin
            val maxbump = min(ymin * 1E13, (y1 - y0) * .1)
            var y = y0 + bump
            while (y <= y1) {
                if (fairlyClose(this.XforY(y), that.XforY(y))) {
                    if ((2.let { bump *= it; bump }) > maxbump) {
                        bump = maxbump
                    }
                } else {
                    y -= bump
                    while (true) {
                        bump /= 2.0
                        val newy = y + bump
                        if (newy <= y) {
                            break
                        }
                        if (fairlyClose(this.XforY(newy), that.XforY(newy))) {
                            y = newy
                        }
                    }
                    break
                }
                y += bump
            }
            if (y > y0) {
                if (y < y1) {
                    yrange[1] = y
                }
                return 0
            }
        }
        //double ymin = y1 * 1E-14;
        if (ymin <= 0) {
            println("ymin = $ymin")
        }
        /*
        System.out.println("s range = "+s0+" to "+s1);
        System.out.println("t range = "+t0+" to "+t1);
        */
        while (s0 < s1 && t0 < t1) {
            val sh = this.nextVertical(s0, s1)
            val xsh = this.XforT(sh)
            val ysh = this.YforT(sh)
            val th = that.nextVertical(t0, t1)
            val xth = that.XforT(th)
            val yth = that.YforT(th)
            /*
            System.out.println("sh = "+sh);
            System.out.println("th = "+th);
            */
            try {
                if (findIntersect(
                        that, yrange, ymin, 0, 0,
                        s0, xs0, ys0, sh, xsh, ysh,
                        t0, xt0, yt0, th, xth, yth
                    )
                ) {
                    break
                }
            } catch (t: Throwable) {
                System.err.println("Error: $t")
                System.err.println("y range was " + yrange[0] + "=>" + yrange[1])
                System.err.println("s y range is $ys0=>$ysh")
                System.err.println("t y range is $yt0=>$yth")
                System.err.println("ymin is $ymin")
                return 0
            }
            if (ysh < yth) {
                if (ysh > yrange[0]) {
                    if (ysh < yrange[1]) {
                        yrange[1] = ysh
                    }
                    break
                }
                s0 = sh
                xs0 = xsh
                ys0 = ysh
            } else {
                if (yth > yrange[0]) {
                    if (yth < yrange[1]) {
                        yrange[1] = yth
                    }
                    break
                }
                t0 = th
                xt0 = xth
                yt0 = yth
            }
        }
        val ymid = (yrange[0] + yrange[1]) / 2
        /*
        System.out.println("final this["+s0+", "+sh+", "+s1+"]");
        System.out.println("final    y["+ys0+", "+ysh+"]");
        System.out.println("final that["+t0+", "+th+", "+t1+"]");
        System.out.println("final    y["+yt0+", "+yth+"]");
        System.out.println("final order = "+orderof(this.XforY(ymid),
                                                    that.XforY(ymid)));
        System.out.println("final range = "+yrange[0]+"=>"+yrange[1]);
        */
        /*
        System.out.println("final sx = "+this.XforY(ymid));
        System.out.println("final tx = "+that.XforY(ymid));
        System.out.println("final order = "+orderof(this.XforY(ymid),
                                                    that.XforY(ymid)));
        */
        return orderof(
            this.XforY(ymid), that.XforY(ymid)
        )
    }

    fun findIntersect(
        that: Curve, yrange: DoubleArray, ymin: Double,
        slevel: Int, tlevel: Int,
        s0: Double, xs0: Double, ys0: Double,
        s1: Double, xs1: Double, ys1: Double,
        t0: Double, xt0: Double, yt0: Double,
        t1: Double, xt1: Double, yt1: Double
    ): Boolean {
        /*
        String pad = "        ";
        pad = pad+pad+pad+pad+pad;
        pad = pad+pad;
        System.out.println("----------------------------------------------");
        System.out.println(pad.substring(0, slevel)+ys0);
        System.out.println(pad.substring(0, slevel)+ys1);
        System.out.println(pad.substring(0, slevel)+(s1-s0));
        System.out.println("-------");
        System.out.println(pad.substring(0, tlevel)+yt0);
        System.out.println(pad.substring(0, tlevel)+yt1);
        System.out.println(pad.substring(0, tlevel)+(t1-t0));
        */
        if (ys0 > yt1 || yt0 > ys1) {
            return false
        }
        if (min(xs0, xs1) > max(xt0, xt1) ||
            max(xs0, xs1) < min(xt0, xt1)
        ) {
            return false
        }
        // Bounding boxes intersect - back off the larger of
        // the two subcurves by half until they stop intersecting
        // (or until they get small enough to switch to a more
        //  intensive algorithm).
        if (s1 - s0 > TMIN) {
            val s = (s0 + s1) / 2
            val xs = this.XforT(s)
            val ys = this.YforT(s)
            if (s == s0 || s == s1) {
                println("s0 = $s0")
                println("s1 = $s1")
                throw InternalError("no s progress!")
            }
            if (t1 - t0 > TMIN) {
                val t = (t0 + t1) / 2
                val xt = that.XforT(t)
                val yt = that.YforT(t)
                if (t == t0 || t == t1) {
                    println("t0 = $t0")
                    println("t1 = $t1")
                    throw InternalError("no t progress!")
                }
                if (ys >= yt0 && yt >= ys0) {
                    if (findIntersect(
                            that, yrange, ymin, slevel + 1, tlevel + 1,
                            s0, xs0, ys0, s, xs, ys,
                            t0, xt0, yt0, t, xt, yt
                        )
                    ) {
                        return true
                    }
                }
                if (ys >= yt) {
                    if (findIntersect(
                            that, yrange, ymin, slevel + 1, tlevel + 1,
                            s0, xs0, ys0, s, xs, ys,
                            t, xt, yt, t1, xt1, yt1
                        )
                    ) {
                        return true
                    }
                }
                if (yt >= ys) {
                    if (findIntersect(
                            that, yrange, ymin, slevel + 1, tlevel + 1,
                            s, xs, ys, s1, xs1, ys1,
                            t0, xt0, yt0, t, xt, yt
                        )
                    ) {
                        return true
                    }
                }
                if (ys1 >= yt && yt1 >= ys) {
                    return findIntersect(
                        that, yrange, ymin, slevel + 1, tlevel + 1,
                        s, xs, ys, s1, xs1, ys1,
                        t, xt, yt, t1, xt1, yt1
                    )
                }
            } else {
                if (ys >= yt0) {
                    if (findIntersect(
                            that, yrange, ymin, slevel + 1, tlevel,
                            s0, xs0, ys0, s, xs, ys,
                            t0, xt0, yt0, t1, xt1, yt1
                        )
                    ) {
                        return true
                    }
                }
                if (yt1 >= ys) {
                    return findIntersect(
                        that, yrange, ymin, slevel + 1, tlevel,
                        s, xs, ys, s1, xs1, ys1,
                        t0, xt0, yt0, t1, xt1, yt1
                    )
                }
            }
        } else if (t1 - t0 > TMIN) {
            val t = (t0 + t1) / 2
            val xt = that.XforT(t)
            val yt = that.YforT(t)
            if (t == t0 || t == t1) {
                println("t0 = $t0")
                println("t1 = $t1")
                throw InternalError("no t progress!")
            }
            if (yt >= ys0) {
                if (findIntersect(
                        that, yrange, ymin, slevel, tlevel + 1,
                        s0, xs0, ys0, s1, xs1, ys1,
                        t0, xt0, yt0, t, xt, yt
                    )
                ) {
                    return true
                }
            }
            if (ys1 >= yt) {
                return findIntersect(
                    that, yrange, ymin, slevel, tlevel + 1,
                    s0, xs0, ys0, s1, xs1, ys1,
                    t, xt, yt, t1, xt1, yt1
                )
            }
        } else {
            // No more subdivisions
            val xlk = xs1 - xs0
            val ylk = ys1 - ys0
            val xnm = xt1 - xt0
            val ynm = yt1 - yt0
            val xmk = xt0 - xs0
            val ymk = yt0 - ys0
            val det = xnm * ylk - ynm * xlk
            if (det != 0.0) {
                val detinv = 1 / det
                var s = (xnm * ymk - ynm * xmk) * detinv
                var t = (xlk * ymk - ylk * xmk) * detinv
                if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
                    s = s0 + s * (s1 - s0)
                    t = t0 + t * (t1 - t0)
                    if (s < 0 || s > 1 || t < 0 || t > 1) {
                        println("Uh oh!")
                    }
                    val y = (this.YforT(s) + that.YforT(t)) / 2
                    if (y <= yrange[1] && y > yrange[0]) {
                        yrange[1] = y
                        return true
                    }
                }
            }
            //System.out.println("Testing lines!");
        }
        return false
    }

    fun refineTforY(t0: Double, yt0: Double, y0: Double): Double {
        var t0 = t0
        var yt0 = yt0
        var t1 = 1.0
        while (true) {
            val th = (t0 + t1) / 2
            if (th == t0 || th == t1) {
                return t1
            }
            val y = YforT(th)
            if (y < y0) {
                t0 = th
                yt0 = y
            } else if (y > y0) {
                t1 = th
            } else {
                return t1
            }
        }
    }

    fun fairlyClose(v1: Double, v2: Double): Boolean {
        return (abs(v1 - v2) <
                max(abs(v1), abs(v2)) * 1E-10)
    }

    abstract fun getSegment(coords: DoubleArray?): Int

    companion object {
        const val INCREASING: Int = 1
        const val DECREASING: Int = -1

        fun insertMove(curves: Vector<Curve?>, x: Double, y: Double) {
            curves.add(Order0(x, y))
        }

        fun insertLine(
            curves: Vector<Curve?>,
            x0: Double, y0: Double,
            x1: Double, y1: Double
        ) {
            if (y0 < y1) {
                curves.add(
                    Order1(
                        x0, y0,
                        x1, y1,
                        INCREASING
                    )
                )
            } else if (y0 > y1) {
                curves.add(
                    Order1(
                        x1, y1,
                        x0, y0,
                        DECREASING
                    )
                )
            } else {
                // Do not add horizontal lines
            }
        }

        @JvmStatic
        fun insertQuad(
            curves: Vector<Curve?>?,
            x0: Double, y0: Double,
            coords: DoubleArray
        ) {
            val y1 = coords[3]
            if (y0 > y1) {
                Order2.insert(
                    curves!!, coords,
                    coords[2], y1,
                    coords[0], coords[1],
                    x0, y0,
                    DECREASING
                )
            } else if (y0 == y1 && y0 == coords[1]) {
                // Do not add horizontal lines
            } else {
                Order2.insert(
                    curves!!, coords,
                    x0, y0,
                    coords[0], coords[1],
                    coords[2], y1,
                    INCREASING
                )
            }
        }

        @JvmStatic
        fun insertCubic(
            curves: Vector<Curve?>?,
            x0: Double, y0: Double,
            coords: DoubleArray
        ) {
            val y1 = coords[5]
            if (y0 > y1) {
                Order3.insert(
                    curves!!, coords,
                    coords[4], y1,
                    coords[2], coords[3],
                    coords[0], coords[1],
                    x0, y0,
                    DECREASING
                )
            } else if (y0 == y1 && y0 == coords[1] && y0 == coords[3]) {
                // Do not add horizontal lines
            } else {
                Order3.insert(
                    curves!!, coords,
                    x0, y0,
                    coords[0], coords[1],
                    coords[2], coords[3],
                    coords[4], y1,
                    INCREASING
                )
            }
        }


        @JvmStatic
        fun pointCrossingsForPath(
            pi: PathIterator,
            px: Double, py: Double
        ): Int {
            if (pi.isDone) {
                return 0
            }
            val coords = DoubleArray(6)
            if (pi.currentSegment(coords) != PathIterator.SEG_MOVETO) {
                throw IllegalPathStateException(
                    "missing initial moveto " +
                            "in path definition"
                )
            }
            pi.next()
            var movx = coords[0]
            var movy = coords[1]
            var curx = movx
            var cury = movy
            var endx: Double
            var endy: Double
            var crossings = 0
            while (!pi.isDone) {
                when (pi.currentSegment(coords)) {
                    PathIterator.SEG_MOVETO -> {
                        if (cury != movy) {
                            crossings += pointCrossingsForLine(
                                px, py,
                                curx, cury,
                                movx, movy
                            )
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
                        endx = coords[0]
                        endy = coords[1]
                        crossings += pointCrossingsForLine(
                            px, py,
                            curx, cury,
                            endx, endy
                        )
                        curx = endx
                        cury = endy
                    }

                    PathIterator.SEG_QUADTO -> {
                        endx = coords[2]
                        endy = coords[3]
                        crossings += pointCrossingsForQuad(
                            px, py,
                            curx, cury,
                            coords[0], coords[1],
                            endx, endy, 0
                        )
                        curx = endx
                        cury = endy
                    }

                    PathIterator.SEG_CUBICTO -> {
                        endx = coords[4]
                        endy = coords[5]
                        crossings += pointCrossingsForCubic(
                            px, py,
                            curx, cury,
                            coords[0], coords[1],
                            coords[2], coords[3],
                            endx, endy, 0
                        )
                        curx = endx
                        cury = endy
                    }

                    PathIterator.SEG_CLOSE -> {
                        if (cury != movy) {
                            crossings += pointCrossingsForLine(
                                px, py,
                                curx, cury,
                                movx, movy
                            )
                        }
                        curx = movx
                        cury = movy
                    }
                }
                pi.next()
            }
            if (cury != movy) {
                crossings += pointCrossingsForLine(
                    px, py,
                    curx, cury,
                    movx, movy
                )
            }
            return crossings
        }


        @JvmStatic
        fun pointCrossingsForLine(
            px: Double, py: Double,
            x0: Double, y0: Double,
            x1: Double, y1: Double
        ): Int {
            if (py < y0 && py < y1) return 0
            if (py >= y0 && py >= y1) return 0
            // assert(y0 != y1);
            if (px >= x0 && px >= x1) return 0
            if (px < x0 && px < x1) return if ((y0 < y1)) 1 else -1
            val xintercept = x0 + (py - y0) * (x1 - x0) / (y1 - y0)
            if (px >= xintercept) return 0
            return if ((y0 < y1)) 1 else -1
        }


        @JvmStatic
        fun pointCrossingsForQuad(
            px: Double, py: Double,
            x0: Double, y0: Double,
            xc: Double, yc: Double,
            x1: Double, y1: Double, level: Int
        ): Int {
            var xc = xc
            var yc = yc
            if (py < y0 && py < yc && py < y1) return 0
            if (py >= y0 && py >= yc && py >= y1) return 0
            // Note y0 could equal y1...
            if (px >= x0 && px >= xc && px >= x1) return 0
            if (px < x0 && px < xc && px < x1) {
                if (py >= y0) {
                    if (py < y1) return 1
                } else {
                    // py < y0
                    if (py >= y1) return -1
                }
                // py outside of y01 range, and/or y0==y1
                return 0
            }
            // double precision only has 52 bits of mantissa
            if (level > 52) return pointCrossingsForLine(px, py, x0, y0, x1, y1)
            val x0c = (x0 + xc) / 2
            val y0c = (y0 + yc) / 2
            val xc1 = (xc + x1) / 2
            val yc1 = (yc + y1) / 2
            xc = (x0c + xc1) / 2
            yc = (y0c + yc1) / 2
            if (java.lang.Double.isNaN(xc) || java.lang.Double.isNaN(yc)) {
                // [xy]c are NaN if any of [xy]0c or [xy]c1 are NaN
                // [xy]0c or [xy]c1 are NaN if any of [xy][0c1] are NaN
                // These values are also NaN if opposing infinities are added
                return 0
            }
            return (pointCrossingsForQuad(
                px, py,
                x0, y0, x0c, y0c, xc, yc,
                level + 1
            ) +
                    pointCrossingsForQuad(
                        px, py,
                        xc, yc, xc1, yc1, x1, y1,
                        level + 1
                    ))
        }


        @JvmStatic
        fun pointCrossingsForCubic(
            px: Double, py: Double,
            x0: Double, y0: Double,
            xc0: Double, yc0: Double,
            xc1: Double, yc1: Double,
            x1: Double, y1: Double, level: Int
        ): Int {
            var xc0 = xc0
            var yc0 = yc0
            var xc1 = xc1
            var yc1 = yc1
            if (py < y0 && py < yc0 && py < yc1 && py < y1) return 0
            if (py >= y0 && py >= yc0 && py >= yc1 && py >= y1) return 0
            // Note y0 could equal yc0...
            if (px >= x0 && px >= xc0 && px >= xc1 && px >= x1) return 0
            if (px < x0 && px < xc0 && px < xc1 && px < x1) {
                if (py >= y0) {
                    if (py < y1) return 1
                } else {
                    // py < y0
                    if (py >= y1) return -1
                }
                // py outside of y01 range, and/or y0==yc0
                return 0
            }
            // double precision only has 52 bits of mantissa
            if (level > 52) return pointCrossingsForLine(px, py, x0, y0, x1, y1)
            var xmid = (xc0 + xc1) / 2
            var ymid = (yc0 + yc1) / 2
            xc0 = (x0 + xc0) / 2
            yc0 = (y0 + yc0) / 2
            xc1 = (xc1 + x1) / 2
            yc1 = (yc1 + y1) / 2
            val xc0m = (xc0 + xmid) / 2
            val yc0m = (yc0 + ymid) / 2
            val xmc1 = (xmid + xc1) / 2
            val ymc1 = (ymid + yc1) / 2
            xmid = (xc0m + xmc1) / 2
            ymid = (yc0m + ymc1) / 2
            if (java.lang.Double.isNaN(xmid) || java.lang.Double.isNaN(ymid)) {
                // [xy]mid are NaN if any of [xy]c0m or [xy]mc1 are NaN
                // [xy]c0m or [xy]mc1 are NaN if any of [xy][c][01] are NaN
                // These values are also NaN if opposing infinities are added
                return 0
            }
            return (pointCrossingsForCubic(
                px, py,
                x0, y0, xc0, yc0,
                xc0m, yc0m, xmid, ymid, level + 1
            ) +
                    pointCrossingsForCubic(
                        px, py,
                        xmid, ymid, xmc1, ymc1,
                        xc1, yc1, x1, y1, level + 1
                    ))
        }


        const val RECT_INTERSECTS: Int = -0x80000000


        @JvmStatic
        fun rectCrossingsForPath(
            pi: PathIterator,
            rxmin: Double, rymin: Double,
            rxmax: Double, rymax: Double
        ): Int {
            if (rxmax <= rxmin || rymax <= rymin) {
                return 0
            }
            if (pi.isDone) {
                return 0
            }
            val coords = DoubleArray(6)
            if (pi.currentSegment(coords) != PathIterator.SEG_MOVETO) {
                throw IllegalPathStateException(
                    "missing initial moveto " +
                            "in path definition"
                )
            }
            pi.next()
            var curx: Double
            var cury: Double
            var movx: Double
            var movy: Double
            var endx: Double
            var endy: Double
            movx = coords[0]
            curx = movx
            movy = coords[1]
            cury = movy
            var crossings = 0
            while (crossings != RECT_INTERSECTS && !pi.isDone) {
                when (pi.currentSegment(coords)) {
                    PathIterator.SEG_MOVETO -> {
                        if (curx != movx || cury != movy) {
                            crossings = rectCrossingsForLine(
                                crossings,
                                rxmin, rymin,
                                rxmax, rymax,
                                curx, cury,
                                movx, movy
                            )
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
                        endx = coords[0]
                        endy = coords[1]
                        crossings = rectCrossingsForLine(
                            crossings,
                            rxmin, rymin,
                            rxmax, rymax,
                            curx, cury,
                            endx, endy
                        )
                        curx = endx
                        cury = endy
                    }

                    PathIterator.SEG_QUADTO -> {
                        endx = coords[2]
                        endy = coords[3]
                        crossings = rectCrossingsForQuad(
                            crossings,
                            rxmin, rymin,
                            rxmax, rymax,
                            curx, cury,
                            coords[0], coords[1],
                            endx, endy, 0
                        )
                        curx = endx
                        cury = endy
                    }

                    PathIterator.SEG_CUBICTO -> {
                        endx = coords[4]
                        endy = coords[5]
                        crossings = rectCrossingsForCubic(
                            crossings,
                            rxmin, rymin,
                            rxmax, rymax,
                            curx, cury,
                            coords[0], coords[1],
                            coords[2], coords[3],
                            endx, endy, 0
                        )
                        curx = endx
                        cury = endy
                    }

                    PathIterator.SEG_CLOSE -> {
                        if (curx != movx || cury != movy) {
                            crossings = rectCrossingsForLine(
                                crossings,
                                rxmin, rymin,
                                rxmax, rymax,
                                curx, cury,
                                movx, movy
                            )
                        }
                        curx = movx
                        cury = movy
                    }
                }
                pi.next()
            }
            if (crossings != RECT_INTERSECTS && (curx != movx || cury != movy)) {
                crossings = rectCrossingsForLine(
                    crossings,
                    rxmin, rymin,
                    rxmax, rymax,
                    curx, cury,
                    movx, movy
                )
            }
            // Count should always be a multiple of 2 here.
            // assert((crossings & 1) != 0);
            return crossings
        }


        @JvmStatic
        fun rectCrossingsForLine(
            crossings: Int,
            rxmin: Double, rymin: Double,
            rxmax: Double, rymax: Double,
            x0: Double, y0: Double,
            x1: Double, y1: Double
        ): Int {
            var crossings = crossings
            if (y0 >= rymax && y1 >= rymax) return crossings
            if (y0 <= rymin && y1 <= rymin) return crossings
            if (x0 <= rxmin && x1 <= rxmin) return crossings
            if (x0 >= rxmax && x1 >= rxmax) {
                // Line is entirely to the right of the rect
                // and the vertical ranges of the two overlap by a non-empty amount
                // Thus, this line segment is partially in the "right-shadow"
                // Path may have done a complete crossing
                // Or path may have entered or exited the right-shadow
                if (y0 < y1) {
                    // y-increasing line segment...
                    // We know that y0 < rymax and y1 > rymin
                    if (y0 <= rymin) crossings++
                    if (y1 >= rymax) crossings++
                } else if (y1 < y0) {
                    // y-decreasing line segment...
                    // We know that y1 < rymax and y0 > rymin
                    if (y1 <= rymin) crossings--
                    if (y0 >= rymax) crossings--
                }
                return crossings
            }
            // Remaining case:
            // Both x and y ranges overlap by a non-empty amount
            // First do trivial INTERSECTS rejection of the cases
            // where one of the endpoints is inside the rectangle.
            if ((x0 > rxmin && x0 < rxmax && y0 > rymin && y0 < rymax) ||
                (x1 > rxmin && x1 < rxmax && y1 > rymin && y1 < rymax)
            ) {
                return RECT_INTERSECTS
            }
            // Otherwise calculate the y intercepts and see where
            // they fall with respect to the rectangle
            var xi0 = x0
            if (y0 < rymin) {
                xi0 += ((rymin - y0) * (x1 - x0) / (y1 - y0))
            } else if (y0 > rymax) {
                xi0 += ((rymax - y0) * (x1 - x0) / (y1 - y0))
            }
            var xi1 = x1
            if (y1 < rymin) {
                xi1 += ((rymin - y1) * (x0 - x1) / (y0 - y1))
            } else if (y1 > rymax) {
                xi1 += ((rymax - y1) * (x0 - x1) / (y0 - y1))
            }
            if (xi0 <= rxmin && xi1 <= rxmin) return crossings
            if (xi0 >= rxmax && xi1 >= rxmax) {
                if (y0 < y1) {
                    // y-increasing line segment...
                    // We know that y0 < rymax and y1 > rymin
                    if (y0 <= rymin) crossings++
                    if (y1 >= rymax) crossings++
                } else if (y1 < y0) {
                    // y-decreasing line segment...
                    // We know that y1 < rymax and y0 > rymin
                    if (y1 <= rymin) crossings--
                    if (y0 >= rymax) crossings--
                }
                return crossings
            }
            return RECT_INTERSECTS
        }


        @JvmStatic
        fun rectCrossingsForQuad(
            crossings: Int,
            rxmin: Double, rymin: Double,
            rxmax: Double, rymax: Double,
            x0: Double, y0: Double,
            xc: Double, yc: Double,
            x1: Double, y1: Double,
            level: Int
        ): Int {
            var crossings = crossings
            var xc = xc
            var yc = yc
            if (y0 >= rymax && yc >= rymax && y1 >= rymax) return crossings
            if (y0 <= rymin && yc <= rymin && y1 <= rymin) return crossings
            if (x0 <= rxmin && xc <= rxmin && x1 <= rxmin) return crossings
            if (x0 >= rxmax && xc >= rxmax && x1 >= rxmax) {
                // Quad is entirely to the right of the rect
                // and the vertical range of the 3 Y coordinates of the quad
                // overlaps the vertical range of the rect by a non-empty amount
                // We now judge the crossings solely based on the line segment
                // connecting the endpoints of the quad.
                // Note that we may have 0, 1, or 2 crossings as the control
                // point may be causing the Y range intersection while the
                // two endpoints are entirely above or below.
                if (y0 < y1) {
                    // y-increasing line segment...
                    if (y0 <= rymin && y1 > rymin) crossings++
                    if (y0 < rymax && y1 >= rymax) crossings++
                } else if (y1 < y0) {
                    // y-decreasing line segment...
                    if (y1 <= rymin && y0 > rymin) crossings--
                    if (y1 < rymax && y0 >= rymax) crossings--
                }
                return crossings
            }
            // The intersection of ranges is more complicated
            // First do trivial INTERSECTS rejection of the cases
            // where one of the endpoints is inside the rectangle.
            if ((x0 < rxmax && x0 > rxmin && y0 < rymax && y0 > rymin) ||
                (x1 < rxmax && x1 > rxmin && y1 < rymax && y1 > rymin)
            ) {
                return RECT_INTERSECTS
            }
            // Otherwise, subdivide and look for one of the cases above.
            // double precision only has 52 bits of mantissa
            if (level > 52) {
                return rectCrossingsForLine(
                    crossings,
                    rxmin, rymin, rxmax, rymax,
                    x0, y0, x1, y1
                )
            }
            val x0c = (x0 + xc) / 2
            val y0c = (y0 + yc) / 2
            val xc1 = (xc + x1) / 2
            val yc1 = (yc + y1) / 2
            xc = (x0c + xc1) / 2
            yc = (y0c + yc1) / 2
            if (java.lang.Double.isNaN(xc) || java.lang.Double.isNaN(yc)) {
                // [xy]c are NaN if any of [xy]0c or [xy]c1 are NaN
                // [xy]0c or [xy]c1 are NaN if any of [xy][0c1] are NaN
                // These values are also NaN if opposing infinities are added
                return 0
            }
            crossings = rectCrossingsForQuad(
                crossings,
                rxmin, rymin, rxmax, rymax,
                x0, y0, x0c, y0c, xc, yc,
                level + 1
            )
            if (crossings != RECT_INTERSECTS) {
                crossings = rectCrossingsForQuad(
                    crossings,
                    rxmin, rymin, rxmax, rymax,
                    xc, yc, xc1, yc1, x1, y1,
                    level + 1
                )
            }
            return crossings
        }


        @JvmStatic
        fun rectCrossingsForCubic(
            crossings: Int,
            rxmin: Double, rymin: Double,
            rxmax: Double, rymax: Double,
            x0: Double, y0: Double,
            xc0: Double, yc0: Double,
            xc1: Double, yc1: Double,
            x1: Double, y1: Double,
            level: Int
        ): Int {
            var crossings = crossings
            var xc0 = xc0
            var yc0 = yc0
            var xc1 = xc1
            var yc1 = yc1
            if (y0 >= rymax && yc0 >= rymax && yc1 >= rymax && y1 >= rymax) {
                return crossings
            }
            if (y0 <= rymin && yc0 <= rymin && yc1 <= rymin && y1 <= rymin) {
                return crossings
            }
            if (x0 <= rxmin && xc0 <= rxmin && xc1 <= rxmin && x1 <= rxmin) {
                return crossings
            }
            if (x0 >= rxmax && xc0 >= rxmax && xc1 >= rxmax && x1 >= rxmax) {
                // Cubic is entirely to the right of the rect
                // and the vertical range of the 4 Y coordinates of the cubic
                // overlaps the vertical range of the rect by a non-empty amount
                // We now judge the crossings solely based on the line segment
                // connecting the endpoints of the cubic.
                // Note that we may have 0, 1, or 2 crossings as the control
                // points may be causing the Y range intersection while the
                // two endpoints are entirely above or below.
                if (y0 < y1) {
                    // y-increasing line segment...
                    if (y0 <= rymin && y1 > rymin) crossings++
                    if (y0 < rymax && y1 >= rymax) crossings++
                } else if (y1 < y0) {
                    // y-decreasing line segment...
                    if (y1 <= rymin && y0 > rymin) crossings--
                    if (y1 < rymax && y0 >= rymax) crossings--
                }
                return crossings
            }
            // The intersection of ranges is more complicated
            // First do trivial INTERSECTS rejection of the cases
            // where one of the endpoints is inside the rectangle.
            if ((x0 > rxmin && x0 < rxmax && y0 > rymin && y0 < rymax) ||
                (x1 > rxmin && x1 < rxmax && y1 > rymin && y1 < rymax)
            ) {
                return RECT_INTERSECTS
            }
            // Otherwise, subdivide and look for one of the cases above.
            // double precision only has 52 bits of mantissa
            if (level > 52) {
                return rectCrossingsForLine(
                    crossings,
                    rxmin, rymin, rxmax, rymax,
                    x0, y0, x1, y1
                )
            }
            var xmid = (xc0 + xc1) / 2
            var ymid = (yc0 + yc1) / 2
            xc0 = (x0 + xc0) / 2
            yc0 = (y0 + yc0) / 2
            xc1 = (xc1 + x1) / 2
            yc1 = (yc1 + y1) / 2
            val xc0m = (xc0 + xmid) / 2
            val yc0m = (yc0 + ymid) / 2
            val xmc1 = (xmid + xc1) / 2
            val ymc1 = (ymid + yc1) / 2
            xmid = (xc0m + xmc1) / 2
            ymid = (yc0m + ymc1) / 2
            if (java.lang.Double.isNaN(xmid) || java.lang.Double.isNaN(ymid)) {
                // [xy]mid are NaN if any of [xy]c0m or [xy]mc1 are NaN
                // [xy]c0m or [xy]mc1 are NaN if any of [xy][c][01] are NaN
                // These values are also NaN if opposing infinities are added
                return 0
            }
            crossings = rectCrossingsForCubic(
                crossings,
                rxmin, rymin, rxmax, rymax,
                x0, y0, xc0, yc0,
                xc0m, yc0m, xmid, ymid, level + 1
            )
            if (crossings != RECT_INTERSECTS) {
                crossings = rectCrossingsForCubic(
                    crossings,
                    rxmin, rymin, rxmax, rymax,
                    xmid, ymid, xmc1, ymc1,
                    xc1, yc1, x1, y1, level + 1
                )
            }
            return crossings
        }

        @JvmStatic
        fun round(v: Double): Double {
            //return Math.rint(v*10)/10;
            return v
        }

        @JvmStatic
        fun orderof(x1: Double, x2: Double): Int {
            if (x1 < x2) {
                return -1
            }
            if (x1 > x2) {
                return 1
            }
            return 0
        }

        fun signeddiffbits(y1: Double, y2: Double): Long {
            return (java.lang.Double.doubleToLongBits(y1) - java.lang.Double.doubleToLongBits(y2))
        }

        @JvmStatic
        fun diffbits(y1: Double, y2: Double): Long {
            return abs(
                (java.lang.Double.doubleToLongBits(y1) -
                        java.lang.Double.doubleToLongBits(y2)).toDouble()
            ).toLong()
        }

        @JvmStatic
        fun prev(v: Double): Double {
            return java.lang.Double.longBitsToDouble(java.lang.Double.doubleToLongBits(v) - 1)
        }

        @JvmStatic
        fun next(v: Double): Double {
            return java.lang.Double.longBitsToDouble(java.lang.Double.doubleToLongBits(v) + 1)
        }

        const val TMIN: Double = 1E-3
    }
}
