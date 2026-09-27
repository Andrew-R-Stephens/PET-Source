package com.tritiumgaming.core.common.graphics.geometry

import com.tritiumgaming.core.common.graphics.geometry.Curve.Companion.pointCrossingsForCubic
import com.tritiumgaming.core.common.graphics.geometry.Curve.Companion.pointCrossingsForLine
import com.tritiumgaming.core.common.graphics.geometry.Curve.Companion.pointCrossingsForPath
import com.tritiumgaming.core.common.graphics.geometry.Curve.Companion.pointCrossingsForQuad
import com.tritiumgaming.core.common.graphics.geometry.Curve.Companion.rectCrossingsForCubic
import com.tritiumgaming.core.common.graphics.geometry.Curve.Companion.rectCrossingsForLine
import com.tritiumgaming.core.common.graphics.geometry.Curve.Companion.rectCrossingsForPath
import com.tritiumgaming.core.common.graphics.geometry.Curve.Companion.rectCrossingsForQuad
import java.io.IOException
import java.io.InvalidObjectException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.io.StreamCorruptedException
import kotlin.math.max

/**
 * The `Path2D` class provides a simple, yet flexible
 * shape which represents an arbitrary geometric path.
 * It can fully represent any path which can be iterated by the
 * [PathIterator] interface including all of its segment
 * types and winding rules and it implements all of the
 * basic hit testing methods of the [Shape] interface.
 *
 *
 * Use [Path2D.Path2DFloat] when dealing with data that can be represented
 * and used with floating point precision.  Use [Path2D.Path2DDouble]
 * for data that requires the accuracy or range of double precision.
 *
 *
 * `Path2D` provides exactly those facilities required for
 * basic construction and management of a geometric path and
 * implementation of the above interfaces with little added
 * interpretation.
 * If it is useful to manipulate the interiors of closed
 * geometric shapes beyond simple hit testing then the
 * class provides additional capabilities
 * specifically targeted at closed figures.
 * While both classes nominally implement the `Shape`
 * interface, they differ in purpose and together they provide
 * two useful views of a geometric shape where `Path2D`
 * deals primarily with a trajectory formed by path segments
 * and `Area` deals more with interpretation and manipulation
 * of enclosed regions of 2D geometric space.
 *
 *
 * The [PathIterator] interface has more detailed descriptions
 * of the types of segments that make up a path and the winding rules
 * that control how to determine which regions are inside or outside
 * the path.
 *
 * @author Jim Graham
 * @noinspection SameParameterValue
 * @since 1.6
 */
abstract class Path2D : Shape, Cloneable {

    override val bounds: Rectangle
        get() = bounds2D.bounds

    @get:Synchronized
    val currentPoint: Point2D?
        /**
         * Returns the coordinates most recently added to the end of the path
         * as a [Point2D] object.
         *
         * @return a `Point2D` object containing the ending coordinates of
         * the path or `null` if there are no points in the path.
         * @since 1.6
         */
        get() {
            var index = numCoords
            if (numTypes < 1 || index < 1) {
                return null
            }
            if (pointTypes[numTypes - 1] == SEG_CLOSE) {
                loop@ for (i in numTypes - 2 downTo 1) {
                    when (pointTypes[i]) {
                        SEG_MOVETO -> break@loop
                        SEG_LINETO -> index -= 2
                        SEG_QUADTO -> index -= 4
                        SEG_CUBICTO -> index -= 6
                        SEG_CLOSE -> {}
                    }
                }
            }
            return getPoint(index - 2)
        }

    @Transient lateinit var pointTypes: ByteArray

    @Transient var numTypes: Int = 0

    @Transient var numCoords: Int = 0

    @Transient var windingRule: Int = 0
        set(rule) {
            require(!(rule != WIND_EVEN_ODD && rule != WIND_NON_ZERO)) {
                "winding rule must be WIND_EVEN_ODD or WIND_NON_ZERO"
            }
            field = rule
        }

    /**
     * Constructs a new empty `Path2D` object.
     * It is assumed that the package sibling subclass that is
     * defaulting to this constructor will fill in all values.
     *
     * @since 1.6
     */
    /* private protected */
    internal constructor()

    /**
     * Constructs a new `Path2D` object from the given
     * specified initial values.
     * This method is only intended for internal use and should
     * not be made public if the other constructors for this class
     * are ever exposed.
     *
     * @param rule         the winding rule
     * @param initialTypes the size to make the initial array to
     * store the path segment types
     * @throws IllegalArgumentException   if `rule` is not either
     * [.WIND_EVEN_ODD] or [.WIND_NON_ZERO]
     * @throws NegativeArraySizeException if `initialTypes` is negative
     * @since 1.6
     */
    /* private protected */
    internal constructor(rule: Int, initialTypes: Int) {
        windingRule = rule
        this.pointTypes = ByteArray(initialTypes)
    }

    abstract fun cloneCoordsFloat(at: AffineTransform?): FloatArray

    abstract fun cloneCoordsDouble(at: AffineTransform?): DoubleArray

    abstract fun append(x: Float, y: Float)

    abstract fun append(x: Double, y: Double)

    abstract fun getPoint(coordindex: Int): Point2D

    abstract fun needRoom(needMove: Boolean, newCoords: Int)

    abstract fun pointCrossings(px: Double, py: Double): Int

    abstract fun rectCrossings(
        rxmin: Double, rymin: Double,
        rxmax: Double, rymax: Double
    ): Int

    /**
     * The `Float` class defines a geometric path with
     * coordinates stored in single precision floating point.
     *
     * @since 1.6
     */
    open class Path2DFloat : Path2D, Serializable {
        @Transient
        var floatCoords: FloatArray

        /**
         * Constructs a new empty single precision `Path2D` object
         * with the specified winding rule and the specified initial
         * capacity to store path segments.
         * This number is an initial guess as to how many path segments
         * will be added to the path, but the storage is expanded as
         * needed to store whatever path segments are added.
         *
         * @param rule            the winding rule
         * @param initialCapacity the estimate for the number of path segments
         * in the path
         * @throws IllegalArgumentException   if `rule` is not either
         * [.WIND_EVEN_ODD] or [.WIND_NON_ZERO]
         * @throws NegativeArraySizeException if `initialCapacity` is
         * negative
         * @see .WIND_EVEN_ODD
         *
         * @see .WIND_NON_ZERO
         *
         * @since 1.6
         */
        /**
         * Constructs a new empty single precision `Path2D` object
         * with a default winding rule of [.WIND_NON_ZERO].
         *
         * @since 1.6
         */
        /**
         * Constructs a new empty single precision `Path2D` object
         * with the specified winding rule to control operations that
         * require the interior of the path to be defined.
         *
         * @param rule the winding rule
         * @throws IllegalArgumentException if `rule` is not either
         * [.WIND_EVEN_ODD] or [.WIND_NON_ZERO]
         * @see .WIND_EVEN_ODD
         *
         * @see .WIND_NON_ZERO
         *
         * @since 1.6
         */
        constructor(
            rule: Int = WIND_NON_ZERO,
            initialCapacity: Int = INIT_SIZE
        ) : super(
            rule,
            initialCapacity
        ) {
            floatCoords = FloatArray(initialCapacity * 2)
        }

        /**
         * Constructs a new single precision `Path2D` object
         * from an arbitrary [Shape] object, transformed by an
         * [AffineTransform] object.
         * All of the initial geometry and the winding rule for this path are
         * taken from the specified `Shape` object and transformed
         * by the specified `AffineTransform` object.
         *
         * @param s  the specified `Shape` object
         * @param at the specified `AffineTransform` object
         * @throws NullPointerException if `s` is `null`
         * @since 1.6
         */
        /**
         * Constructs a new single precision `Path2D` object
         * from an arbitrary [Shape] object.
         * All of the initial geometry and the winding rule for this path are
         * taken from the specified `Shape` object.
         *
         * @param s the specified `Shape` object
         * @throws NullPointerException if `s` is `null`
         * @since 1.6
         */
        constructor(s: Shape, at: AffineTransform? = null) {
            if (s is Path2D) {
                windingRule = s.windingRule
                this.numTypes = s.numTypes
                // trim arrays:
                this.pointTypes = s.pointTypes.copyOf(s.numTypes)
                this.numCoords = s.numCoords
                this.floatCoords = s.cloneCoordsFloat(at)
            } else {
                val pi = s.getPathIterator(at)
                windingRule = pi.windingRule
                this.pointTypes = ByteArray(INIT_SIZE)
                this.floatCoords = FloatArray(INIT_SIZE * 2)
                append(pi, false)
            }
        }

        override fun trimToSize() {
            // trim arrays:
            if (numTypes < pointTypes.size) {
                this.pointTypes = pointTypes.copyOf(numTypes)
            }
            if (numCoords < floatCoords.size) {
                this.floatCoords = floatCoords.copyOf(numCoords)
            }
        }

        override val bounds: Rectangle
            get() = TODO("Not yet implemented")
        override val bounds2D: Rectangle2D
            get() {
                var x1: Float
                var y1: Float
                var x2: Float
                var y2: Float
                var i = numCoords
                if (i > 0) {
                    y2 = floatCoords[--i]
                    y1 = y2
                    x2 = floatCoords[--i]
                    x1 = x2
                    while (i > 0) {
                        val y = floatCoords[--i]
                        val x = floatCoords[--i]
                        if (x < x1) x1 = x
                        if (y < y1) y1 = y
                        if (x > x2) x2 = x
                        if (y > y2) y2 = y
                    }
                } else {
                    y2 = 0.0f
                    x2 = y2
                    y1 = x2
                    x1 = y1
                }
                return Rectangle2D.Rectangle2DFloat(
                    x1,
                    y1,
                    x2 - x1,
                    y2 - y1
                )
            }

        override fun cloneCoordsFloat(at: AffineTransform?): FloatArray {
            // trim arrays:
            val ret: FloatArray
            if (at == null) {
                ret = floatCoords.copyOf(numCoords)
            } else {
                ret = FloatArray(numCoords)
                at.transform(floatCoords, 0, ret, 0, numCoords / 2)
            }
            return ret
        }

        override fun cloneCoordsDouble(at: AffineTransform?): DoubleArray {
            // trim arrays:
            val ret = DoubleArray(numCoords)
            if (at == null) {
                for (i in 0 until numCoords) {
                    ret[i] = floatCoords[i].toDouble()
                }
            } else {
                at.transform(floatCoords, 0, ret, 0, numCoords / 2)
            }
            return ret
        }

        override fun append(x: Float, y: Float) {
            floatCoords[numCoords++] = x
            floatCoords[numCoords++] = y
        }

        override fun append(x: Double, y: Double) {
            floatCoords[numCoords++] = x.toFloat()
            floatCoords[numCoords++] = y.toFloat()
        }

        override fun getPoint(coordindex: Int): Point2D {
            return Point2D.Point2DFloat(
                floatCoords[coordindex],
                floatCoords[coordindex + 1]
            )
        }

        override fun needRoom(needMove: Boolean, newCoords: Int) {
            if ((numTypes == 0) && needMove) {
                throw IllegalPathStateException(
                    "missing initial moveto " +
                            "in path definition"
                )
            }
            if (numTypes >= pointTypes.size) {
                pointTypes = expandPointTypes(pointTypes, 1)
            }
            if (numCoords > (floatCoords.size - newCoords)) {
                floatCoords = expandCoords(floatCoords, newCoords)
            }
        }

        /**
         * {@inheritDoc}
         *
         * @since 1.6
         */
        @Synchronized
        override fun moveTo(x: Double, y: Double) {
            if (numTypes > 0 && pointTypes[numTypes - 1] == SEG_MOVETO) {
                floatCoords[numCoords - 2] = x.toFloat()
                floatCoords[numCoords - 1] = y.toFloat()
            } else {
                needRoom(false, 2)
                pointTypes[numTypes++] = SEG_MOVETO
                floatCoords[numCoords++] = x.toFloat()
                floatCoords[numCoords++] = y.toFloat()
            }
        }

        /**
         * Adds a point to the path by moving to the specified
         * coordinates specified in float precision.
         *
         *
         * This method provides a single precision variant of
         * the double precision `moveTo()` method on the
         * base `Path2D` class.
         *
         * @param x the specified X coordinate
         * @param y the specified Y coordinate
         * @see Path2D.moveTo
         *
         * @since 1.6
         */
        @Synchronized
        fun moveTo(x: Float, y: Float) {
            if (numTypes > 0 && pointTypes[numTypes - 1] == SEG_MOVETO) {
                floatCoords[numCoords - 2] = x
                floatCoords[numCoords - 1] = y
            } else {
                needRoom(false, 2)
                pointTypes[numTypes++] = SEG_MOVETO
                floatCoords[numCoords++] = x
                floatCoords[numCoords++] = y
            }
        }

        /**
         * {@inheritDoc}
         *
         * @since 1.6
         */
        @Synchronized
        override fun lineTo(x: Double, y: Double) {
            needRoom(true, 2)
            pointTypes[numTypes++] = SEG_LINETO
            floatCoords[numCoords++] = x.toFloat()
            floatCoords[numCoords++] = y.toFloat()
        }

        /**
         * Adds a point to the path by drawing a straight line from the
         * current coordinates to the new specified coordinates
         * specified in float precision.
         *
         *
         * This method provides a single precision variant of
         * the double precision `lineTo()` method on the
         * base `Path2D` class.
         *
         * @param x the specified X coordinate
         * @param y the specified Y coordinate
         * @see Path2D.lineTo
         *
         * @since 1.6
         */
        @Synchronized
        fun lineTo(x: Float, y: Float) {
            needRoom(true, 2)
            pointTypes[numTypes++] = SEG_LINETO
            floatCoords[numCoords++] = x
            floatCoords[numCoords++] = y
        }

        /**
         * {@inheritDoc}
         *
         * @since 1.6
         */
        @Synchronized
        override fun quadTo(
            x1: Double, y1: Double,
            x2: Double, y2: Double
        ) {
            needRoom(true, 4)
            pointTypes[numTypes++] = SEG_QUADTO
            floatCoords[numCoords++] = x1.toFloat()
            floatCoords[numCoords++] = y1.toFloat()
            floatCoords[numCoords++] = x2.toFloat()
            floatCoords[numCoords++] = y2.toFloat()
        }

        /**
         * Adds a curved segment, defined by two new points, to the path by
         * drawing a Quadratic curve that intersects both the current
         * coordinates and the specified coordinates `(x2,y2)`,
         * using the specified point `(x1,y1)` as a quadratic
         * parametric control point.
         * All coordinates are specified in float precision.
         *
         *
         * This method provides a single precision variant of
         * the double precision `quadTo()` method on the
         * base `Path2D` class.
         *
         * @param x1 the X coordinate of the quadratic control point
         * @param y1 the Y coordinate of the quadratic control point
         * @param x2 the X coordinate of the final end point
         * @param y2 the Y coordinate of the final end point
         * @see Path2D.quadTo
         *
         * @since 1.6
         */
        @Synchronized
        fun quadTo(
            x1: Float, y1: Float,
            x2: Float, y2: Float
        ) {
            needRoom(true, 4)
            pointTypes[numTypes++] = SEG_QUADTO
            floatCoords[numCoords++] = x1
            floatCoords[numCoords++] = y1
            floatCoords[numCoords++] = x2
            floatCoords[numCoords++] = y2
        }

        /**
         * {@inheritDoc}
         *
         * @since 1.6
         */
        @Synchronized
        override fun curveTo(
            x1: Double, y1: Double,
            x2: Double, y2: Double,
            x3: Double, y3: Double
        ) {
            needRoom(true, 6)
            pointTypes[numTypes++] = SEG_CUBICTO
            floatCoords[numCoords++] = x1.toFloat()
            floatCoords[numCoords++] = y1.toFloat()
            floatCoords[numCoords++] = x2.toFloat()
            floatCoords[numCoords++] = y2.toFloat()
            floatCoords[numCoords++] = x3.toFloat()
            floatCoords[numCoords++] = y3.toFloat()
        }

        /**
         * Adds a curved segment, defined by three new points, to the path by
         * drawing a Bzier curve that intersects both the current
         * coordinates and the specified coordinates `(x3,y3)`,
         * using the specified points `(x1,y1)` and `(x2,y2)` as
         * Bzier control points.
         * All coordinates are specified in float precision.
         *
         *
         * This method provides a single precision variant of
         * the double precision `curveTo()` method on the
         * base `Path2D` class.
         *
         * @param x1 the X coordinate of the first Bzier control point
         * @param y1 the Y coordinate of the first Bzier control point
         * @param x2 the X coordinate of the second Bzier control point
         * @param y2 the Y coordinate of the second Bzier control point
         * @param x3 the X coordinate of the final end point
         * @param y3 the Y coordinate of the final end point
         * @see Path2D.curveTo
         *
         * @since 1.6
         */
        @Synchronized
        fun curveTo(
            x1: Float, y1: Float,
            x2: Float, y2: Float,
            x3: Float, y3: Float
        ) {
            needRoom(true, 6)
            pointTypes[numTypes++] = SEG_CUBICTO
            floatCoords[numCoords++] = x1
            floatCoords[numCoords++] = y1
            floatCoords[numCoords++] = x2
            floatCoords[numCoords++] = y2
            floatCoords[numCoords++] = x3
            floatCoords[numCoords++] = y3
        }

        override fun pointCrossings(px: Double, py: Double): Int {
            if (numTypes == 0) {
                return 0
            }
            var movx: Double
            var movy: Double
            var curx: Double
            var cury: Double
            var endx: Double
            var endy: Double
            val coords = floatCoords
            movx = coords[0].toDouble()
            curx = movx
            movy = coords[1].toDouble()
            cury = movy
            var crossings = 0
            var ci = 2
            for (i in 1 until numTypes) {
                when (pointTypes[i]) {
                    SEG_MOVETO -> {
                        if (cury != movy) {
                            crossings +=
                                pointCrossingsForLine(
                                    px, py,
                                    curx, cury,
                                    movx, movy
                                )
                        }
                        run {
                            curx = coords[ci++].toDouble()
                            movx = curx
                        }
                        run {
                            cury = coords[ci++].toDouble()
                            movy = cury
                        }
                    }

                    SEG_LINETO -> {
                        crossings +=
                            pointCrossingsForLine(
                                px, py,
                                curx, cury,
                                coords[ci++].also { endx = it.toDouble() }.toDouble(),
                                coords[ci++].also { endy = it.toDouble() }.toDouble()
                            )
                        curx = endx
                        cury = endy
                    }

                    SEG_QUADTO -> {
                        crossings +=
                            pointCrossingsForQuad(
                                px, py,
                                curx, cury,
                                coords[ci++].toDouble(),
                                coords[ci++].toDouble(),
                                coords[ci++].also { endx = it.toDouble() }.toDouble(),
                                coords[ci++].also { endy = it.toDouble() }.toDouble(),
                                0
                            )
                        curx = endx
                        cury = endy
                    }

                    SEG_CUBICTO -> {
                        crossings +=
                            pointCrossingsForCubic(
                                px, py,
                                curx, cury,
                                coords[ci++].toDouble(),
                                coords[ci++].toDouble(),
                                coords[ci++].toDouble(),
                                coords[ci++].toDouble(),
                                coords[ci++].also { endx = it.toDouble() }.toDouble(),
                                coords[ci++].also { endy = it.toDouble() }.toDouble(),
                                0
                            )
                        curx = endx
                        cury = endy
                    }

                    SEG_CLOSE -> {
                        if (cury != movy) {
                            crossings +=
                                pointCrossingsForLine(
                                    px, py,
                                    curx, cury,
                                    movx, movy
                                )
                        }
                        curx = movx
                        cury = movy
                    }
                }
            }
            if (cury != movy) {
                crossings +=
                    pointCrossingsForLine(
                        px, py,
                        curx, cury,
                        movx, movy
                    )
            }
            return crossings
        }

        override fun rectCrossings(
            rxmin: Double, rymin: Double,
            rxmax: Double, rymax: Double
        ): Int {
            if (numTypes == 0) {
                return 0
            }
            val coords = floatCoords
            var curx: Double
            var cury: Double
            var movx: Double
            var movy: Double
            var endx: Double
            var endy: Double
            movx = coords[0].toDouble()
            curx = movx
            movy = coords[1].toDouble()
            cury = movy
            var crossings = 0
            var ci = 2
            var i = 1
            while (crossings != Curve.RECT_INTERSECTS && i < numTypes
            ) {
                when (pointTypes[i]) {
                    SEG_MOVETO -> {
                        if (curx != movx || cury != movy) {
                            crossings =
                                rectCrossingsForLine(
                                    crossings,
                                    rxmin, rymin,
                                    rxmax, rymax,
                                    curx, cury,
                                    movx, movy
                                )
                        }
                        run {
                            curx = coords[ci++].toDouble()
                            movx = curx
                        }
                        run {
                            cury = coords[ci++].toDouble()
                            movy = cury
                        }
                    }

                    SEG_LINETO -> {
                        crossings =
                            rectCrossingsForLine(
                                crossings,
                                rxmin, rymin,
                                rxmax, rymax,
                                curx, cury,
                                coords[ci++].also { endx = it.toDouble() }.toDouble(),
                                coords[ci++].also { endy = it.toDouble() }.toDouble()
                            )
                        curx = endx
                        cury = endy
                    }

                    SEG_QUADTO -> {
                        crossings =
                            rectCrossingsForQuad(
                                crossings,
                                rxmin, rymin,
                                rxmax, rymax,
                                curx, cury,
                                coords[ci++].toDouble(),
                                coords[ci++].toDouble(),
                                coords[ci++].also { endx = it.toDouble() }.toDouble(),
                                coords[ci++].also { endy = it.toDouble() }.toDouble(),
                                0
                            )
                        curx = endx
                        cury = endy
                    }

                    SEG_CUBICTO -> {
                        crossings =
                            rectCrossingsForCubic(
                                crossings,
                                rxmin, rymin,
                                rxmax, rymax,
                                curx, cury,
                                coords[ci++].toDouble(),
                                coords[ci++].toDouble(),
                                coords[ci++].toDouble(),
                                coords[ci++].toDouble(),
                                coords[ci++].also { endx = it.toDouble() }.toDouble(),
                                coords[ci++].also { endy = it.toDouble() }.toDouble(),
                                0
                            )
                        curx = endx
                        cury = endy
                    }

                    SEG_CLOSE -> {
                        if (curx != movx || cury != movy) {
                            crossings =
                                rectCrossingsForLine(
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
                i++
            }
            if (crossings != Curve.RECT_INTERSECTS &&
                (curx != movx || cury != movy)
            ) {
                crossings =
                    rectCrossingsForLine(
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

        /**
         * {@inheritDoc}
         *
         * @since 1.6
         */
        override fun append(pi: PathIterator, connect: Boolean) {
            var connect = connect
            val coords = FloatArray(6)
            while (!pi.isDone) {
                when (pi.currentSegment(coords)) {
                    SEG_MOVETO.toInt() -> {
                        if (!connect || numTypes < 1 || numCoords < 1) {
                            moveTo(coords[0], coords[1])
                            break
                        }
                        if (pointTypes[numTypes - 1] != SEG_CLOSE && floatCoords[numCoords - 2] == coords[0] && floatCoords[numCoords - 1] == coords[1]) {
                            // Collapse out initial moveto/lineto
                            break
                        }
                        lineTo(coords[0], coords[1])
                    }

                    SEG_LINETO.toInt() -> lineTo(
                        coords[0], coords[1]
                    )

                    SEG_QUADTO.toInt() -> quadTo(
                        coords[0], coords[1],
                        coords[2], coords[3]
                    )

                    SEG_CUBICTO.toInt() -> curveTo(
                        coords[0], coords[1],
                        coords[2], coords[3],
                        coords[4], coords[5]
                    )

                    SEG_CLOSE.toInt() -> closePath()
                }
                pi.next()
                connect = false
            }
        }

        /**
         * {@inheritDoc}
         *
         * @since 1.6
         */
        override fun transform(at: AffineTransform) {
            at.transform(floatCoords, 0, floatCoords, 0, numCoords / 2)
        }

        /**
         * {@inheritDoc}
         *
         *
         * The iterator for this class is not multi-threaded safe,
         * which means that the `Path2D` class does not
         * guarantee that modifications to the geometry of this
         * `Path2D` object do not affect any iterations of
         * that geometry that are already in process.
         *
         * @since 1.6
         */
        override fun getPathIterator(at: AffineTransform?): PathIterator {
            return if (at == null) {
                CopyIterator(
                    this
                )
            } else {
                TxIterator(
                    this,
                    at
                )
            }
        }

        /**
         * Creates a new object of the same class as this object.
         *
         * @return a clone of this instance.
         * @throws OutOfMemoryError if there is not enough memory.
         * @see java.lang.Cloneable
         *
         * @since 1.6
         */
        public override fun clone(): Any {
            // Note: It would be nice to have this return Path2D
            // but one of our subclasses (GeneralPath) needs to
            // offer "public Object clone()" for backwards
            // compatibility so we cannot restrict it further.
            // REMIND: Can we do both somehow?
            return if (this is GeneralPath) {
                GeneralPath(
                    this
                )
            } else {
                Path2DFloat(
                    this
                )
            }
        }

        /**
         * Writes the default serializable fields to the
         * `ObjectOutputStream` followed by an explicit
         * serialization of the path segments stored in this
         * path.
         *
         * @param s the `ObjectOutputStream` to write
         * @throws IOException if an I/O error occurs
         * @serialData
         *  1. The default serializable fields.
         * There are no default serializable fields as of 1.6.
         *  1. followed by
         * a byte indicating the storage type of the original object
         * as a hint (SERIAL_STORAGE_FLT_ARRAY)
         *  1. followed by
         * an integer indicating the number of path segments to follow (NP)
         * or -1 to indicate an unknown number of path segments follows
         *  1. followed by
         * an integer indicating the total number of coordinates to follow (NC)
         * or -1 to indicate an unknown number of coordinates follows
         * (NC should always be even since coordinates always appear in pairs
         * representing an x,y pair)
         *  1. followed by
         * a byte indicating the winding rule
         * ([WIND_EVEN_ODD][.WIND_EVEN_ODD] or
         * [WIND_NON_ZERO][.WIND_NON_ZERO])
         *  1. followed by
         * `NP` (or unlimited if `NP < 0`) sets of values consisting of
         * a single byte indicating a path segment type
         * followed by one or more pairs of float or double
         * values representing the coordinates of the path segment
         *  1. followed by
         * a byte indicating the end of the path (SERIAL_PATH_END).
         *
         *
         *
         * The following byte value constants are used in the serialized form
         * of `Path2D` objects:
         *
         * <table class="striped">
         * <caption>Constants</caption>
         * <thead>
         * <tr>
         * <th scope="col">Constant Name</th>
         * <th scope="col">Byte Value</th>
         * <th scope="col">Followed by</th>
         * <th scope="col">Description</th>
        </tr> *
        </thead> *
         * <tbody>
         * <tr>
         * <th scope="row">`SERIAL_STORAGE_FLT_ARRAY`</th>
         * <td>0x30</td>
         * <td></td>
         * <td>A hint that the original `Path2D` object stored
         * the coordinates in a Java array of floats.</td>
        </tr> *
         * <tr>
         * <th scope="row">`SERIAL_STORAGE_DBL_ARRAY`</th>
         * <td>0x31</td>
         * <td></td>
         * <td>A hint that the original `Path2D` object stored
         * the coordinates in a Java array of doubles.</td>
        </tr> *
         * <tr>
         * <th scope="row">`SERIAL_SEG_FLT_MOVETO`</th>
         * <td>0x40</td>
         * <td>2 floats</td>
         * <td>A [moveTo][.moveTo] path segment follows.</td>
        </tr> *
         * <tr>
         * <th scope="row">`SERIAL_SEG_FLT_LINETO`</th>
         * <td>0x41</td>
         * <td>2 floats</td>
         * <td>A [lineTo][.lineTo] path segment follows.</td>
        </tr> *
         * <tr>
         * <th scope="row">`SERIAL_SEG_FLT_QUADTO`</th>
         * <td>0x42</td>
         * <td>4 floats</td>
         * <td>A [quadTo][.quadTo] path segment follows.</td>
        </tr> *
         * <tr>
         * <th scope="row">`SERIAL_SEG_FLT_CUBICTO`</th>
         * <td>0x43</td>
         * <td>6 floats</td>
         * <td>A [curveTo][.curveTo] path segment follows.</td>
        </tr> *
         * <tr>
         * <th scope="row">`SERIAL_SEG_DBL_MOVETO`</th>
         * <td>0x50</td>
         * <td>2 doubles</td>
         * <td>A [moveTo][.moveTo] path segment follows.</td>
        </tr> *
         * <tr>
         * <th scope="row">`SERIAL_SEG_DBL_LINETO`</th>
         * <td>0x51</td>
         * <td>2 doubles</td>
         * <td>A [lineTo][.lineTo] path segment follows.</td>
        </tr> *
         * <tr>
         * <th scope="row">`SERIAL_SEG_DBL_QUADTO`</th>
         * <td>0x52</td>
         * <td>4 doubles</td>
         * <td>A [curveTo][.curveTo] path segment follows.</td>
        </tr> *
         * <tr>
         * <th scope="row">`SERIAL_SEG_DBL_CUBICTO`</th>
         * <td>0x53</td>
         * <td>6 doubles</td>
         * <td>A [curveTo][.curveTo] path segment follows.</td>
        </tr> *
         * <tr>
         * <th scope="row">`SERIAL_SEG_CLOSE`</th>
         * <td>0x60</td>
         * <td></td>
         * <td>A [closePath][.closePath] path segment.</td>
        </tr> *
         * <tr>
         * <th scope="row">`SERIAL_PATH_END`</th>
         * <td>0x61</td>
         * <td></td>
         * <td>There are no more path segments following.</td>
        </tr></tbody> *
        </table> *
         * @since 1.6
         */
        @Throws(IOException::class)
        private fun writeObject(s: ObjectOutputStream) {
            super.writeObject(s, false)
        }

        /**
         * Reads the default serializable fields from the
         * `ObjectInputStream` followed by an explicit
         * serialization of the path segments stored in this
         * path.
         *
         *
         * There are no default serializable fields as of 1.6.
         *
         *
         * The serial data for this object is described in the
         * writeObject method.
         *
         * @param s the `ObjectInputStream` to read
         * @throws ClassNotFoundException if the class of a serialized object
         * could not be found
         * @throws IOException            if an I/O error occurs
         * @since 1.6
         */
        @Throws(ClassNotFoundException::class, IOException::class)
        private fun readObject(s: ObjectInputStream) {
            super.readObject(s, false)
        }

        internal class CopyIterator(
            p2df: Path2DFloat,
            override val windingRule: Int = 0,
            override var isDone: Boolean = false
        ) : Iterator(p2df) {
            var floatCoords: FloatArray = p2df.floatCoords

            override fun currentSegment(coords: FloatArray): Int {
                val type = path.pointTypes[typeIdx].toInt()
                val numCoords = curvecoords[type]
                if (numCoords > 0) {
                    System.arraycopy(
                        floatCoords, pointIdx,
                        coords, 0, numCoords
                    )
                }
                return type
            }

            override fun currentSegment(coords: DoubleArray): Int {
                val type = path.pointTypes[typeIdx].toInt()
                val numCoords = curvecoords[type]
                if (numCoords > 0) {
                    for (i in 0 until numCoords) {
                        coords[i] = floatCoords[pointIdx + i].toDouble()
                    }
                }
                return type
            }
        }

        internal class TxIterator(
            p2df: Path2DFloat,
            var affine: AffineTransform,
            override val windingRule: Int = 0,
            override var isDone: Boolean = false
        ) : Iterator(p2df) {
            var floatCoords: FloatArray = p2df.floatCoords

            override fun currentSegment(coords: FloatArray): Int {
                val type = path.pointTypes[typeIdx].toInt()
                val numCoords = curvecoords[type]
                if (numCoords > 0) {
                    affine.transform(
                        floatCoords, pointIdx,
                        coords, 0, numCoords / 2
                    )
                }
                return type
            }

            override fun currentSegment(coords: DoubleArray): Int {
                val type = path.pointTypes[typeIdx].toInt()
                val numCoords = curvecoords[type]
                if (numCoords > 0) {
                    affine.transform(
                        floatCoords, pointIdx,
                        coords, 0, numCoords / 2
                    )
                }
                return type
            }
        }

        companion object {
            fun expandCoords(oldCoords: FloatArray, needed: Int): FloatArray {
                val oldSize = oldCoords.size
                val newSizeMin = oldSize + needed
                if (newSizeMin < oldSize) {
                    // hard overflow failure - we can't even accommodate
                    // new items without overflowing
                    throw ArrayIndexOutOfBoundsException(
                        "coords exceeds maximum capacity !"
                    )
                }
                // growth algorithm computation
                var grow = oldSize
                if (grow > EXPAND_MAX_COORDS) {
                    grow = max(EXPAND_MAX_COORDS.toDouble(), (oldSize shr 3).toDouble())
                        .toInt() // 1/8th min
                } else if (grow < EXPAND_MIN) {
                    grow = EXPAND_MIN
                }
                assert(grow > needed)
                var newSize = oldSize + grow
                if (newSize < newSizeMin) {
                    // overflow in growth algorithm computation
                    newSize = Int.MAX_VALUE
                }
                while (true) {
                    try {
                        // try allocating the larger array
                        return oldCoords.copyOf(newSize)
                    } catch (oome: OutOfMemoryError) {
                        if (newSize == newSizeMin) {
                            throw oome
                        }
                    }
                    newSize = newSizeMin + (newSize - newSizeMin) / 2
                }
            }

            /**
             * Use serialVersionUID from JDK 1.6 for interoperability.
             */
            private const val serialVersionUID = 6990832515060788886L
        }
    }

    /**
     * The `Double` class defines a geometric path with
     * coordinates stored in double precision floating point.
     *
     * @since 1.6
     */
    class Path2DDouble : Path2D, Serializable {
        @Transient
        var doubleCoords: DoubleArray

        /**
         * Constructs a new empty double precision `Path2D` object
         * with the specified winding rule and the specified initial
         * capacity to store path segments.
         * This number is an initial guess as to how many path segments
         * are in the path, but the storage is expanded as needed to store
         * whatever path segments are added to this path.
         *
         * @param rule            the winding rule
         * @param initialCapacity the estimate for the number of path segments
         * in the path
         * @throws IllegalArgumentException   if `rule` is not either
         * [.WIND_EVEN_ODD] or [.WIND_NON_ZERO]
         * @throws NegativeArraySizeException if `initialCapacity` is
         * negative
         * @see .WIND_EVEN_ODD
         *
         * @see .WIND_NON_ZERO
         *
         * @since 1.6
         */
        /**
         * Constructs a new empty double precision `Path2D` object
         * with a default winding rule of [.WIND_NON_ZERO].
         *
         * @since 1.6
         */
        /**
         * Constructs a new empty double precision `Path2D` object
         * with the specified winding rule to control operations that
         * require the interior of the path to be defined.
         *
         * @param rule the winding rule
         * @throws IllegalArgumentException if `rule` is not either
         * [.WIND_EVEN_ODD] or [.WIND_NON_ZERO]
         * @see .WIND_EVEN_ODD
         *
         * @see .WIND_NON_ZERO
         *
         * @since 1.6
         */
        @JvmOverloads
        constructor(rule: Int = WIND_NON_ZERO, initialCapacity: Int = INIT_SIZE) : super(
            rule,
            initialCapacity
        ) {
            doubleCoords = DoubleArray(initialCapacity * 2)
        }

        /**
         * Constructs a new double precision `Path2D` object
         * from an arbitrary [Shape] object, transformed by an
         * [AffineTransform] object.
         * All of the initial geometry and the winding rule for this path are
         * taken from the specified `Shape` object and transformed
         * by the specified `AffineTransform` object.
         *
         * @param s  the specified `Shape` object
         * @param at the specified `AffineTransform` object
         * @throws NullPointerException if `s` is `null`
         * @since 1.6
         */
        /**
         * Constructs a new double precision `Path2D` object
         * from an arbitrary [Shape] object.
         * All of the initial geometry and the winding rule for this path are
         * taken from the specified `Shape` object.
         *
         * @param s the specified `Shape` object
         * @throws NullPointerException if `s` is `null`
         * @since 1.6
         */
        constructor(s: Shape, at: AffineTransform? = null) {
            if (s is Path2D) {
                windingRule = s.windingRule
                this.numTypes = s.numTypes
                // trim arrays:
                this.pointTypes = s.pointTypes.copyOf(s.numTypes)
                this.numCoords = s.numCoords
                this.doubleCoords = s.cloneCoordsDouble(at)
            } else {
                val pi = s.getPathIterator(at)
                windingRule = pi.windingRule
                this.pointTypes = ByteArray(INIT_SIZE)
                this.doubleCoords = DoubleArray(INIT_SIZE * 2)
                append(pi, false)
            }
        }

        override fun trimToSize() {
            // trim arrays:
            if (numTypes < pointTypes.size) {
                this.pointTypes = pointTypes.copyOf(numTypes)
            }
            if (numCoords < doubleCoords.size) {
                this.doubleCoords = doubleCoords.copyOf(numCoords)
            }
        }

        override fun cloneCoordsFloat(at: AffineTransform?): FloatArray {
            // trim arrays:
            val ret = FloatArray(numCoords)
            if (at == null) {
                for (i in 0 until numCoords) {
                    ret[i] = doubleCoords[i].toFloat()
                }
            } else {
                at.transform(doubleCoords, 0, ret, 0, numCoords / 2)
            }
            return ret
        }

        override fun cloneCoordsDouble(at: AffineTransform?): DoubleArray {
            // trim arrays:
            val ret: DoubleArray
            if (at == null) {
                ret = doubleCoords.copyOf(numCoords)
            } else {
                ret = DoubleArray(numCoords)
                at.transform(doubleCoords, 0, ret, 0, numCoords / 2)
            }
            return ret
        }

        override fun append(x: Float, y: Float) {
            doubleCoords[numCoords++] = x.toDouble()
            doubleCoords[numCoords++] = y.toDouble()
        }

        override fun append(x: Double, y: Double) {
            doubleCoords[numCoords++] = x
            doubleCoords[numCoords++] = y
        }

        override fun getPoint(coordindex: Int): Point2D {
            return Point2D.Point2DDouble(
                doubleCoords[coordindex],
                doubleCoords[coordindex + 1]
            )
        }

        override fun needRoom(needMove: Boolean, newCoords: Int) {
            if ((numTypes == 0) && needMove) {
                throw IllegalPathStateException(
                    "missing initial moveto " +
                            "in path definition"
                )
            }
            if (numTypes >= pointTypes.size) {
                pointTypes = expandPointTypes(pointTypes, 1)
            }
            if (numCoords > (doubleCoords.size - newCoords)) {
                doubleCoords = expandCoords(doubleCoords, newCoords)
            }
        }

        /**
         * {@inheritDoc}
         *
         * @since 1.6
         */
        @Synchronized
        override fun moveTo(x: Double, y: Double) {
            if (numTypes > 0 && pointTypes[numTypes - 1] == SEG_MOVETO) {
                doubleCoords[numCoords - 2] = x
                doubleCoords[numCoords - 1] = y
            } else {
                needRoom(false, 2)
                pointTypes[numTypes++] = SEG_MOVETO
                doubleCoords[numCoords++] = x
                doubleCoords[numCoords++] = y
            }
        }

        /**
         * {@inheritDoc}
         *
         * @since 1.6
         */
        @Synchronized
        override fun lineTo(x: Double, y: Double) {
            needRoom(true, 2)
            pointTypes[numTypes++] = SEG_LINETO
            doubleCoords[numCoords++] = x
            doubleCoords[numCoords++] = y
        }

        /**
         * {@inheritDoc}
         *
         * @since 1.6
         */
        @Synchronized
        override fun quadTo(
            x1: Double, y1: Double,
            x2: Double, y2: Double
        ) {
            needRoom(true, 4)
            pointTypes[numTypes++] = SEG_QUADTO
            doubleCoords[numCoords++] = x1
            doubleCoords[numCoords++] = y1
            doubleCoords[numCoords++] = x2
            doubleCoords[numCoords++] = y2
        }

        /**
         * {@inheritDoc}
         *
         * @since 1.6
         */
        @Synchronized
        override fun curveTo(
            x1: Double, y1: Double,
            x2: Double, y2: Double,
            x3: Double, y3: Double
        ) {
            needRoom(true, 6)
            pointTypes[numTypes++] = SEG_CUBICTO
            doubleCoords[numCoords++] = x1
            doubleCoords[numCoords++] = y1
            doubleCoords[numCoords++] = x2
            doubleCoords[numCoords++] = y2
            doubleCoords[numCoords++] = x3
            doubleCoords[numCoords++] = y3
        }

        override fun pointCrossings(px: Double, py: Double): Int {
            if (numTypes == 0) {
                return 0
            }
            var movx: Double
            var movy: Double
            var curx: Double
            var cury: Double
            var endx: Double
            var endy: Double
            val coords = doubleCoords
            movx = coords[0]
            curx = movx
            movy = coords[1]
            cury = movy
            var crossings = 0
            var ci = 2
            for (i in 1 until numTypes) {
                when (pointTypes[i]) {
                    SEG_MOVETO -> {
                        if (cury != movy) {
                            crossings +=
                                pointCrossingsForLine(
                                    px, py,
                                    curx, cury,
                                    movx, movy
                                )
                        }
                        run {
                            curx = coords[ci++]
                            movx = curx
                        }
                        run {
                            cury = coords[ci++]
                            movy = cury
                        }
                    }

                    SEG_LINETO -> {
                        crossings +=
                            pointCrossingsForLine(px, py,
                                curx, cury,
                                coords[ci++].also { endx = it },
                                coords[ci++].also { endy = it })
                        curx = endx
                        cury = endy
                    }

                    SEG_QUADTO -> {
                        crossings +=
                            pointCrossingsForQuad(
                                px, py,
                                curx, cury,
                                coords[ci++],
                                coords[ci++],
                                coords[ci++].also { endx = it },
                                coords[ci++].also { endy = it },
                                0
                            )
                        curx = endx
                        cury = endy
                    }

                    SEG_CUBICTO -> {
                        crossings +=
                            pointCrossingsForCubic(
                                px, py,
                                curx, cury,
                                coords[ci++],
                                coords[ci++],
                                coords[ci++],
                                coords[ci++],
                                coords[ci++].also { endx = it },
                                coords[ci++].also { endy = it },
                                0
                            )
                        curx = endx
                        cury = endy
                    }

                    SEG_CLOSE -> {
                        if (cury != movy) {
                            crossings +=
                                pointCrossingsForLine(
                                    px, py,
                                    curx, cury,
                                    movx, movy
                                )
                        }
                        curx = movx
                        cury = movy
                    }
                }
            }
            if (cury != movy) {
                crossings +=
                    pointCrossingsForLine(
                        px, py,
                        curx, cury,
                        movx, movy
                    )
            }
            return crossings
        }

        override fun rectCrossings(
            rxmin: Double, rymin: Double,
            rxmax: Double, rymax: Double
        ): Int {
            if (numTypes == 0) {
                return 0
            }
            val coords = doubleCoords
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
            var ci = 2
            var i = 1
            while (crossings != Curve.RECT_INTERSECTS && i < numTypes
            ) {
                when (pointTypes[i]) {
                    SEG_MOVETO -> {
                        if (curx != movx || cury != movy) {
                            crossings =
                                rectCrossingsForLine(
                                    crossings,
                                    rxmin, rymin,
                                    rxmax, rymax,
                                    curx, cury,
                                    movx, movy
                                )
                        }
                        run {
                            curx = coords[ci++]
                            movx = curx
                        }
                        run {
                            cury = coords[ci++]
                            movy = cury
                        }
                    }

                    SEG_LINETO -> {
                        endx = coords[ci++]
                        endy = coords[ci++]
                        crossings =
                            rectCrossingsForLine(
                                crossings,
                                rxmin, rymin,
                                rxmax, rymax,
                                curx, cury,
                                endx, endy
                            )
                        curx = endx
                        cury = endy
                    }

                    SEG_QUADTO -> {
                        crossings =
                            rectCrossingsForQuad(
                                crossings,
                                rxmin, rymin,
                                rxmax, rymax,
                                curx, cury,
                                coords[ci++],
                                coords[ci++],
                                coords[ci++].also { endx = it },
                                coords[ci++].also { endy = it },
                                0
                            )
                        curx = endx
                        cury = endy
                    }

                    SEG_CUBICTO -> {
                        crossings =
                            rectCrossingsForCubic(
                                crossings,
                                rxmin, rymin,
                                rxmax, rymax,
                                curx, cury,
                                coords[ci++],
                                coords[ci++],
                                coords[ci++],
                                coords[ci++],
                                coords[ci++].also { endx = it },
                                coords[ci++].also { endy = it },
                                0
                            )
                        curx = endx
                        cury = endy
                    }

                    SEG_CLOSE -> {
                        if (curx != movx || cury != movy) {
                            crossings =
                                rectCrossingsForLine(
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
                i++
            }
            if (crossings != Curve.RECT_INTERSECTS &&
                (curx != movx || cury != movy)
            ) {
                crossings =
                    rectCrossingsForLine(
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

        /**
         * {@inheritDoc}
         *
         * @since 1.6
         */
        override fun append(pi: PathIterator, connect: Boolean) {
            var connect = connect
            val coords = DoubleArray(6)
            while (!pi.isDone) {
                when (pi.currentSegment(coords)) {
                    SEG_MOVETO.toInt() -> {
                        if (!connect || numTypes < 1 || numCoords < 1) {
                            moveTo(coords[0], coords[1])
                            break
                        }
                        if (pointTypes[numTypes - 1] != SEG_CLOSE && doubleCoords[numCoords - 2] == coords[0] && doubleCoords[numCoords - 1] == coords[1]) {
                            // Collapse out initial moveto/lineto
                            break
                        }
                        lineTo(coords[0], coords[1])
                    }

                    SEG_LINETO.toInt() -> lineTo(
                        coords[0], coords[1]
                    )

                    SEG_QUADTO.toInt() -> quadTo(
                        coords[0], coords[1],
                        coords[2], coords[3]
                    )

                    SEG_CUBICTO.toInt() -> curveTo(
                        coords[0], coords[1],
                        coords[2], coords[3],
                        coords[4], coords[5]
                    )

                    SEG_CLOSE.toInt() -> closePath()
                }
                pi.next()
                connect = false
            }
        }

        /**
         * {@inheritDoc}
         *
         * @since 1.6
         */
        override fun transform(at: AffineTransform) {
            at.transform(doubleCoords, 0, doubleCoords, 0, numCoords / 2)
        }

        override val bounds2D: Rectangle2D
        @Synchronized get() {
                var x1: Double
                var y1: Double
                var x2: Double
                var y2: Double
                var i = numCoords
                if (i > 0) {
                    y2 = doubleCoords[--i]
                    y1 = y2
                    x2 = doubleCoords[--i]
                    x1 = x2
                    while (i > 0) {
                        val y = doubleCoords[--i]
                        val x = doubleCoords[--i]
                        if (x < x1) x1 = x
                        if (y < y1) y1 = y
                        if (x > x2) x2 = x
                        if (y > y2) y2 = y
                    }
                } else {
                    y2 = 0.0
                    x2 = y2
                    y1 = x2
                    x1 = y1
                }
                return Rectangle2D.Rectangle2DDouble(
                    x1,
                    y1,
                    x2 - x1,
                    y2 - y1
                )
            }

        /**
         * {@inheritDoc}
         *
         *
         * The iterator for this class is not multi-threaded safe,
         * which means that the `Path2D` class does not
         * guarantee that modifications to the geometry of this
         * `Path2D` object do not affect any iterations of
         * that geometry that are already in process.
         *
         * @param at an `AffineTransform`
         * @return a new `PathIterator` that iterates along the boundary
         * of this `Shape` and provides access to the geometry
         * of this `Shape`'s outline
         * @since 1.6
         */
        override fun getPathIterator(at: AffineTransform?): PathIterator {
            return if (at == null) {
                CopyIterator(
                    this
                )
            } else {
                TxIterator(
                    this,
                    at
                )
            }
        }

        /**
         * Creates a new object of the same class as this object.
         *
         * @return a clone of this instance.
         * @throws OutOfMemoryError if there is not enough memory.
         * @see java.lang.Cloneable
         *
         * @since 1.6
         */
        public override fun clone(): Any {
            // Note: It would be nice to have this return Path2D
            // but one of our subclasses (GeneralPath) needs to
            // offer "public Object clone()" for backwards
            // compatibility so we cannot restrict it further.
            // REMIND: Can we do both somehow?
            return Path2DDouble(
                this
            )
        }

        /**
         * Writes the default serializable fields to the
         * `ObjectOutputStream` followed by an explicit
         * serialization of the path segments stored in this
         * path.
         *
         * @param s the `ObjectOutputStream` to write
         * @throws IOException if an I/O error occurs
         * @serialData
         *  1. The default serializable fields.
         * There are no default serializable fields as of 1.6.
         *  1. followed by
         * a byte indicating the storage type of the original object
         * as a hint (SERIAL_STORAGE_DBL_ARRAY)
         *  1. followed by
         * an integer indicating the number of path segments to follow (NP)
         * or -1 to indicate an unknown number of path segments follows
         *  1. followed by
         * an integer indicating the total number of coordinates to follow (NC)
         * or -1 to indicate an unknown number of coordinates follows
         * (NC should always be even since coordinates always appear in pairs
         * representing an x,y pair)
         *  1. followed by
         * a byte indicating the winding rule
         * ([WIND_EVEN_ODD][.WIND_EVEN_ODD] or
         * [WIND_NON_ZERO][.WIND_NON_ZERO])
         *  1. followed by
         * `NP` (or unlimited if `NP < 0`) sets of values consisting of
         * a single byte indicating a path segment type
         * followed by one or more pairs of float or double
         * values representing the coordinates of the path segment
         *  1. followed by
         * a byte indicating the end of the path (SERIAL_PATH_END).
         *
         *
         *
         * The following byte value constants are used in the serialized form
         * of `Path2D` objects:
         * <table class="striped">
         * <caption>Constants</caption>
         * <thead>
         * <tr>
         * <th scope="col">Constant Name</th>
         * <th scope="col">Byte Value</th>
         * <th scope="col">Followed by</th>
         * <th scope="col">Description</th>
        </tr> *
        </thead> *
         * <tbody>
         * <tr>
         * <th scope="row">`SERIAL_STORAGE_FLT_ARRAY`</th>
         * <td>0x30</td>
         * <td></td>
         * <td>A hint that the original `Path2D` object stored
         * the coordinates in a Java array of floats.</td>
        </tr> *
         * <tr>
         * <th scope="row">`SERIAL_STORAGE_DBL_ARRAY`</th>
         * <td>0x31</td>
         * <td></td>
         * <td>A hint that the original `Path2D` object stored
         * the coordinates in a Java array of doubles.</td>
        </tr> *
         * <tr>
         * <th scope="row">`SERIAL_SEG_FLT_MOVETO`</th>
         * <td>0x40</td>
         * <td>2 floats</td>
         * <td>A [moveTo][.moveTo] path segment follows.</td>
        </tr> *
         * <tr>
         * <th scope="row">`SERIAL_SEG_FLT_LINETO`</th>
         * <td>0x41</td>
         * <td>2 floats</td>
         * <td>A [lineTo][.lineTo] path segment follows.</td>
        </tr> *
         * <tr>
         * <th scope="row">`SERIAL_SEG_FLT_QUADTO`</th>
         * <td>0x42</td>
         * <td>4 floats</td>
         * <td>A [quadTo][.quadTo] path segment follows.</td>
        </tr> *
         * <tr>
         * <th scope="row">`SERIAL_SEG_FLT_CUBICTO`</th>
         * <td>0x43</td>
         * <td>6 floats</td>
         * <td>A [curveTo][.curveTo] path segment follows.</td>
        </tr> *
         * <tr>
         * <th scope="row">`SERIAL_SEG_DBL_MOVETO`</th>
         * <td>0x50</td>
         * <td>2 doubles</td>
         * <td>A [moveTo][.moveTo] path segment follows.</td>
        </tr> *
         * <tr>
         * <th scope="row">`SERIAL_SEG_DBL_LINETO`</th>
         * <td>0x51</td>
         * <td>2 doubles</td>
         * <td>A [lineTo][.lineTo] path segment follows.</td>
        </tr> *
         * <tr>
         * <th scope="row">`SERIAL_SEG_DBL_QUADTO`</th>
         * <td>0x52</td>
         * <td>4 doubles</td>
         * <td>A [curveTo][.curveTo] path segment follows.</td>
        </tr> *
         * <tr>
         * <th scope="row">`SERIAL_SEG_DBL_CUBICTO`</th>
         * <td>0x53</td>
         * <td>6 doubles</td>
         * <td>A [curveTo][.curveTo] path segment follows.</td>
        </tr> *
         * <tr>
         * <th scope="row">`SERIAL_SEG_CLOSE`</th>
         * <td>0x60</td>
         * <td></td>
         * <td>A [closePath][.closePath] path segment.</td>
        </tr> *
         * <tr>
         * <th scope="row">`SERIAL_PATH_END`</th>
         * <td>0x61</td>
         * <td></td>
         * <td>There are no more path segments following.</td>
        </tr></tbody> *
        </table> *
         * @since 1.6
         */
        @Throws(IOException::class)
        private fun writeObject(s: ObjectOutputStream) {
            super.writeObject(s, true)
        }

        /**
         * Reads the default serializable fields from the
         * `ObjectInputStream` followed by an explicit
         * serialization of the path segments stored in this
         * path.
         *
         *
         * There are no default serializable fields as of 1.6.
         *
         *
         * The serial data for this object is described in the
         * writeObject method.
         *
         * @param s the `ObjectInputStream` to read
         * @throws ClassNotFoundException if the class of a serialized object
         * could not be found
         * @throws IOException            if an I/O error occurs         *
         * @since 1.6
         */
        @Throws(ClassNotFoundException::class, IOException::class)
        private fun readObject(s: ObjectInputStream) {
            super.readObject(s, true)
        }

        internal class CopyIterator(
            p2dd: Path2DDouble,
            override val windingRule: Int = 0,
            override var isDone: Boolean = false
        ) : Iterator(p2dd) {
            var doubleCoords: DoubleArray = p2dd.doubleCoords

            override fun currentSegment(coords: FloatArray): Int {
                val type = path.pointTypes[typeIdx].toInt()
                val numCoords = curvecoords[type]
                if (numCoords > 0) {
                    for (i in 0 until numCoords) {
                        coords[i] = doubleCoords[pointIdx + i].toFloat()
                    }
                }
                return type
            }

            override fun currentSegment(coords: DoubleArray): Int {
                val type = path.pointTypes[typeIdx].toInt()
                val numCoords = curvecoords[type]
                if (numCoords > 0) {
                    System.arraycopy(
                        doubleCoords, pointIdx,
                        coords, 0, numCoords
                    )
                }
                return type
            }
        }

        internal class TxIterator(
            p2dd: Path2DDouble,
            var affine: AffineTransform,
            override val windingRule: Int = 0,
            override var isDone: Boolean = false
        ) : Iterator(p2dd) {
            var doubleCoords: DoubleArray = p2dd.doubleCoords

            override fun currentSegment(coords: FloatArray): Int {
                val type = path.pointTypes[typeIdx].toInt()
                val numCoords = curvecoords[type]
                if (numCoords > 0) {
                    affine.transform(
                        doubleCoords, pointIdx,
                        coords, 0, numCoords / 2
                    )
                }
                return type
            }

            override fun currentSegment(coords: DoubleArray): Int {
                val type = path.pointTypes[typeIdx].toInt()
                val numCoords = curvecoords[type]
                if (numCoords > 0) {
                    affine.transform(
                        doubleCoords, pointIdx,
                        coords, 0, numCoords / 2
                    )
                }
                return type
            }
        }

        companion object {
            fun expandCoords(oldCoords: DoubleArray, needed: Int): DoubleArray {
                val oldSize = oldCoords.size
                val newSizeMin = oldSize + needed
                if (newSizeMin < oldSize) {
                    // hard overflow failure - we can't even accommodate
                    // new items without overflowing
                    throw ArrayIndexOutOfBoundsException(
                        "coords exceeds maximum capacity !"
                    )
                }
                // growth algorithm computation
                var grow = oldSize
                if (grow > EXPAND_MAX_COORDS) {
                    grow = max(EXPAND_MAX_COORDS.toDouble(), (oldSize shr 3).toDouble())
                        .toInt() // 1/8th min
                } else if (grow < EXPAND_MIN) {
                    grow = EXPAND_MIN
                }
                assert(grow > needed)
                var newSize = oldSize + grow
                if (newSize < newSizeMin) {
                    // overflow in growth algorithm computation
                    newSize = Int.MAX_VALUE
                }
                while (true) {
                    try {
                        // try allocating the larger array
                        return oldCoords.copyOf(newSize)
                    } catch (oome: OutOfMemoryError) {
                        if (newSize == newSizeMin) {
                            throw oome
                        }
                    }
                    newSize = newSizeMin + (newSize - newSizeMin) / 2
                }
            }

            /**
             * Use serialVersionUID from JDK 1.6 for interoperability.
             */
            private const val serialVersionUID = 1826762518450014216L
        }
    }

    /**
     * Adds a point to the path by moving to the specified
     * coordinates specified in double precision.
     *
     * @param x the specified X coordinate
     * @param y the specified Y coordinate
     * @since 1.6
     */
    abstract fun moveTo(x: Double, y: Double)

    /**
     * Adds a point to the path by drawing a straight line from the
     * current coordinates to the new specified coordinates
     * specified in double precision.
     *
     * @param x the specified X coordinate
     * @param y the specified Y coordinate
     * @since 1.6
     */
    abstract fun lineTo(x: Double, y: Double)

    /**
     * Adds a curved segment, defined by two new points, to the path by
     * drawing a Quadratic curve that intersects both the current
     * coordinates and the specified coordinates `(x2,y2)`,
     * using the specified point `(x1,y1)` as a quadratic
     * parametric control point.
     * All coordinates are specified in double precision.
     *
     * @param x1 the X coordinate of the quadratic control point
     * @param y1 the Y coordinate of the quadratic control point
     * @param x2 the X coordinate of the final end point
     * @param y2 the Y coordinate of the final end point
     * @since 1.6
     */
    abstract fun quadTo(
        x1: Double, y1: Double,
        x2: Double, y2: Double
    )

    /**
     * Adds a curved segment, defined by three new points, to the path by
     * drawing a Bzier curve that intersects both the current
     * coordinates and the specified coordinates `(x3,y3)`,
     * using the specified points `(x1,y1)` and `(x2,y2)` as
     * Bzier control points.
     * All coordinates are specified in double precision.
     *
     * @param x1 the X coordinate of the first Bzier control point
     * @param y1 the Y coordinate of the first Bzier control point
     * @param x2 the X coordinate of the second Bzier control point
     * @param y2 the Y coordinate of the second Bzier control point
     * @param x3 the X coordinate of the final end point
     * @param y3 the Y coordinate of the final end point
     * @since 1.6
     */
    abstract fun curveTo(
        x1: Double, y1: Double,
        x2: Double, y2: Double,
        x3: Double, y3: Double
    )

    /**
     * Closes the current subpath by drawing a straight line back to
     * the coordinates of the last `moveTo`.  If the path is already
     * closed then this method has no effect.
     *
     * @since 1.6
     */
    @Synchronized
    fun closePath() {
        if (numTypes == 0 || pointTypes[numTypes - 1] != SEG_CLOSE) {
            needRoom(true, 0)
            pointTypes[numTypes++] = SEG_CLOSE
        }
    }

    /**
     * Appends the geometry of the specified `Shape` object to the
     * path, possibly connecting the new geometry to the existing path
     * segments with a line segment.
     * If the `connect` parameter is `true` and the
     * path is not empty then any initial `moveTo` in the
     * geometry of the appended `Shape`
     * is turned into a `lineTo` segment.
     * If the destination coordinates of such a connecting `lineTo`
     * segment match the ending coordinates of a currently open
     * subpath then the segment is omitted as superfluous.
     * The winding rule of the specified `Shape` is ignored
     * and the appended geometry is governed by the winding
     * rule specified for this path.
     *
     * @param s       the `Shape` whose geometry is appended
     * to this path
     * @param connect a boolean to control whether or not to turn an initial
     * `moveTo` segment into a `lineTo` segment
     * to connect the new geometry to the existing path
     * @since 1.6
     */
    fun append(s: Shape, connect: Boolean) {
        append(s.getPathIterator(AffineTransform()), connect)
    }

    /**
     * Appends the geometry of the specified
     * [PathIterator] object
     * to the path, possibly connecting the new geometry to the existing
     * path segments with a line segment.
     * If the `connect` parameter is `true` and the
     * path is not empty then any initial `moveTo` in the
     * geometry of the appended `Shape` is turned into a
     * `lineTo` segment.
     * If the destination coordinates of such a connecting `lineTo`
     * segment match the ending coordinates of a currently open
     * subpath then the segment is omitted as superfluous.
     * The winding rule of the specified `Shape` is ignored
     * and the appended geometry is governed by the winding
     * rule specified for this path.
     *
     * @param pi      the `PathIterator` whose geometry is appended to
     * this path
     * @param connect a boolean to control whether or not to turn an initial
     * `moveTo` segment into a `lineTo` segment
     * to connect the new geometry to the existing path
     * @since 1.6
     */
    abstract fun append(pi: PathIterator, connect: Boolean)


    /**
     * Resets the path to empty.  The append position is set back to the
     * beginning of the path and all coordinates and point types are
     * forgotten.
     *
     * @since 1.6
     */
    @Synchronized
    fun reset() {
        numCoords = 0
        numTypes = numCoords
    }

    /**
     * Transforms the geometry of this path using the specified
     * [AffineTransform].
     * The geometry is transformed in place, which permanently changes the
     * boundary defined by this object.
     *
     * @param at the `AffineTransform` used to transform the area
     * @since 1.6
     */
    abstract fun transform(at: AffineTransform)

    /**
     * Returns a new `Shape` representing a transformed version
     * of this `Path2D`.
     * Note that the exact type and coordinate precision of the return
     * value is not specified for this method.
     * The method will return a Shape that contains no less precision
     * for the transformed geometry than this `Path2D` currently
     * maintains, but it may contain no more precision either.
     * If the tradeoff of precision vs. storage size in the result is
     * important then the convenience constructors in the
     * [Path2D.Float][Path2D.Path2DFloat.Float]
     * and
     * [Path2D.Double][Path2D.Path2DDouble.Double]
     * subclasses should be used to make the choice explicit.
     *
     * @param at the `AffineTransform` used to transform a
     * new `Shape`.
     * @return a new `Shape`, transformed with the specified
     * `AffineTransform`.
     * @since 1.6
     */
    @Synchronized
    fun createTransformedShape(at: AffineTransform?): Shape {
        val p2d = clone() as Path2D
        if (at != null) {
            p2d.transform(at)
        }
        return p2d
    }

    /**
     * {@inheritDoc}
     *
     * @since 1.6
     */
    override fun contains(x: Double, y: Double): Boolean {
        if (x * 0.0 + y * 0.0 == 0.0) {
            /* N * 0.0 is 0.0 only if N is finite.
             * Here we know that both x and y are finite.
             */
            if (numTypes < 2) {
                return false
            }
            val mask = (if (windingRule == WIND_NON_ZERO) -1 else 1)
            return ((pointCrossings(x, y) and mask) != 0)
        } else {
            /* Either x or y was infinite or NaN.
             * A NaN always produces a negative response to any test
             * and Infinity values cannot be "inside" any path so
             * they should return false as well.
             */
            return false
        }
    }

    /**
     * {@inheritDoc}
     *
     * @since 1.6
     */
    override fun contains(p: Point2D): Boolean {
        return contains(p.x, p.y)
    }

    /**
     * {@inheritDoc}
     *
     *
     * This method object may conservatively return false in
     * cases where the specified rectangular area intersects a
     * segment of the path, but that segment does not represent a
     * boundary between the interior and exterior of the path.
     * Such segments could lie entirely within the interior of the
     * path if they are part of a path with a [.WIND_NON_ZERO]
     * winding rule or if the segments are retraced in the reverse
     * direction such that the two sets of segments cancel each
     * other out without any exterior area falling between them.
     * To determine whether segments represent true boundaries of
     * the interior of the path would require extensive calculations
     * involving all of the segments of the path and the winding
     * rule and are thus beyond the scope of this implementation.
     *
     * @since 1.6
     */
    override fun contains(
        x: Double,
        y: Double,
        w: Double,
        h: Double
    ): Boolean {
        if (java.lang.Double.isNaN(x + w) || java.lang.Double.isNaN(y + h)) {
            /* [xy]+[wh] is NaN if any of those values are NaN,
             * or if adding the two together would produce NaN
             * by virtue of adding opposing Infinte values.
             * Since we need to add them below, their sum must
             * not be NaN.
             * We return false because NaN always produces a
             * negative response to tests
             */
            return false
        }
        if (w <= 0 || h <= 0) {
            return false
        }
        val mask = (if (windingRule == WIND_NON_ZERO) -1 else 2)
        val crossings = rectCrossings(x, y, x + w, y + h)
        return (crossings != Curve.RECT_INTERSECTS &&
                (crossings and mask) != 0)
    }

    /**
     * {@inheritDoc}
     *
     *
     * This method object may conservatively return false in
     * cases where the specified rectangular area intersects a
     * segment of the path, but that segment does not represent a
     * boundary between the interior and exterior of the path.
     * Such segments could lie entirely within the interior of the
     * path if they are part of a path with a [.WIND_NON_ZERO]
     * winding rule or if the segments are retraced in the reverse
     * direction such that the two sets of segments cancel each
     * other out without any exterior area falling between them.
     * To determine whether segments represent true boundaries of
     * the interior of the path would require extensive calculations
     * involving all of the segments of the path and the winding
     * rule and are thus beyond the scope of this implementation.
     *
     * @since 1.6
     */
    override fun contains(r: Rectangle2D): Boolean {
        return contains(r.x, r.y, r.width, r.height)
    }

    /**
     * {@inheritDoc}
     *
     *
     * This method object may conservatively return true in
     * cases where the specified rectangular area intersects a
     * segment of the path, but that segment does not represent a
     * boundary between the interior and exterior of the path.
     * Such a case may occur if some set of segments of the
     * path are retraced in the reverse direction such that the
     * two sets of segments cancel each other out without any
     * interior area between them.
     * To determine whether segments represent true boundaries of
     * the interior of the path would require extensive calculations
     * involving all of the segments of the path and the winding
     * rule and are thus beyond the scope of this implementation.
     *
     * @since 1.6
     */
    override fun intersects(
        x: Double,
        y: Double,
        w: Double,
        h: Double
    ): Boolean {
        if (java.lang.Double.isNaN(x + w) || java.lang.Double.isNaN(y + h)) {
            /* [xy]+[wh] is NaN if any of those values are NaN,
             * or if adding the two together would produce NaN
             * by virtue of adding opposing Infinte values.
             * Since we need to add them below, their sum must
             * not be NaN.
             * We return false because NaN always produces a
             * negative response to tests
             */
            return false
        }
        if (w <= 0 || h <= 0) {
            return false
        }
        val mask = (if (windingRule == WIND_NON_ZERO) -1 else 2)
        val crossings = rectCrossings(x, y, x + w, y + h)
        return (crossings == Curve.RECT_INTERSECTS ||
                (crossings and mask) != 0)
    }

    /**
     * {@inheritDoc}
     *
     *
     * This method object may conservatively return true in
     * cases where the specified rectangular area intersects a
     * segment of the path, but that segment does not represent a
     * boundary between the interior and exterior of the path.
     * Such a case may occur if some set of segments of the
     * path are retraced in the reverse direction such that the
     * two sets of segments cancel each other out without any
     * interior area between them.
     * To determine whether segments represent true boundaries of
     * the interior of the path would require extensive calculations
     * involving all of the segments of the path and the winding
     * rule and are thus beyond the scope of this implementation.
     *
     * @since 1.6
     */
    override fun intersects(r: Rectangle2D): Boolean {
        return intersects(r.x, r.y, r.width, r.height)
    }

    /**
     * {@inheritDoc}
     *
     *
     * The iterator for this class is not multi-threaded safe,
     * which means that this `Path2D` class does not
     * guarantee that modifications to the geometry of this
     * `Path2D` object do not affect any iterations of
     * that geometry that are already in process.
     *
     * @since 1.6
     */
    override fun getPathIterator(
        at: AffineTransform,
        flatness: Double
    ): PathIterator {
        return FlatteningPathIterator(getPathIterator(at), flatness)
    }

    /**
     * Creates a new object of the same class as this object.
     *
     * @return a clone of this instance.
     * @throws OutOfMemoryError if there is not enough memory.
     * @see java.lang.Cloneable
     *
     * @since 1.6
     */
    abstract override fun clone(): Any

    // Note: It would be nice to have this return Path2D
    // but one of our subclasses (GeneralPath) needs to
    // offer "public Object clone()" for backwards
    // compatibility so we cannot restrict it further.
    // REMIND: Can we do both somehow?
    /**
     * Trims the capacity of this Path2D instance to its current
     * size. An application can use this operation to minimize the
     * storage of a path.
     *
     * @since 10
     */
    abstract fun trimToSize()

    @Throws(IOException::class)
    fun writeObject(s: ObjectOutputStream, isdbl: Boolean) {
        s.defaultWriteObject()

        val fCoords: FloatArray?
        val dCoords: DoubleArray?

        if (isdbl) {
            dCoords = (this as Path2DDouble).doubleCoords
            fCoords = null
        } else {
            fCoords = (this as Path2DFloat).floatCoords
            dCoords = null
        }

        val numTypes = this.numTypes

        s.writeByte(
            (if (isdbl
            ) SERIAL_STORAGE_DBL_ARRAY
            else SERIAL_STORAGE_FLT_ARRAY).toInt()
        )
        s.writeInt(numTypes)
        s.writeInt(numCoords)
        s.writeByte(windingRule.toByte().toInt())

        var cindex = 0
        for (i in 0 until numTypes) {
            var npoints: Int
            var serialtype: Byte
            when (pointTypes[i]) {
                SEG_MOVETO -> {
                    npoints = 1
                    serialtype = (if (isdbl
                    ) SERIAL_SEG_DBL_MOVETO
                    else SERIAL_SEG_FLT_MOVETO)
                }

                SEG_LINETO -> {
                    npoints = 1
                    serialtype = (if (isdbl
                    ) SERIAL_SEG_DBL_LINETO
                    else SERIAL_SEG_FLT_LINETO)
                }

                SEG_QUADTO -> {
                    npoints = 2
                    serialtype = (if (isdbl
                    ) SERIAL_SEG_DBL_QUADTO
                    else SERIAL_SEG_FLT_QUADTO)
                }

                SEG_CUBICTO -> {
                    npoints = 3
                    serialtype = (if (isdbl
                    ) SERIAL_SEG_DBL_CUBICTO
                    else SERIAL_SEG_FLT_CUBICTO)
                }

                SEG_CLOSE -> {
                    npoints = 0
                    serialtype = SERIAL_SEG_CLOSE
                }

                else ->                     // Should never happen
                    throw InternalError("unrecognized path type")
            }
            s.writeByte(serialtype.toInt())
            while (--npoints >= 0) {
                if (isdbl) {
                    s.writeDouble(dCoords!![cindex++])
                    s.writeDouble(dCoords[cindex++])
                } else {
                    s.writeFloat(fCoords!![cindex++])
                    s.writeFloat(fCoords[cindex++])
                }
            }
        }
        s.writeByte(SERIAL_PATH_END.toInt())
    }

    @Throws(ClassNotFoundException::class, IOException::class)
    fun readObject(s: ObjectInputStream, storedbl: Boolean) {
        s.defaultReadObject()

        // The subclass calls this method with the storage type that
        // they want us to use (storedbl) so we ignore the storage
        // method hint from the stream.
        s.readByte()
        val nT = s.readInt()
        var nC = s.readInt()
        try {
            windingRule = s.readByte().toInt()
        } catch (iae: IllegalArgumentException) {
            throw InvalidObjectException(iae.message)
        }

        // Accept the size from the stream only if it is less than INIT_SIZE
        // otherwise the size will be based on the real data in the stream
        pointTypes = ByteArray(if ((nT < 0 || nT > INIT_SIZE)) INIT_SIZE else nT)
        val initX2 = INIT_SIZE * 2
        if (nC < 0 || nC > initX2) {
            nC = initX2
        }
        if (storedbl) {
            (this as Path2DDouble).doubleCoords = DoubleArray(nC)
        } else {
            (this as Path2DFloat).floatCoords = FloatArray(nC)
        }

        var i = 0
        PATHDONE@ while (nT < 0 || i < nT) {
            var isdbl: Boolean
            var npoints: Int
            var segtype: Byte

            val serialtype = s.readByte()
            when (serialtype) {
                SERIAL_SEG_FLT_MOVETO -> {
                    isdbl = false
                    npoints = 1
                    segtype = SEG_MOVETO
                }

                SERIAL_SEG_FLT_LINETO -> {
                    isdbl = false
                    npoints = 1
                    segtype = SEG_LINETO
                }

                SERIAL_SEG_FLT_QUADTO -> {
                    isdbl = false
                    npoints = 2
                    segtype = SEG_QUADTO
                }

                SERIAL_SEG_FLT_CUBICTO -> {
                    isdbl = false
                    npoints = 3
                    segtype = SEG_CUBICTO
                }

                SERIAL_SEG_DBL_MOVETO -> {
                    isdbl = true
                    npoints = 1
                    segtype = SEG_MOVETO
                }

                SERIAL_SEG_DBL_LINETO -> {
                    isdbl = true
                    npoints = 1
                    segtype = SEG_LINETO
                }

                SERIAL_SEG_DBL_QUADTO -> {
                    isdbl = true
                    npoints = 2
                    segtype = SEG_QUADTO
                }

                SERIAL_SEG_DBL_CUBICTO -> {
                    isdbl = true
                    npoints = 3
                    segtype = SEG_CUBICTO
                }

                SERIAL_SEG_CLOSE -> {
                    isdbl = false
                    npoints = 0
                    segtype = SEG_CLOSE
                }

                SERIAL_PATH_END -> {
                    if (nT < 0) {
                        break@PATHDONE
                    }
                    throw StreamCorruptedException("unexpected PATH_END")
                }

                else -> throw StreamCorruptedException("unrecognized path type")
            }
            needRoom(segtype != SEG_MOVETO, npoints * 2)
            if (isdbl) {
                while (--npoints >= 0) {
                    append(s.readDouble(), s.readDouble())
                }
            } else {
                while (--npoints >= 0) {
                    append(s.readFloat(), s.readFloat())
                }
            }
            pointTypes[numTypes++] = segtype
            i++
        }
        if (nT >= 0 && s.readByte() != SERIAL_PATH_END) {
            throw StreamCorruptedException("missing PATH_END")
        }
    }

    internal abstract class Iterator(
        var path: Path2D
    ) : PathIterator {
        var typeIdx: Int = 0
        var pointIdx: Int = 0

        override val windingRule
            get() = path.windingRule

        override var isDone = (typeIdx >= path.numTypes)

        override fun next() {
            val type = path.pointTypes[typeIdx++].toInt()
            pointIdx += curvecoords[type]
        }

        companion object {
            val curvecoords: IntArray = intArrayOf(2, 2, 4, 6, 0)
        }
    }

    companion object {
        /** An even-odd winding rule for determining the interior of
         * a path.
         * @see PathIterator.WIND_EVEN_ODD */
        const val WIND_EVEN_ODD: Int = PathIterator.WIND_EVEN_ODD

        /** A non-zero winding rule for determining the interior of a
         * path.
         * @see PathIterator.WIND_NON_ZERO */
        const val WIND_NON_ZERO: Int = PathIterator.WIND_NON_ZERO

        // For code simplicity, copy these constants to our namespace
        // and cast them to byte constants for easy storage.
        private const val SEG_MOVETO = PathIterator.SEG_MOVETO.toByte()
        private const val SEG_LINETO = PathIterator.SEG_LINETO.toByte()
        private const val SEG_QUADTO = PathIterator.SEG_QUADTO.toByte()
        private const val SEG_CUBICTO = PathIterator.SEG_CUBICTO.toByte()
        private const val SEG_CLOSE = PathIterator.SEG_CLOSE.toByte()

        const val INIT_SIZE: Int = 20
        const val EXPAND_MAX: Int = 500
        const val EXPAND_MAX_COORDS: Int = EXPAND_MAX * 2
        const val EXPAND_MIN: Int = 10 // ensure > 6 (cubics)

        fun expandPointTypes(oldPointTypes: ByteArray, needed: Int): ByteArray {
            val oldSize = oldPointTypes.size
            val newSizeMin = oldSize + needed
            if (newSizeMin < oldSize) {
                // hard overflow failure - we can't even accommodate
                // new items without overflowing
                throw ArrayIndexOutOfBoundsException(
                    "pointTypes exceeds maximum capacity !"
                )
            }
            // growth algorithm computation
            var grow = oldSize
            if (grow > EXPAND_MAX) {
                grow = max(EXPAND_MAX.toDouble(), (oldSize shr 3).toDouble())
                    .toInt() // 1/8th min
            } else if (grow < EXPAND_MIN) {
                grow = EXPAND_MIN
            }
            assert(grow > 0)
            var newSize = oldSize + grow
            if (newSize < newSizeMin) {
                // overflow in growth algorithm computation
                newSize = Int.MAX_VALUE
            }
            while (true) {
                try {
                    // try allocating the larger array
                    return oldPointTypes.copyOf(newSize)
                } catch (oome: OutOfMemoryError) {
                    if (newSize == newSizeMin) {
                        throw oome
                    }
                }
                newSize = newSizeMin + (newSize - newSizeMin) / 2
            }
        }

        /**
         * Tests if the specified coordinates are inside the closed
         * boundary of the specified [PathIterator].
         *
         *
         * This method provides a basic facility for implementors of
         * the [Shape] interface to implement support for the
         * [Shape.contains] method.
         *
         * @param pi the specified `PathIterator`
         * @param x  the specified X coordinate
         * @param y  the specified Y coordinate
         * @return `true` if the specified coordinates are inside the
         * specified `PathIterator`; `false` otherwise
         * @since 1.6
         */
        fun contains(pi: PathIterator, x: Double, y: Double): Boolean {
            if (x * 0.0 + y * 0.0 == 0.0) {
                /* N * 0.0 is 0.0 only if N is finite.
             * Here we know that both x and y are finite.
             */
                val mask = (if (pi.windingRule == WIND_NON_ZERO) -1 else 1)
                val cross = pointCrossingsForPath(pi, x, y)
                return ((cross and mask) != 0)
            } else {
                /* Either x or y was infinite or NaN.
             * A NaN always produces a negative response to any test
             * and Infinity values cannot be "inside" any path so
             * they should return false as well.
             */
                return false
            }
        }

        /**
         * Tests if the specified [Point2D] is inside the closed
         * boundary of the specified [PathIterator].
         *
         *
         * This method provides a basic facility for implementors of
         * the [Shape] interface to implement support for the
         * [Shape.contains] method.
         *
         * @param pi the specified `PathIterator`
         * @param p  the specified `Point2D`
         * @return `true` if the specified coordinates are inside the
         * specified `PathIterator`; `false` otherwise
         * @since 1.6
         */
        fun contains(pi: PathIterator, p: Point2D): Boolean {
            return contains(pi, p.x, p.y)
        }

        /**
         * Tests if the specified rectangular area is entirely inside the
         * closed boundary of the specified [PathIterator].
         *
         *
         * This method provides a basic facility for implementors of
         * the [Shape] interface to implement support for the
         * [Shape.contains] method.
         *
         *
         * This method object may conservatively return false in
         * cases where the specified rectangular area intersects a
         * segment of the path, but that segment does not represent a
         * boundary between the interior and exterior of the path.
         * Such segments could lie entirely within the interior of the
         * path if they are part of a path with a [.WIND_NON_ZERO]
         * winding rule or if the segments are retraced in the reverse
         * direction such that the two sets of segments cancel each
         * other out without any exterior area falling between them.
         * To determine whether segments represent true boundaries of
         * the interior of the path would require extensive calculations
         * involving all of the segments of the path and the winding
         * rule and are thus beyond the scope of this implementation.
         *
         * @param pi the specified `PathIterator`
         * @param x  the specified X coordinate
         * @param y  the specified Y coordinate
         * @param w  the width of the specified rectangular area
         * @param h  the height of the specified rectangular area
         * @return `true` if the specified `PathIterator` contains
         * the specified rectangular area; `false` otherwise.
         * @since 1.6
         */
        fun contains(
            pi: PathIterator,
            x: Double, y: Double, w: Double, h: Double
        ): Boolean {
            if (java.lang.Double.isNaN(x + w) || java.lang.Double.isNaN(y + h)) {
                /* [xy]+[wh] is NaN if any of those values are NaN,
             * or if adding the two together would produce NaN
             * by virtue of adding opposing Infinte values.
             * Since we need to add them below, their sum must
             * not be NaN.
             * We return false because NaN always produces a
             * negative response to tests
             */
                return false
            }
            if (w <= 0 || h <= 0) {
                return false
            }
            val mask = (if (pi.windingRule == WIND_NON_ZERO) -1 else 2)
            val crossings = rectCrossingsForPath(pi, x, y, x + w, y + h)
            return (crossings != Curve.RECT_INTERSECTS &&
                    (crossings and mask) != 0)
        }

        /**
         * Tests if the specified [Rectangle2D] is entirely inside the
         * closed boundary of the specified [PathIterator].
         *
         *
         * This method provides a basic facility for implementors of
         * the [Shape] interface to implement support for the
         * [Shape.contains] method.
         *
         *
         * This method object may conservatively return false in
         * cases where the specified rectangular area intersects a
         * segment of the path, but that segment does not represent a
         * boundary between the interior and exterior of the path.
         * Such segments could lie entirely within the interior of the
         * path if they are part of a path with a [.WIND_NON_ZERO]
         * winding rule or if the segments are retraced in the reverse
         * direction such that the two sets of segments cancel each
         * other out without any exterior area falling between them.
         * To determine whether segments represent true boundaries of
         * the interior of the path would require extensive calculations
         * involving all of the segments of the path and the winding
         * rule and are thus beyond the scope of this implementation.
         *
         * @param pi the specified `PathIterator`
         * @param r  a specified `Rectangle2D`
         * @return `true` if the specified `PathIterator` contains
         * the specified `Rectangle2D`; `false` otherwise.
         * @since 1.6
         */
        fun contains(pi: PathIterator, r: Rectangle2D): Boolean {
            return contains(pi, r.x, r.y, r.width, r.height)
        }

        /**
         * Tests if the interior of the specified [PathIterator]
         * intersects the interior of a specified set of rectangular
         * coordinates.
         *
         *
         * This method provides a basic facility for implementors of
         * the [Shape] interface to implement support for the
         * [Shape.intersects] method.
         *
         *
         * This method object may conservatively return true in
         * cases where the specified rectangular area intersects a
         * segment of the path, but that segment does not represent a
         * boundary between the interior and exterior of the path.
         * Such a case may occur if some set of segments of the
         * path are retraced in the reverse direction such that the
         * two sets of segments cancel each other out without any
         * interior area between them.
         * To determine whether segments represent true boundaries of
         * the interior of the path would require extensive calculations
         * involving all of the segments of the path and the winding
         * rule and are thus beyond the scope of this implementation.
         *
         * @param pi the specified `PathIterator`
         * @param x  the specified X coordinate
         * @param y  the specified Y coordinate
         * @param w  the width of the specified rectangular coordinates
         * @param h  the height of the specified rectangular coordinates
         * @return `true` if the specified `PathIterator` and
         * the interior of the specified set of rectangular
         * coordinates intersect each other; `false` otherwise.
         * @since 1.6
         */
        fun intersects(
            pi: PathIterator,
            x: Double, y: Double, w: Double, h: Double
        ): Boolean {
            if (java.lang.Double.isNaN(x + w) || java.lang.Double.isNaN(y + h)) {
                /* [xy]+[wh] is NaN if any of those values are NaN,
             * or if adding the two together would produce NaN
             * by virtue of adding opposing Infinte values.
             * Since we need to add them below, their sum must
             * not be NaN.
             * We return false because NaN always produces a
             * negative response to tests
             */
                return false
            }
            if (w <= 0 || h <= 0) {
                return false
            }
            val mask = (if (pi.windingRule == WIND_NON_ZERO) -1 else 2)
            val crossings = rectCrossingsForPath(pi, x, y, x + w, y + h)
            return (crossings == Curve.RECT_INTERSECTS ||
                    (crossings and mask) != 0)
        }

        /**
         * Tests if the interior of the specified [PathIterator]
         * intersects the interior of a specified [Rectangle2D].
         *
         *
         * This method provides a basic facility for implementors of
         * the [Shape] interface to implement support for the
         * [Shape.intersects] method.
         *
         *
         * This method object may conservatively return true in
         * cases where the specified rectangular area intersects a
         * segment of the path, but that segment does not represent a
         * boundary between the interior and exterior of the path.
         * Such a case may occur if some set of segments of the
         * path are retraced in the reverse direction such that the
         * two sets of segments cancel each other out without any
         * interior area between them.
         * To determine whether segments represent true boundaries of
         * the interior of the path would require extensive calculations
         * involving all of the segments of the path and the winding
         * rule and are thus beyond the scope of this implementation.
         *
         * @param pi the specified `PathIterator`
         * @param r  the specified `Rectangle2D`
         * @return `true` if the specified `PathIterator` and
         * the interior of the specified `Rectangle2D`
         * intersect each other; `false` otherwise.
         * @since 1.6
         */
        fun intersects(pi: PathIterator, r: Rectangle2D): Boolean {
            return intersects(pi, r.x, r.y, r.width, r.height)
        }

        /*
     * Support fields and methods for serializing the subclasses.
     */
        private const val SERIAL_STORAGE_FLT_ARRAY: Byte = 0x30
        private const val SERIAL_STORAGE_DBL_ARRAY: Byte = 0x31

        private const val SERIAL_SEG_FLT_MOVETO: Byte = 0x40
        private const val SERIAL_SEG_FLT_LINETO: Byte = 0x41
        private const val SERIAL_SEG_FLT_QUADTO: Byte = 0x42
        private const val SERIAL_SEG_FLT_CUBICTO: Byte = 0x43

        private const val SERIAL_SEG_DBL_MOVETO: Byte = 0x50
        private const val SERIAL_SEG_DBL_LINETO: Byte = 0x51
        private const val SERIAL_SEG_DBL_QUADTO: Byte = 0x52
        private const val SERIAL_SEG_DBL_CUBICTO: Byte = 0x53

        private const val SERIAL_SEG_CLOSE: Byte = 0x60
        private const val SERIAL_PATH_END: Byte = 0x61
    }
}
