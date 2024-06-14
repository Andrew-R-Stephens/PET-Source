package com.TritiumGaming.phasmophobiaevidencepicker.data.utils.geometry

import android.os.Build
import com.TritiumGaming.phasmophobiaevidencepicker.data.utils.geometry.Curve.Companion.pointCrossingsForCubic
import com.TritiumGaming.phasmophobiaevidencepicker.data.utils.geometry.Curve.Companion.pointCrossingsForLine
import com.TritiumGaming.phasmophobiaevidencepicker.data.utils.geometry.Curve.Companion.rectCrossingsForCubic
import com.TritiumGaming.phasmophobiaevidencepicker.data.utils.geometry.Curve.Companion.rectCrossingsForLine
import com.TritiumGaming.phasmophobiaevidencepicker.data.utils.geometry.Line2D.Companion.ptSegDistSq
import com.TritiumGaming.phasmophobiaevidencepicker.data.utils.geometry.Point2D.Point2DDouble
import com.TritiumGaming.phasmophobiaevidencepicker.data.utils.geometry.Point2D.Point2DFloat
import com.TritiumGaming.phasmophobiaevidencepicker.data.utils.geometry.QuadCurve2D.Companion.solveQuadratic
import com.TritiumGaming.phasmophobiaevidencepicker.data.utils.geometry.Rectangle2D.Rectangle2DDouble
import com.TritiumGaming.phasmophobiaevidencepicker.data.utils.geometry.Rectangle2D.Rectangle2DFloat
import java.io.Serial
import java.io.Serializable
import java.util.Arrays
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.cbrt
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

abstract class CubicCurve2D protected constructor() : Shape, Cloneable {
    class CubicCurveFloat : CubicCurve2D, Serializable {
        override var x1: Double = 0.0
        override var y1: Double = 0.0
        override var x2: Double = 0.0
        override var y2: Double = 0.0
        override var ctrlX1: Double = 0.0
        override var ctrlY1: Double = 0.0
        override var ctrlX2: Double = 0.0
        override var ctrlY2: Double = 0.0
        
        constructor()

        constructor(
            x1: Float, y1: Float,
            ctrlx1: Float, ctrly1: Float,
            ctrlx2: Float, ctrly2: Float,
            x2: Float, y2: Float
        ) {
            setCurve(x1, y1, ctrlx1, ctrly1, ctrlx2, ctrly2, x2, y2)
        }

        override val ctrlP1: Point2D
            get() = Point2DFloat(ctrlX1.toFloat(), ctrlY1.toFloat())
        override val ctrlP2: Point2D
            get() = Point2DFloat(ctrlX2.toFloat(), ctrlY2.toFloat())
        override val p1: Point2D
            get() = Point2DFloat(x1.toFloat(), y1.toFloat())
        override val p2: Point2D
            get() = Point2DFloat(x2.toFloat(), y2.toFloat())

        override fun setCurve(
            x1: Double, y1: Double,
            ctrlx1: Double, ctrly1: Double,
            ctrlx2: Double, ctrly2: Double,
            x2: Double, y2: Double
        ) {
            this.x1 = x1
            this.y1 = y1
            this.ctrlX1 = ctrlx1
            this.ctrlY1 = ctrly1
            this.ctrlX2 = ctrlx2
            this.ctrlY2 = ctrly2
            this.x2 = x2
            this.y2 = y2
        }

        override val bounds2D: Rectangle2D
            get() {
                val left = min(
                    min(x1, x2),
                    min(ctrlX1, ctrlX2)
                ).toFloat()
                val top = min(
                    min(y1, y2),
                    min(ctrlY1, ctrlY2)
                ).toFloat()
                val right = max(
                    max(x1, x2),
                    max(ctrlX1, ctrlX2)
                ).toFloat()
                val bottom = max(
                    max(y1, y2),
                    max(ctrlY1, ctrlY2)
                ).toFloat()
                return Rectangle2DFloat(
                    left, top,
                    right - left, bottom - top
                )
            }

        fun setCurve(
            x1: Float, y1: Float,
            ctrlx1: Float, ctrly1: Float,
            ctrlx2: Float, ctrly2: Float,
            x2: Float, y2: Float
        ) {
            this.x1 = x1.toDouble()
            this.y1 = y1.toDouble()
            this.ctrlX1 = ctrlx1.toDouble()
            this.ctrlY1 = ctrly1.toDouble()
            this.ctrlX2 = ctrlx2.toDouble()
            this.ctrlY2 = ctrly2.toDouble()
            this.x2 = x2.toDouble()
            this.y2 = y2.toDouble()
        }

        companion object {
            private const val serialVersionUID = -1272015596714244385L
        }
    }

    class CubicCurve2DDouble : CubicCurve2D, Serializable {
        override var x1: Double = 0.0
        override var y1: Double = 0.0
        override var x2: Double = 0.0
        override var y2: Double = 0.0
        override var ctrlX1: Double = 0.0
        override var ctrlY1: Double = 0.0
        override var ctrlX2: Double = 0.0
        override var ctrlY2: Double = 0.0

        constructor()

        constructor(
            x1: Double, y1: Double,
            ctrlx1: Double, ctrly1: Double,
            ctrlx2: Double, ctrly2: Double,
            x2: Double, y2: Double
        ) {
            setCurve(x1, y1, ctrlx1, ctrly1, ctrlx2, ctrly2, x2, y2)
        }

        override val ctrlP1: Point2D
            get() = Point2DDouble(ctrlX1, ctrlY1)
        override val ctrlP2: Point2D
            get() = Point2DDouble(ctrlX2, ctrlY2)
        override val p1: Point2D
            get() = Point2DDouble(x1, y1)
        override val p2: Point2D
            get() = Point2DDouble(x2, y2)
        override val bounds: Rectangle
            get() = super.bounds
        override val bounds2D: Rectangle2D
            get() {
                val left = min(
                    min(x1, x2),
                    min(ctrlX1, ctrlX2)
                )
                val top = min(
                    min(y1, y2),
                    min(ctrlY1, ctrlY2)
                )
                val right = max(
                    max(x1, x2),
                    max(ctrlX1, ctrlX2)
                )
                val bottom = max(
                    max(y1, y2),
                    max(ctrlY1, ctrlY2)
                )
                return Rectangle2DDouble(
                    left, top,
                    right - left, bottom - top
                )
            }

        override fun setCurve(
            x1: Double, y1: Double,
            ctrlx1: Double, ctrly1: Double,
            ctrlx2: Double, ctrly2: Double,
            x2: Double, y2: Double
        ) {
            this.x1 = x1
            this.y1 = y1
            this.ctrlX1 = ctrlx1
            this.ctrlY1 = ctrly1
            this.ctrlX2 = ctrlx2
            this.ctrlY2 = ctrly2
            this.x2 = x2
            this.y2 = y2
        }

        companion object {
            @Serial private const val serialVersionUID = -4202960122839707295L
        }
    }

    abstract val x1: Double
    abstract val y1: Double

    abstract val x2: Double
    abstract val y2: Double

    abstract val p1: Point2D
    abstract val p2: Point2D

    abstract val ctrlX1: Double
    abstract val ctrlY1: Double

    abstract val ctrlX2: Double
    abstract val ctrlY2: Double

    abstract val ctrlP1: Point2D
    abstract val ctrlP2: Point2D

    abstract fun setCurve(
        x1: Double, y1: Double,
        ctrlx1: Double, ctrly1: Double,
        ctrlx2: Double, ctrly2: Double,
        x2: Double, y2: Double
    )

    fun setCurve(coords: DoubleArray, offset: Int) {
        setCurve(
            coords[offset], coords[offset + 1],
            coords[offset + 2], coords[offset + 3],
            coords[offset + 4], coords[offset + 5],
            coords[offset + 6], coords[offset + 7]
        )
    }

    fun setCurve(p1: Point2D, cp1: Point2D, cp2: Point2D, p2: Point2D) {
        setCurve(
            p1.x, p1.y, cp1.x, cp1.y,
            cp2.x, cp2.y, p2.x, p2.y
        )
    }

    fun setCurve(pts: Array<Point2D>, offset: Int) {
        setCurve(
            pts[offset].x, pts[offset].y,
            pts[offset + 1].x, pts[offset + 1].y,
            pts[offset + 2].x, pts[offset + 2].y,
            pts[offset + 3].x, pts[offset + 3].y
        )
    }

    fun setCurve(c: CubicCurve2D) {
        setCurve(
            c.x1, c.y1, c.ctrlX1, c.ctrlY1,
            c.ctrlX2, c.ctrlY2, c.x2, c.y2
        )
    }

    val flatnessSq: Double
        get() = getFlatnessSq(
            x1, y1, ctrlX1, ctrlY1,
            ctrlX2, ctrlY2, x2, y2
        )

    val flatness: Double
        get() = getFlatness(
            x1, y1, ctrlX1, ctrlY1,
            ctrlX2, ctrlY2, x2, y2
        )

    fun subdivide(left: CubicCurve2D?, right: CubicCurve2D?) {
        subdivide(
            this, left, right
        )
    }

    override fun contains(x: Double, y: Double): Boolean {
        if (x * 0.0 + y * 0.0 != 0.0) {
            /* Either x or y was infinite or NaN.
             * A NaN always produces a negative response to any test
             * and Infinity values cannot be "inside" any path so
             * they should return false as well.
             */
            return false
        }
        // We count the "Y" crossings to determine if the point is
        // inside the curve bounded by its closing line.
        val x1 = x1
        val y1 = y1
        val x2 = x2
        val y2 = y2
        val crossings =
            (pointCrossingsForLine(x, y, x1, y1, x2, y2) +
                    pointCrossingsForCubic(
                        x, y,
                        x1, y1,
                        ctrlX1, ctrlY1,
                        ctrlX2, ctrlY2,
                        x2, y2, 0
                    ))
        return ((crossings and 1) == 1)
    }


    override fun contains(p: Point2D): Boolean {
        return contains(p.x, p.y)
    }


    override fun intersects(x: Double, y: Double, w: Double, h: Double): Boolean {
        // Trivially reject non-existant rectangles
        if (w <= 0 || h <= 0) {
            return false
        }

        val numCrossings = rectCrossings(x, y, w, h)
        // the intended return value is
        // numCrossings != 0 || numCrossings == Curve.RECT_INTERSECTS
        // but if (numCrossings != 0) numCrossings == INTERSECTS won't matter
        // and if !(numCrossings != 0) then numCrossings == 0, so
        // numCrossings != RECT_INTERSECT
        return numCrossings != 0
    }


    override fun intersects(r: Rectangle2D): Boolean {
        return intersects(r.x, r.y, r.width, r.height)
    }


    override fun contains(x: Double, y: Double, w: Double, h: Double): Boolean {
        if (w <= 0 || h <= 0) {
            return false
        }

        val numCrossings = rectCrossings(x, y, w, h)
        return !(numCrossings == 0 || numCrossings == Curve.RECT_INTERSECTS)
    }

    private fun rectCrossings(x: Double, y: Double, w: Double, h: Double): Int {
        var crossings = 0
        if (!(x1 == x2 && y1 == y2)) {
            crossings = rectCrossingsForLine(
                crossings,
                x, y,
                x + w, y + h,
                x1, y1,
                x2, y2
            )
            if (crossings == Curve.RECT_INTERSECTS) {
                return crossings
            }
        }
        // we call this with the curve's direction reversed, because we wanted
        // to call rectCrossingsForLine first, because it's cheaper.
        return rectCrossingsForCubic(
            crossings,
            x, y,
            x + w, y + h,
            x2, y2,
            ctrlX2, ctrlY2,
            ctrlX1, ctrlY1,
            x1, y1, 0
        )
    }


    override fun contains(r: Rectangle2D): Boolean {
        return contains(r.x, r.y, r.width, r.height)
    }


    override val bounds: Rectangle
        get() = bounds2D.bounds


    override fun getPathIterator(at: AffineTransform?): PathIterator {
        return CubicIterator(
            this, at
        )
    }


    override fun getPathIterator(at: AffineTransform, flatness: Double): PathIterator {
        return FlatteningPathIterator(getPathIterator(at), flatness)
    }


    public override fun clone(): Any {
        try {
            return super.clone()
        } catch (e: CloneNotSupportedException) {
            // this shouldn't happen, since we are Cloneable
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                throw InternalError(e)
            }
        }
        return Any()
    }

    companion object {
        fun getFlatnessSq(
            x1: Double, y1: Double,
            ctrlx1: Double, ctrly1: Double,
            ctrlx2: Double, ctrly2: Double,
            x2: Double, y2: Double
        ): Double {
            return max(
                ptSegDistSq(x1, y1, x2, y2, ctrlx1, ctrly1),
                ptSegDistSq(x1, y1, x2, y2, ctrlx2, ctrly2)
            )
        }

        fun getFlatness(
            x1: Double, y1: Double,
            ctrlx1: Double, ctrly1: Double,
            ctrlx2: Double, ctrly2: Double,
            x2: Double, y2: Double
        ): Double {
            return sqrt(
                getFlatnessSq(
                    x1, y1, ctrlx1, ctrly1,
                    ctrlx2, ctrly2, x2, y2
                )
            )
        }

        @JvmStatic
        fun getFlatnessSq(coords: DoubleArray, offset: Int): Double {
            return getFlatnessSq(
                coords[offset], coords[offset + 1],
                coords[offset + 2], coords[offset + 3],
                coords[offset + 4], coords[offset + 5],
                coords[offset + 6], coords[offset + 7]
            )
        }

        fun getFlatness(coords: DoubleArray, offset: Int): Double {
            return getFlatness(
                coords[offset], coords[offset + 1],
                coords[offset + 2], coords[offset + 3],
                coords[offset + 4], coords[offset + 5],
                coords[offset + 6], coords[offset + 7]
            )
        }

        fun subdivide(
            src: CubicCurve2D,
            left: CubicCurve2D?,
            right: CubicCurve2D?
        ) {
            val x1 = src.x1
            val y1 = src.y1
            var ctrlx1 = src.ctrlX1
            var ctrly1 = src.ctrlY1
            var ctrlx2 = src.ctrlX2
            var ctrly2 = src.ctrlY2
            val x2 = src.x2
            val y2 = src.y2
            var centerx = (ctrlx1 + ctrlx2) / 2.0
            var centery = (ctrly1 + ctrly2) / 2.0
            ctrlx1 = (x1 + ctrlx1) / 2.0
            ctrly1 = (y1 + ctrly1) / 2.0
            ctrlx2 = (x2 + ctrlx2) / 2.0
            ctrly2 = (y2 + ctrly2) / 2.0
            val ctrlx12 = (ctrlx1 + centerx) / 2.0
            val ctrly12 = (ctrly1 + centery) / 2.0
            val ctrlx21 = (ctrlx2 + centerx) / 2.0
            val ctrly21 = (ctrly2 + centery) / 2.0
            centerx = (ctrlx12 + ctrlx21) / 2.0
            centery = (ctrly12 + ctrly21) / 2.0
            left?.setCurve(
                x1, y1, ctrlx1, ctrly1,
                ctrlx12, ctrly12, centerx, centery
            )
            right?.setCurve(
                centerx, centery, ctrlx21, ctrly21,
                ctrlx2, ctrly2, x2, y2
            )
        }

        @JvmStatic
        fun subdivide(
            src: DoubleArray, srcoff: Int,
            left: DoubleArray?, leftoff: Int,
            right: DoubleArray?, rightoff: Int
        ) {
            var x1 = src[srcoff]
            var y1 = src[srcoff + 1]
            var ctrlx1 = src[srcoff + 2]
            var ctrly1 = src[srcoff + 3]
            var ctrlx2 = src[srcoff + 4]
            var ctrly2 = src[srcoff + 5]
            var x2 = src[srcoff + 6]
            var y2 = src[srcoff + 7]
            if (left != null) {
                left[leftoff] = x1
                left[leftoff + 1] = y1
            }
            if (right != null) {
                right[rightoff + 6] = x2
                right[rightoff + 7] = y2
            }
            x1 = (x1 + ctrlx1) / 2.0
            y1 = (y1 + ctrly1) / 2.0
            x2 = (x2 + ctrlx2) / 2.0
            y2 = (y2 + ctrly2) / 2.0
            var centerx = (ctrlx1 + ctrlx2) / 2.0
            var centery = (ctrly1 + ctrly2) / 2.0
            ctrlx1 = (x1 + centerx) / 2.0
            ctrly1 = (y1 + centery) / 2.0
            ctrlx2 = (x2 + centerx) / 2.0
            ctrly2 = (y2 + centery) / 2.0
            centerx = (ctrlx1 + ctrlx2) / 2.0
            centery = (ctrly1 + ctrly2) / 2.0
            if (left != null) {
                left[leftoff + 2] = x1
                left[leftoff + 3] = y1
                left[leftoff + 4] = ctrlx1
                left[leftoff + 5] = ctrly1
                left[leftoff + 6] = centerx
                left[leftoff + 7] = centery
            }
            if (right != null) {
                right[rightoff] = centerx
                right[rightoff + 1] = centery
                right[rightoff + 2] = ctrlx2
                right[rightoff + 3] = ctrly2
                right[rightoff + 4] = x2
                right[rightoff + 5] = y2
            }
        }

        @JvmOverloads
        fun solveCubic(eqn: DoubleArray, res: DoubleArray = eqn): Int {
            // From Graphics Gems:
            // http://tog.acm.org/resources/GraphicsGems/gems/Roots3And4.c
            var eqn = eqn
            val d = eqn[3]
            if (d == 0.0) {
                return solveQuadratic(eqn, res)
            }

            /* normal form: x^3 + Ax^2 + Bx + C = 0 */
            val A = eqn[2] / d
            val B = eqn[1] / d
            val C = eqn[0] / d


            //  substitute x = y - A/3 to eliminate quadratic term:
            //     x^3 +Px + Q = 0
            //
            // Since we actually need P/3 and Q/2 for all of the
            // calculations that follow, we will calculate
            // p = P/3
            // q = Q/2
            // instead and use those values for simplicity of the code.
            val sq_A = A * A
            val p = 1.0 / 3 * (-1.0 / 3 * sq_A + B)
            val q = 1.0 / 2 * (2.0 / 27 * A * sq_A - 1.0 / 3 * A * B + C)

            /* use Cardano's formula */
            val cb_p = p * p * p
            val D = q * q + cb_p

            val sub = 1.0 / 3 * A

            var num: Int
            if (D < 0) { /* Casus irreducibilis: three real solutions */
                // see: http://en.wikipedia.org/wiki/Cubic_function#Trigonometric_.28and_hyperbolic.29_method
                val phi = 1.0 / 3 * acos(-q / sqrt(-cb_p))
                val t = 2 * sqrt(-p)

                if (res == eqn) {
                    eqn = eqn.copyOf(4)
                }

                res[0] = (t * cos(phi))
                res[1] = (-t * cos(phi + Math.PI / 3))
                res[2] = (-t * cos(phi - Math.PI / 3))
                num = 3

                for (i in 0 until num) {
                    res[i] -= sub
                }
            } else {
                // Please see the comment in fixRoots marked 'XXX' before changing
                // any of the code in this case.
                val sqrt_D = sqrt(D)
                val u = cbrt(sqrt_D - q)
                val v = -cbrt(sqrt_D + q)
                val uv = u + v

                num = 1

                val err = 1200000000 * Math.ulp(abs(uv) + abs(sub))
                if (iszero(D, err) || within(u, v, err)) {
                    if (res == eqn) {
                        eqn = eqn.copyOf(4)
                    }
                    res[1] = -(uv / 2) - sub
                    num = 2
                }
                // this must be done after the potential Arrays.copyOf
                res[0] = uv - sub
            }

            if (num > 1) { // num == 3 || num == 2
                num = fixRoots(eqn, res, num)
            }
            if (num > 2 && (res[2] == res[1] || res[2] == res[0])) {
                num--
            }
            if (num > 1 && res[1] == res[0]) {
                res[1] = res[--num] // Copies res[2] to res[1] if needed
            }
            return num
        }

        // preconditions: eqn != res && eqn[3] != 0 && num > 1
        // This method tries to improve the accuracy of the roots of eqn (which
        // should be in res). It also might eliminate roots in res if it decideds
        // that they're not real roots. It will not check for roots that the
        // computation of res might have missed, so this method should only be
        // used when the roots in res have been computed using an algorithm
        // that never underestimates the number of roots (such as solveCubic above)
        private fun fixRoots(eqn: DoubleArray, res: DoubleArray, num: Int): Int {
            val intervals = doubleArrayOf(eqn[1], 2 * eqn[2], 3 * eqn[3])
            var critCount = solveQuadratic(intervals, intervals)
            if (critCount == 2 && intervals[0] == intervals[1]) {
                critCount--
            }
            if (critCount == 2 && intervals[0] > intervals[1]) {
                val tmp = intervals[0]
                intervals[0] = intervals[1]
                intervals[1] = tmp
            }

            // below we use critCount to possibly filter out roots that shouldn't
            // have been computed. We require that eqn[3] != 0, so eqn is a proper
            // cubic, which means that its limits at -/+inf are -/+inf or +/-inf.
            // Therefore, if critCount==2, the curve is shaped like a sideways S,
            // and it could have 1-3 roots. If critCount==0 it is monotonic, and
            // if critCount==1 it is monotonic with a single point where it is
            // flat. In the last 2 cases there can only be 1 root. So in cases
            // where num > 1 but critCount < 2, we eliminate all roots in res
            // except one.
            if (num == 3) {
                val xe = getRootUpperBound(eqn)
                val x0 = -xe

                Arrays.sort(res, 0, num)
                if (critCount == 2) {
                    // this just tries to improve the accuracy of the computed
                    // roots using Newton's method.
                    res[0] = refineRootWithHint(eqn, x0, intervals[0], res[0])
                    res[1] = refineRootWithHint(eqn, intervals[0], intervals[1], res[1])
                    res[2] = refineRootWithHint(eqn, intervals[1], xe, res[2])
                    return 3
                } else if (critCount == 1) {
                    // we only need fx0 and fxe for the sign of the polynomial
                    // at -inf and +inf respectively, so we don't need to do
                    // fx0 = solveEqn(eqn, 3, x0); fxe = solveEqn(eqn, 3, xe)
                    val fxe = eqn[3]
                    val fx0 = -fxe

                    val x1 = intervals[0]
                    val fx1 = solveEqn(eqn, 3, x1)

                    // if critCount == 1 or critCount == 0, but num == 3 then
                    // something has gone wrong. This branch and the one below
                    // would ideally never execute, but if they do we can't know
                    // which of the computed roots is closest to the real root;
                    // therefore, we can't use refineRootWithHint. But even if
                    // we did know, being here most likely means that the
                    // curve is very flat close to two of the computed roots
                    // (or maybe even all three). This might make Newton's method
                    // fail altogether, which would be a pain to detect and fix.
                    // This is why we use a very stable bisection method.
                    if (oppositeSigns(fx0, fx1)) {
                        res[0] = bisectRootWithHint(eqn, x0, x1, res[0])
                    } else if (oppositeSigns(fx1, fxe)) {
                        res[0] = bisectRootWithHint(eqn, x1, xe, res[2])
                    } else  /* fx1 must be 0 */ {
                        res[0] = x1
                    }
                    // return 1
                } else if (critCount == 0) {
                    res[0] = bisectRootWithHint(eqn, x0, xe, res[1])
                    // return 1
                }
            } else if (num == 2 && critCount == 2) {
                // XXX: here we assume that res[0] has better accuracy than res[1].
                // This is true because this method is only used from solveCubic
                // which puts in res[0] the root that it would compute anyway even
                // if num==1. If this method is ever used from any other method, or
                // if the solveCubic implementation changes, this assumption should
                // be reevaluated, and the choice of goodRoot might have to become
                // goodRoot = (abs(eqn'(res[0])) > abs(eqn'(res[1]))) ? res[0] : res[1]
                // where eqn' is the derivative of eqn.
                val goodRoot = res[0]
                val badRoot = res[1]
                val x1 = intervals[0]
                val x2 = intervals[1]
                // If a cubic curve really has 2 roots, one of those roots must be
                // at a critical point. That can't be goodRoot, so we compute x to
                // be the farthest critical point from goodRoot. If there are two
                // roots, x must be the second one, so we evaluate eqn at x, and if
                // it is zero (or close enough) we put x in res[1] (or badRoot, if
                // |solveEqn(eqn, 3, badRoot)| < |solveEqn(eqn, 3, x)| but this
                // shouldn't happen often).
                val x = if (abs(x1 - goodRoot) > abs(x2 - goodRoot)) x1 else x2
                val fx = solveEqn(eqn, 3, x)

                if (iszero(fx, 10000000 * Math.ulp(x))) {
                    val badRootVal = solveEqn(eqn, 3, badRoot)
                    res[1] = if (abs(badRootVal) < abs(fx)) badRoot else x
                    return 2
                }
            } // else there can only be one root - goodRoot, and it is already in res[0]


            return 1
        }

        // use newton's method.
        private fun refineRootWithHint(
            eqn: DoubleArray,
            min: Double,
            max: Double,
            t: Double
        ): Double {
            var t = t
            if (!inInterval(t, min, max)) {
                return t
            }
            val deriv = doubleArrayOf(eqn[1], 2 * eqn[2], 3 * eqn[3])
            val origt = t
            for (i in 0..2) {
                val slope = solveEqn(deriv, 2, t)
                val y = solveEqn(eqn, 3, t)
                val delta = -(y / slope)
                val newt = t + delta

                if (slope == 0.0 || y == 0.0 || t == newt) {
                    break
                }

                t = newt
            }
            if (within(t, origt, 1000 * Math.ulp(origt)) && inInterval(t, min, max)) {
                return t
            }
            return origt
        }

        private fun bisectRootWithHint(
            eqn: DoubleArray,
            x0: Double,
            xe: Double,
            hint: Double
        ): Double {
            var x0 = x0
            var xe = xe
            var delta1 = min(abs(hint - x0) / 64, 0.0625)
            var delta2 = min(abs(hint - xe) / 64, 0.0625)
            var x02 = hint - delta1
            var xe2 = hint + delta2
            var fx02 = solveEqn(eqn, 3, x02)
            var fxe2 = solveEqn(eqn, 3, xe2)
            while (oppositeSigns(fx02, fxe2)) {
                if (x02 >= xe2) {
                    return x02
                }
                x0 = x02
                xe = xe2
                delta1 /= 64.0
                delta2 /= 64.0
                x02 = hint - delta1
                xe2 = hint + delta2
                fx02 = solveEqn(eqn, 3, x02)
                fxe2 = solveEqn(eqn, 3, xe2)
            }
            if (fx02 == 0.0) {
                return x02
            }
            if (fxe2 == 0.0) {
                return xe2
            }

            return bisectRoot(eqn, x0, xe)
        }

        private fun bisectRoot(eqn: DoubleArray, x0: Double, xe: Double): Double {
            var x0 = x0
            var xe = xe
            var fx0 = solveEqn(eqn, 3, x0)
            var m = x0 + (xe - x0) / 2
            while (m != x0 && m != xe) {
                val fm = solveEqn(eqn, 3, m)
                if (fm == 0.0) {
                    return m
                }
                if (oppositeSigns(fx0, fm)) {
                    xe = m
                } else {
                    fx0 = fm
                    x0 = m
                }
                m = x0 + (xe - x0) / 2
            }
            return m
        }

        private fun inInterval(t: Double, min: Double, max: Double): Boolean {
            return min <= t && t <= max
        }

        private fun within(x: Double, y: Double, err: Double): Boolean {
            val d = y - x
            return (d <= err && d >= -err)
        }

        private fun iszero(x: Double, err: Double): Boolean {
            return within(x, 0.0, err)
        }

        private fun oppositeSigns(x1: Double, x2: Double): Boolean {
            return (x1 < 0 && x2 > 0) || (x1 > 0 && x2 < 0)
        }

        private fun solveEqn(eqn: DoubleArray, order: Int, t: Double): Double {
            var order = order
            var v = eqn[order]
            while (--order >= 0) {
                v = v * t + eqn[order]
            }
            return v
        }

        /*
     * Computes M+1 where M is an upper bound for all the roots in of eqn.
     * See: http://en.wikipedia.org/wiki/Sturm%27s_theorem#Applications.
     * The above link doesn't contain a proof, but I [dlila] proved it myself
     * so the result is reliable. The proof isn't difficult, but it's a bit
     * long to include here.
     * Precondition: eqn must represent a cubic polynomial
     */
        private fun getRootUpperBound(eqn: DoubleArray): Double {
            val d = eqn[3]
            val a = eqn[2]
            val b = eqn[1]
            val c = eqn[0]

            var M = 1 + max(max(abs(a), abs(b)), abs(c)) / abs(d)
            M += Math.ulp(M) + 1
            return M
        }
    }
}
