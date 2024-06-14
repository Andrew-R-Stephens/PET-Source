package com.TritiumGaming.phasmophobiaevidencepicker.data.utils.geometry

import android.os.Build
import androidx.annotation.RequiresApi
import com.TritiumGaming.phasmophobiaevidencepicker.data.utils.geometry.Line2D.Companion.ptSegDist
import com.TritiumGaming.phasmophobiaevidencepicker.data.utils.geometry.Line2D.Companion.ptSegDistSq
import com.TritiumGaming.phasmophobiaevidencepicker.data.utils.geometry.Point2D.Point2DDouble
import com.TritiumGaming.phasmophobiaevidencepicker.data.utils.geometry.Point2D.Point2DFloat
import com.TritiumGaming.phasmophobiaevidencepicker.data.utils.geometry.Rectangle2D.Rectangle2DDouble
import com.TritiumGaming.phasmophobiaevidencepicker.data.utils.geometry.Rectangle2D.Rectangle2DFloat
import java.io.Serial
import java.io.Serializable
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

/**
 * @noinspection SameParameterValue, SameParameterValue, SameParameterValue
 */
abstract class QuadCurve2D protected constructor() : Shape, Cloneable {

    abstract val x1: Double
    abstract val y1: Double

    abstract val p1: Point2D
    abstract val p2: Point2D

    abstract val ctrlX: Double
    abstract val ctrlY: Double
    abstract val ctrlPt: Point2D

    abstract val x2: Double
    abstract val y2: Double

    override val bounds: Rectangle
        get() = bounds2D.bounds

    class QuadCurve2DFloat : QuadCurve2D, Serializable {

        override var ctrlX: Double = 0.0
        override var ctrlY: Double = 0.0
        override var x1: Double = 0.0
        override var y1: Double = 0.0
        override var x2: Double = 0.0
        override var y2: Double = 0.0

        constructor()

        constructor(
            x1: Float, y1: Float,
            ctrlx: Float, ctrly: Float,
            x2: Float, y2: Float
        ) {
            setCurve(x1, y1, ctrlx, ctrly, x2, y2)
        }

        override val p1: Point2D
            get() = Point2DFloat(x1.toFloat(), y1.toFloat())

        override val p2: Point2D
            get() = Point2DFloat(x2.toFloat(), y2.toFloat())

        override val ctrlPt: Point2D
            get() = Point2DFloat(ctrlX.toFloat(), ctrlY.toFloat())

        override val bounds2D: Rectangle2D
            get() {
                val left = min(min(x1, x2), ctrlX)
                    .toFloat()
                val top = min(min(y1, y2), ctrlY)
                    .toFloat()
                val right = max(max(x1, x2), ctrlX)
                    .toFloat()
                val bottom = max(max(y1, y2), ctrlY)
                    .toFloat()
                return Rectangle2DFloat(
                    left, top,
                    right - left, bottom - top
                )
            }
        override fun setCurve(
            x1: Double, y1: Double,
            ctrlx: Double, ctrly: Double,
            x2: Double, y2: Double
        ) {
            this.x1 = x1
            this.y1 = y1
            this.ctrlX = ctrlx
            this.ctrlY = ctrly
            this.x2 = x2
            this.y2 = y2
        }

        fun setCurve(
            x1: Float, y1: Float,
            ctrlx: Float, ctrly: Float,
            x2: Float, y2: Float
        ) {
            this.x1 = x1.toDouble()
            this.y1 = y1.toDouble()
            this.ctrlX = ctrlx.toDouble()
            this.ctrlY = ctrly.toDouble()
            this.x2 = x2.toDouble()
            this.y2 = y2.toDouble()
        }

        companion object {
            @Serial private const val serialVersionUID = -8511188402130719609L
        }
    }

    class QuadCurve2DDouble : QuadCurve2D, Serializable {

        override var x1: Double = 0.0
        override var y1: Double = 0.0
        override var ctrlX: Double = 0.0
        override var ctrlY: Double = 0.0
        override var x2: Double = 0.0
        override var y2: Double = 0.0
        override val bounds2D: Rectangle2D
            get() {
                val left = min(min(x1, x2), ctrlX)
                val top = min(min(y1, y2), ctrlY)
                val right = max(max(x1, x2), ctrlX)
                val bottom = max(max(y1, y2), ctrlY)
                return Rectangle2DDouble(
                    left, top,
                    right - left, bottom - top
                )
            }

        constructor()

        constructor(
            x1: Double, y1: Double,
            ctrlx: Double, ctrly: Double,
            x2: Double, y2: Double
        ) {
            setCurve(x1, y1, ctrlx, ctrly, x2, y2)
        }

        override val p1: Point2D
            get() = Point2DDouble(x1, y1)

        override val p2: Point2D
            get() = Point2DDouble(x2, y2)

        override val ctrlPt: Point2D
            get() = Point2DDouble(ctrlX, ctrlY)

        override fun setCurve(
            x1: Double, y1: Double,
            ctrlx: Double, ctrly: Double,
            x2: Double, y2: Double
        ) {
            this.x1 = x1
            this.y1 = y1
            this.ctrlX = ctrlx
            this.ctrlY = ctrly
            this.x2 = x2
            this.y2 = y2
        }

        companion object {
            @Serial private const val serialVersionUID = 4217149928428559721L
        }
    }

    abstract fun setCurve(
        x1: Double, y1: Double,
        ctrlx: Double, ctrly: Double,
        x2: Double, y2: Double
    )

    fun setCurve(coords: DoubleArray, offset: Int) {
        setCurve(
            coords[offset], coords[offset + 1],
            coords[offset + 2], coords[offset + 3],
            coords[offset + 4], coords[offset + 5]
        )
    }

    fun setCurve(p1: Point2D, cp: Point2D, p2: Point2D) {
        setCurve(
            p1.x, p1.y,
            cp.x, cp.y,
            p2.x, p2.y
        )
    }

    fun setCurve(pts: Array<Point2D>, offset: Int) {
        setCurve(
            pts[offset].x, pts[offset].y,
            pts[offset + 1].x, pts[offset + 1].y,
            pts[offset + 2].x, pts[offset + 2].y
        )
    }

    fun setCurve(c: QuadCurve2D) {
        setCurve(
            c.x1, c.y1,
            c.ctrlX, c.ctrlY,
            c.x2, c.y2
        )
    }

    val flatnessSq: Double
        get() = ptSegDistSq(
            x1, y1,
            x2, y2,
            ctrlX, ctrlY
        )

    val flatness: Double
        get() = ptSegDist(
            x1, y1,
            x2, y2,
            ctrlX, ctrlY
        )

    fun subdivide(left: QuadCurve2D?, right: QuadCurve2D?) {
        subdivide(
            this, left, right
        )
    }

    override fun contains(x: Double, y: Double): Boolean {
        val x1 = x1
        val y1 = y1
        val xc = ctrlX
        val yc = ctrlY
        val x2 = x2
        val y2 = y2

        /*
         * We have a convex shape bounded by quad curve Pc(t)
         * and ine Pl(t).
         *
         *     P1 = (x1, y1) - start point of curve
         *     P2 = (x2, y2) - end point of curve
         *     Pc = (xc, yc) - control point
         *
         *     Pq(t) = P1*(1 - t)^2 + 2*Pc*t*(1 - t) + P2*t^2 =
         *           = (P1 - 2*Pc + P2)*t^2 + 2*(Pc - P1)*t + P1
         *     Pl(t) = P1*(1 - t) + P2*t
         *     t = [0:1]
         *
         *     P = (x, y) - point of interest
         *
         * Let's look at second derivative of quad curve equation:
         *
         *     Pq''(t) = 2 * (P1 - 2 * Pc + P2) = Pq''
         *     It's constant vector.
         *
         * Let's draw a line through P to be parallel to this
         * vector and find the intersection of the quad curve
         * and the line.
         *
         * Pq(t) is point of intersection if system of equations
         * below has the solution.
         *
         *     L(s) = P + Pq''*s == Pq(t)
         *     Pq''*s + (P - Pq(t)) == 0
         *
         *     | xq''*s + (x - xq(t)) == 0
         *     | yq''*s + (y - yq(t)) == 0
         *
         * This system has the solution if rank of its matrix equals to 1.
         * That is, determinant of the matrix should be zero.
         *
         *     (y - yq(t))*xq'' == (x - xq(t))*yq''
         *
         * Let's solve this equation with 't' variable.
         * Also let kx = x1 - 2*xc + x2
         *          ky = y1 - 2*yc + y2
         *
         *     t0q = (1/2)*((x - x1)*ky - (y - y1)*kx) /
         *                 ((xc - x1)*ky - (yc - y1)*kx)
         *
         * Let's do the same for our line Pl(t):
         *
         *     t0l = ((x - x1)*ky - (y - y1)*kx) /
         *           ((x2 - x1)*ky - (y2 - y1)*kx)
         *
         * It's easy to check that t0q == t0l. This fact means
         * we can compute t0 only one time.
         *
         * In case t0 < 0 or t0 > 1, we have an intersections outside
         * of shape bounds. So, P is definitely out of shape.
         *
         * In case t0 is inside [0:1], we should calculate Pq(t0)
         * and Pl(t0). We have three points for now, and all of them
         * lie on one line. So, we just need to detect, is our point
         * of interest between points of intersections or not.
         *
         * If the denominator in the t0q and t0l equations is
         * zero, then the points must be collinear and so the
         * curve is degenerate and encloses no area.  Thus the
         * result is false.
         */
        val kx = x1 - 2 * xc + x2
        val ky = y1 - 2 * yc + y2
        val dx = x - x1
        val dy = y - y1
        val dxl = x2 - x1
        val dyl = y2 - y1

        val t0 = (dx * ky - dy * kx) / (dxl * ky - dyl * kx)
        if (t0 < 0 || t0 > 1 || t0 != t0) {
            return false
        }

        val xb = kx * t0 * t0 + 2 * (xc - x1) * t0 + x1
        val yb = ky * t0 * t0 + 2 * (yc - y1) * t0 + y1
        val xl = dxl * t0 + x1
        val yl = dyl * t0 + y1

        return (x >= xb && x < xl) ||
                (x >= xl && x < xb) ||
                (y >= yb && y < yl) ||
                (y >= yl && y < yb)
    }

    override fun contains(p: Point2D): Boolean {
        return contains(p.x, p.y)
    }

    override fun intersects(x: Double, y: Double, w: Double, h: Double): Boolean {
        // Trivially reject non-existant rectangles
        if (w <= 0 || h <= 0) {
            return false
        }

        // Trivially accept if either endpoint is inside the rectangle
        // (not on its border since it may end there and not go inside)
        // Record where they lie with respect to the rectangle.
        //     -1 => left, 0 => inside, 1 => right
        val x1 = x1
        val y1 = y1
        val x1tag = getTag(x1, x, x + w)
        val y1tag = getTag(y1, y, y + h)
        if (x1tag == INSIDE && y1tag == INSIDE) {
            return true
        }
        val x2 = x2
        val y2 = y2
        val x2tag = getTag(x2, x, x + w)
        val y2tag = getTag(y2, y, y + h)
        if (x2tag == INSIDE && y2tag == INSIDE) {
            return true
        }
        val ctrlx = ctrlX
        val ctrly = ctrlY
        val ctrlxtag = getTag(ctrlx, x, x + w)
        val ctrlytag = getTag(ctrly, y, y + h)

        // Trivially reject if all points are entirely to one side of
        // the rectangle.
        if (x1tag < INSIDE && x2tag < INSIDE && ctrlxtag < INSIDE) {
            return false // All points left
        }
        if (y1tag < INSIDE && y2tag < INSIDE && ctrlytag < INSIDE) {
            return false // All points above
        }
        if (x1tag > INSIDE && x2tag > INSIDE && ctrlxtag > INSIDE) {
            return false // All points right
        }
        if (y1tag > INSIDE && y2tag > INSIDE && ctrlytag > INSIDE) {
            return false // All points below
        }

        // Test for endpoints on the edge where either the segment
        // or the curve is headed "inwards" from them
        // Note: These tests are a superset of the fast endpoint tests
        //       above and thus repeat those tests, but take more time
        //       and cover more cases
        if (inwards(x1tag, x2tag, ctrlxtag) &&
            inwards(y1tag, y2tag, ctrlytag)
        ) {
            // First endpoint on border with either edge moving inside
            return true
        }
        if (inwards(x2tag, x1tag, ctrlxtag) &&
            inwards(y2tag, y1tag, ctrlytag)
        ) {
            // Second endpoint on border with either edge moving inside
            return true
        }

        // Trivially accept if endpoints span directly across the rectangle
        val xoverlap = (x1tag * x2tag <= 0)
        val yoverlap = (y1tag * y2tag <= 0)
        if (x1tag == INSIDE && x2tag == INSIDE && yoverlap) {
            return true
        }
        if (y1tag == INSIDE && y2tag == INSIDE && xoverlap) {
            return true
        }

        // We now know that both endpoints are outside the rectangle
        // but the 3 points are not all on one side of the rectangle.
        // Therefore the curve cannot be contained inside the rectangle,
        // but the rectangle might be contained inside the curve, or
        // the curve might intersect the boundary of the rectangle.
        val eqn = DoubleArray(3)
        val res = DoubleArray(3)
        if (!yoverlap) {
            // Both Y coordinates for the closing segment are above or
            // below the rectangle which means that we can only intersect
            // if the curve crosses the top (or bottom) of the rectangle
            // in more than one place and if those crossing locations
            // span the horizontal range of the rectangle.
            fillEqn(eqn, (if (y1tag < INSIDE) y else y + h), y1, ctrly, y2)
            return (solveQuadratic(
                eqn,
                res
            ) == 2 && evalQuadratic(
                res, 2, true, true, null,
                x1, ctrlx, x2
            ) == 2 && getTag(
                res[0],
                x,
                x + w
            ) * getTag(
                res[1],
                x,
                x + w
            ) <= 0)
        }

        // Y ranges overlap.  Now we examine the X ranges
        if (!xoverlap) {
            // Both X coordinates for the closing segment are left of
            // or right of the rectangle which means that we can only
            // intersect if the curve crosses the left (or right) edge
            // of the rectangle in more than one place and if those
            // crossing locations span the vertical range of the rectangle.
            fillEqn(eqn, (if (x1tag < INSIDE) x else x + w), x1, ctrlx, x2)
            return (solveQuadratic(
                eqn,
                res
            ) == 2 && evalQuadratic(
                res, 2, true, true, null,
                y1, ctrly, y2
            ) == 2 && getTag(
                res[0],
                y,
                y + h
            ) * getTag(
                res[1],
                y,
                y + h
            ) <= 0)
        }

        // The X and Y ranges of the endpoints overlap the X and Y
        // ranges of the rectangle, now find out how the endpoint
        // line segment intersects the Y range of the rectangle
        val dx = x2 - x1
        val dy = y2 - y1
        val k = y2 * x1 - x2 * y1
        var c1tag = if (y1tag == INSIDE) {
            x1tag
        } else {
            getTag(
                (k + dx * (if (y1tag < INSIDE) y else y + h)) / dy,
                x,
                x + w
            )
        }
        var c2tag = if (y2tag == INSIDE) {
            x2tag
        } else {
            getTag(
                (k + dx * (if (y2tag < INSIDE) y else y + h)) / dy,
                x,
                x + w
            )
        }
        // If the part of the line segment that intersects the Y range
        // of the rectangle crosses it horizontally - trivially accept
        if (c1tag * c2tag <= 0) {
            return true
        }

        // Now we know that both the X and Y ranges intersect and that
        // the endpoint line segment does not directly cross the rectangle.
        //
        // We can almost treat this case like one of the cases above
        // where both endpoints are to one side, except that we will
        // only get one intersection of the curve with the vertical
        // side of the rectangle.  This is because the endpoint segment
        // accounts for the other intersection.
        //
        // (Remember there is overlap in both the X and Y ranges which
        //  means that the segment must cross at least one vertical edge
        //  of the rectangle - in particular, the "near vertical side" -
        //  leaving only one intersection for the curve.)
        //
        // Now we calculate the y tags of the two intersections on the
        // "near vertical side" of the rectangle.  We will have one with
        // the endpoint segment, and one with the curve.  If those two
        // vertical intersections overlap the Y range of the rectangle,
        // we have an intersection.  Otherwise, we don't.

        // c1tag = vertical intersection class of the endpoint segment
        //
        // Choose the y tag of the endpoint that was not on the same
        // side of the rectangle as the subsegment calculated above.
        // Note that we can "steal" the existing Y tag of that endpoint
        // since it will be provably the same as the vertical intersection.
        c1tag = (if ((c1tag * x1tag <= 0)) y1tag else y2tag)

        // c2tag = vertical intersection class of the curve
        //
        // We have to calculate this one the straightforward way.
        // Note that the c2tag can still tell us which vertical edge
        // to test against.
        fillEqn(eqn, (if (c2tag < INSIDE) x else x + w), x1, ctrlx, x2)
        val num = solveQuadratic(eqn, res)

        // Note: We should be able to assert(num == 2); since the
        // X range "crosses" (not touches) the vertical boundary,
        // but we pass num to evalQuadratic for completeness.
        evalQuadratic(res, num, true, true, null, y1, ctrly, y2)

        // Note: We can assert(num evals == 1); since one of the
        // 2 crossings will be out of the [0,1] range.
        c2tag = getTag(
            res[0], y, y + h
        )

        // Finally, we have an intersection if the two crossings
        // overlap the Y range of the rectangle.
        return (c1tag * c2tag <= 0)
    }

    override fun intersects(r: Rectangle2D): Boolean {
        return intersects(r.x, r.y, r.width, r.height)
    }

    override fun contains(x: Double, y: Double, w: Double, h: Double): Boolean {
        if (w <= 0 || h <= 0) {
            return false
        }
        // Assertion: Quadratic curves closed by connecting their
        // endpoints are always convex.
        return (contains(x, y) &&
                contains(x + w, y) &&
                contains(x + w, y + h) &&
                contains(x, y + h))
    }

    override fun contains(r: Rectangle2D): Boolean {
        return contains(r.x, r.y, r.width, r.height)
    }

    override fun getPathIterator(at: AffineTransform?): PathIterator {
        return QuadIterator(
            this, at
        )
    }

    override fun getPathIterator(at: AffineTransform, flatness: Double): PathIterator {
        return FlatteningPathIterator(getPathIterator(at), flatness)
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public override fun clone(): Any {
        try {
            return super.clone()
        } catch (e: CloneNotSupportedException) {
            // this shouldn't happen, since we are Cloneable
            throw InternalError(e)
        }
    }

    companion object {

        private const val BELOW = -2
        private const val LOWEDGE = -1
        private const val INSIDE = 0
        private const val HIGHEDGE = 1
        private const val ABOVE = 2

        fun getFlatnessSq(
            x1: Double, y1: Double,
            ctrlx: Double, ctrly: Double,
            x2: Double, y2: Double
        ): Double {
            return ptSegDistSq(x1, y1, x2, y2, ctrlx, ctrly)
        }

        fun getFlatness(
            x1: Double, y1: Double,
            ctrlx: Double, ctrly: Double,
            x2: Double, y2: Double
        ): Double {
            return ptSegDist(x1, y1, x2, y2, ctrlx, ctrly)
        }

        @JvmStatic
        fun getFlatnessSq(coords: DoubleArray, offset: Int): Double {
            return ptSegDistSq(
                coords[offset], coords[offset + 1],
                coords[offset + 4], coords[offset + 5],
                coords[offset + 2], coords[offset + 3]
            )
        }

        fun getFlatness(coords: DoubleArray, offset: Int): Double {
            return ptSegDist(
                coords[offset], coords[offset + 1],
                coords[offset + 4], coords[offset + 5],
                coords[offset + 2], coords[offset + 3]
            )
        }

        fun subdivide(
            src: QuadCurve2D,
            left: QuadCurve2D?,
            right: QuadCurve2D?
        ) {
            val x1 = src.x1
            val y1 = src.y1
            var ctrlx = src.ctrlX
            var ctrly = src.ctrlY
            val x2 = src.x2
            val y2 = src.y2
            val ctrlx1 = (x1 + ctrlx) / 2.0
            val ctrly1 = (y1 + ctrly) / 2.0
            val ctrlx2 = (x2 + ctrlx) / 2.0
            val ctrly2 = (y2 + ctrly) / 2.0
            ctrlx = (ctrlx1 + ctrlx2) / 2.0
            ctrly = (ctrly1 + ctrly2) / 2.0
            left?.setCurve(x1, y1, ctrlx1, ctrly1, ctrlx, ctrly)
            right?.setCurve(ctrlx, ctrly, ctrlx2, ctrly2, x2, y2)
        }

        @JvmStatic
        fun subdivide(
            src: DoubleArray, srcoff: Int,
            left: DoubleArray?, leftoff: Int,
            right: DoubleArray?, rightoff: Int
        ) {
            var x1 = src[srcoff]
            var y1 = src[srcoff + 1]
            var ctrlx = src[srcoff + 2]
            var ctrly = src[srcoff + 3]
            var x2 = src[srcoff + 4]
            var y2 = src[srcoff + 5]
            if (left != null) {
                left[leftoff] = x1
                left[leftoff + 1] = y1
            }
            if (right != null) {
                right[rightoff + 4] = x2
                right[rightoff + 5] = y2
            }
            x1 = (x1 + ctrlx) / 2.0
            y1 = (y1 + ctrly) / 2.0
            x2 = (x2 + ctrlx) / 2.0
            y2 = (y2 + ctrly) / 2.0
            ctrlx = (x1 + x2) / 2.0
            ctrly = (y1 + y2) / 2.0
            if (left != null) {
                left[leftoff + 2] = x1
                left[leftoff + 3] = y1
                left[leftoff + 4] = ctrlx
                left[leftoff + 5] = ctrly
            }
            if (right != null) {
                right[rightoff] = ctrlx
                right[rightoff + 1] = ctrly
                right[rightoff + 2] = x2
                right[rightoff + 3] = y2
            }
        }

        @JvmStatic
        @JvmOverloads
        fun solveQuadratic(eqn: DoubleArray, res: DoubleArray = eqn): Int {
            val a = eqn[2]
            val b = eqn[1]
            val c = eqn[0]
            var roots = 0
            if (a == 0.0) {
                // The quadratic parabola has degenerated to a line.
                if (b == 0.0) {
                    // The line has degenerated to a constant.
                    return -1
                }
                res[roots++] = -c / b
            } else {
                // From Numerical Recipes, 5.6, Quadratic and Cubic Equations
                var d = b * b - 4.0 * a * c
                if (d < 0.0) {
                    // If d < 0.0, then there are no roots
                    return 0
                }
                d = sqrt(d)
                // For accuracy, calculate one root using:
                //     (-b +/- d) / 2a
                // and the other using:
                //     2c / (-b +/- d)
                // Choose the sign of the +/- so that b+d gets larger in magnitude
                if (b < 0.0) {
                    d = -d
                }
                val q = (b + d) / -2.0
                // We already tested a for being 0 above
                res[roots++] = q / a
                if (q != 0.0) {
                    res[roots++] = c / q
                }
            }
            return roots
        }

        private fun fillEqn(
            eqn: DoubleArray, `val`: Double,
            c1: Double, cp: Double, c2: Double
        ) {
            eqn[0] = c1 - `val`
            eqn[1] = cp + cp - c1 - c1
            eqn[2] = c1 - cp - cp + c2
        }

        private fun evalQuadratic(
            vals: DoubleArray, num: Int,
            include0: Boolean,
            include1: Boolean,
            inflect: DoubleArray?,
            c1: Double, ctrl: Double, c2: Double
        ): Int {
            var j = 0
            for (i in 0 until num) {
                val t = vals[i]
                if ((if (include0) t >= 0 else t > 0) &&
                    (if (include1) t <= 1 else t < 1) &&
                    (inflect == null ||
                            inflect[1] + 2 * inflect[2] * t != 0.0)
                ) {
                    val u = 1 - t
                    vals[j++] = c1 * u * u + 2 * ctrl * t * u + c2 * t * t
                }
            }
            return j
        }

        private fun getTag(coord: Double, low: Double, high: Double): Int {
            if (coord <= low) {
                return (if (coord < low) BELOW else LOWEDGE)
            }
            if (coord >= high) {
                return (if (coord > high) ABOVE else HIGHEDGE)
            }
            return INSIDE
        }

        private fun inwards(pttag: Int, opt1tag: Int, opt2tag: Int): Boolean {
            return when (pttag) {
                BELOW, ABOVE -> false
                LOWEDGE -> opt1tag >= INSIDE || opt2tag >= INSIDE
                INSIDE -> true
                HIGHEDGE -> opt1tag <= INSIDE || opt2tag <= INSIDE
                else -> false
            }
        }
    }
}
