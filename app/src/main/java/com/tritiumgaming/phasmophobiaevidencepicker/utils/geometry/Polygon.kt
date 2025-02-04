package com.tritiumgaming.phasmophobiaevidencepicker.utils.geometry

import java.io.Serializable
import kotlin.math.max
import kotlin.math.min

/**
 * The `Polygon` class encapsulates a description of a
 * closed, two-dimensional region within a coordinate space. This
 * region is bounded by an arbitrary number of line segments, each of
 * which is one side of the polygon. Internally, a polygon
 * comprises of a list of `(x,y)`
 * coordinate pairs, where each pair defines a *vertex* of the
 * polygon, and two successive pairs are the endpoints of a
 * line that is a side of the polygon. The first and final
 * pairs of `(x,y)` points are joined by a line segment
 * that closes the polygon.  This `Polygon` is defined with
 * an even-odd winding rule.  See
 * [WIND_EVEN_ODD][PathIterator.WIND_EVEN_ODD]
 * for a definition of the even-odd winding rule.
 * This class's hit-testing methods, which include the
 * `contains`, `intersects` and `inside`
 * methods, use the *insideness* definition described in the
 * class comments.
 * @author Sami Shaio
 * @author Herb Jellinek */
class Polygon : Shape, Serializable {

    val boundingBox: Rectangle
        /** Returns the bounds of this `Polygon`.
         * @return the bounds of this `Polygon`. */
        get() {
            if (npoints == 0) {
                return Rectangle()
            }
            if (rectBounds == null) {
                calculateBounds(xpoints, ypoints, npoints)
            }
            return rectBounds!!.bounds
        }

    override val bounds: Rectangle
        get() = boundingBox

    override val bounds2D: Rectangle2D
        get() = bounds

    /** The total number of points.  The value of `npoints`
     * represents the number of valid points in this `Polygon`
     * and might be less than the number of elements in
     * [xpoints][.xpoints] or [ypoints][.ypoints].
     * This value can be 0.
     * @serial
     * @see .addPoint */
    var npoints: Int = 0

    /** The array of X coordinates.  The number of elements in
     * this array might be more than the number of X coordinates
     * in this `Polygon`.  The extra elements allow new points
     * to be added to this `Polygon` without re-creating this
     * array.  The value of [npoints][.npoints] is equal to the
     * number of valid points in this `Polygon`.
     * @serial
     * @see .addPoint
     * @since 1.0 */
    var xpoints: IntArray

    /** The array of Y coordinates.  The number of elements in
     * this array might be more than the number of Y coordinates
     * in this `Polygon`.  The extra elements allow new points
     * to be added to this `Polygon` without re-creating this
     * array.  The value of `npoints` is equal to the
     * number of valid points in this `Polygon`.
     * @serial
     * @see .addPoint */
    var ypoints: IntArray

    /** The bounds of this `Polygon`. This value can be null.
     * @serial
     * @see .getBoundingBox
     * @see .getBounds */
    protected var rectBounds: Rectangle? = null

    /** Creates an empty polygon. */
    constructor() {
        xpoints = IntArray(MIN_LENGTH)
        ypoints = IntArray(MIN_LENGTH)
    }

    /** Constructs and initializes a `Polygon` from the specified
     * parameters.
     * @param xpoints an array of X coordinates
     * @param ypoints an array of Y coordinates
     * @param npoints the total number of points in the `Polygon`
     * @throws NegativeArraySizeException if the value of `npoints` is negative.
     * @throws IndexOutOfBoundsException  if `npoints` is greater than the length of `xpoints`
     * or the length of `ypoints`.
     * @throws NullPointerException       if `xpoints` or `ypoints` is `null`. */
    constructor(xpoints: IntArray, ypoints: IntArray, npoints: Int) {
        // Fix 4489009: should throw IndexOutOfBoundsException instead
        // of OutOfMemoryError if npoints is huge and > {x,y}points.length
        if (npoints > xpoints.size || npoints > ypoints.size) {
            throw IndexOutOfBoundsException(
                "npoints > xpoints.length || " +
                        "npoints > ypoints.length"
            )
        }
        // Fix 6191114: should throw NegativeArraySizeException with
        // negative npoints
        if (npoints < 0) {
            throw NegativeArraySizeException("npoints < 0")
        }
        // Fix 6343431: Applet compatibility problems if arrays are not
        // exactly npoints in length
        this.npoints = npoints
        this.xpoints = xpoints.copyOf(npoints)
        this.ypoints = ypoints.copyOf(npoints)
    }

    /**
     * Resets this `Polygon` object to an empty polygon.
     * The coordinate arrays and the data in them are left untouched
     * but the number of points is reset to zero to mark the old
     * vertex data as invalid and to start accumulating new vertex
     * data at the beginning.
     * All internally-cached data relating to the old vertices
     * are discarded.
     * Note that since the coordinate arrays from before the reset
     * are reused, creating a new empty `Polygon` might
     * be more memory efficient than resetting the current one if
     * the number of vertices in the new polygon data is significantly
     * smaller than the number of vertices in the data from before the
     * reset.
     * @see Polygon.invalidate */
    fun reset() {
        npoints = 0
        rectBounds = null
    }

    /** Invalidates or flushes any internally-cached data that depends on the vertex coordinates of
     * this `Polygon`. This method should be called after any direct manipulation of the coordinates
     * in the `xpoints` or `ypoints` arrays to avoid inconsistent results from methods such as
     * `getBounds` or `contains` that might cache data from earlier computations relating to
     * the vertex coordinates.
     * @see Polygon.getBounds */
    fun invalidate() {
        rectBounds = null
    }

    /** Translates the vertices of the `Polygon` by `deltaX` along the x axis and by `deltaY`
     * along the y axis.
     * @param deltaX the amount to translate along the X axis
     * @param deltaY the amount to translate along the Y axis */
    fun translate(deltaX: Int, deltaY: Int) {
        for (i in 0 until npoints) {
            xpoints[i] += deltaX
            ypoints[i] += deltaY
        }

        rectBounds?.translate(deltaX, deltaY)
    }

    /** Calculates the bounding box of the points passed to the constructor.
     * Sets {@code bounds} to the result.
     * @param xpoints[] array of <i>x</i> coordinates
     * @param ypoints[] array of <i>y</i> coordinates
     * @param npoints the total number of points */
    fun calculateBounds(xpoints: IntArray, ypoints: IntArray, npoints: Int) {
        var boundsMinX = Int.MAX_VALUE
        var boundsMinY = Int.MAX_VALUE
        var boundsMaxX = Int.MIN_VALUE
        var boundsMaxY = Int.MIN_VALUE

        for (i in 0 until npoints) {
            val x = xpoints[i]
            boundsMinX = min(boundsMinX.toDouble(), x.toDouble()).toInt()
            boundsMaxX = max(boundsMaxX.toDouble(), x.toDouble()).toInt()
            val y = ypoints[i]
            boundsMinY = min(boundsMinY.toDouble(), y.toDouble()).toInt()
            boundsMaxY = max(boundsMaxY.toDouble(), y.toDouble()).toInt()
        }
        rectBounds = Rectangle(
            boundsMinX.toDouble(), boundsMinY.toDouble(),
            (boundsMaxX - boundsMinX).toDouble(),
            (boundsMaxY - boundsMinY).toDouble()
        )
    }

    /** Resizes the bounding box to accommodate the specified coordinates. */
    fun updateBounds(x: Int, y: Int) {
        if (x < rectBounds!!.x) {
            rectBounds!!.width += (rectBounds!!.x - x)
            rectBounds!!.x = x.toDouble()
        } else {
            rectBounds!!.width = max(rectBounds!!.width, (x - rectBounds!!.x))
                .toInt().toDouble()
            // bounds.x = bounds.x;
        }

        if (y < rectBounds!!.y) {
            rectBounds!!.height += (rectBounds!!.y - y)
            rectBounds!!.y = y.toDouble()
        } else {
            rectBounds!!.height = max(rectBounds!!.height, (y - rectBounds!!.y))
                .toInt().toDouble()
            // bounds.y = bounds.y;
        }
    }

    /** Appends the specified coordinates to this `Polygon`. If an operation that calculates the
     * bounding box of this `Polygon` has already been performed, such as `getBounds` or `contains`,
     * then this method updates the bounding box.
     * @param x the specified X coordinate
     * @param y the specified Y coordinate
     * @see Polygon.getBounds
     * @see Polygon.contains */
    fun addPoint(x: Int, y: Int) {
        if (npoints >= xpoints.size || npoints >= ypoints.size) {
            var newLength = npoints * 2
            // Make sure that newLength will be greater than MIN_LENGTH and
            // aligned to the power of 2
            if (newLength < MIN_LENGTH) {
                newLength = MIN_LENGTH
            } else if ((newLength and (newLength - 1)) != 0) {
                newLength = Integer.highestOneBit(newLength)
            }

            xpoints = xpoints.copyOf(newLength)
            ypoints = ypoints.copyOf(newLength)
        }
        xpoints[npoints] = x
        ypoints[npoints] = y
        npoints++
        if (rectBounds != null) {
            updateBounds(x, y)
        }
    }

    /** Determines whether the specified [Point] is inside this `Polygon`.
     * @param p the specified `Point` to be tested
     * @return `true` if the `Polygon` contains the
     * `Point`; `false` otherwise.
     * @see .contains */
    fun contains(p: Point): Boolean {
        return contains(p.x, p.y)
    }

    /** Determines whether the specified coordinates are inside this `Polygon`.
     * @param x the specified X coordinate to be tested
     * @param y the specified Y coordinate to be tested
     * @return `true` if this `Polygon` contains
     * the specified coordinates `(x,y)`; `false` otherwise.
     * @see .contains */
    fun contains(x: Int, y: Int): Boolean {
        return contains(x.toDouble(), y.toDouble())
    }

    /** Determines whether the specified coordinates are contained in this `Polygon`.
     * @param x the specified X coordinate to be tested
     * @param y the specified Y coordinate to be tested
     * @return `true` if this `Polygon` contains
     * the specified coordinates `(x,y)`;
     * `false` otherwise.
     * @see .contains */
    @Deprecated(
        "As of JDK version 1.1",
        ReplaceWith("contains(x.toDouble(), y.toDouble())")
    )
    fun inside(x: Int, y: Int): Boolean {
        return contains(x.toDouble(), y.toDouble())
    }

    /** {@inheritDoc} */
    override fun contains(x: Double, y: Double): Boolean {
        if (npoints <= 2 || !boundingBox.contains(x, y)) {
            return false
        }
        var hits = 0

        var lastx = xpoints[npoints - 1]
        var lasty = ypoints[npoints - 1]
        var curx: Int
        var cury: Int

        // Walk the edges of the polygon
        var i = 0
        while (i < npoints) {
            curx = xpoints[i]
            cury = ypoints[i]

            if (cury == lasty) {
                lastx = curx
                lasty = cury
                i++
                continue
            }

            var leftx: Int
            if (curx < lastx) {
                if (x >= lastx) {
                    lastx = curx
                    lasty = cury
                    i++
                    continue
                }
                leftx = curx
            } else {
                if (x >= curx) {
                    lastx = curx
                    lasty = cury
                    i++
                    continue
                }
                leftx = lastx
            }

            var test1: Double
            var test2: Double
            if (cury < lasty) {
                if (y < cury || y >= lasty) {
                    lastx = curx
                    lasty = cury
                    i++
                    continue
                }
                if (x < leftx) {
                    hits++
                    lastx = curx
                    lasty = cury
                    i++
                    continue
                }
                test1 = x - curx
                test2 = y - cury
            } else {
                if (y < lasty || y >= cury) {
                    lastx = curx
                    lasty = cury
                    i++
                    continue
                }
                if (x < leftx) {
                    hits++
                    lastx = curx
                    lasty = cury
                    i++
                    continue
                }
                test1 = x - lastx
                test2 = y - lasty
            }

            if (test1 < (test2 / (lasty - cury) * (lastx - curx))) {
                hits++
            }
            lastx = curx
            lasty = cury
            i++
        }

        return ((hits and 1) != 0)
    }

    private fun getCrossings(
        xlo: Double, ylo: Double,
        xhi: Double, yhi: Double
    ): Crossings? {
        val cross: Crossings = Crossings.EvenOdd(xlo, ylo, xhi, yhi)
        var lastx = xpoints[npoints - 1]
        var lasty = ypoints[npoints - 1]
        var curx: Int
        var cury: Int

        // Walk the edges of the polygon
        for (i in 0 until npoints) {
            curx = xpoints[i]
            cury = ypoints[i]
            if (cross.accumulateLine(
                    lastx.toDouble(),
                    lasty.toDouble(),
                    curx.toDouble(),
                    cury.toDouble()
                )
            ) {
                return null
            }
            lastx = curx
            lasty = cury
        }

        return cross
    }

    /** {@inheritDoc} */
    override fun contains(p: Point2D): Boolean {
        return contains(p.x, p.y)
    }

    /** {@inheritDoc} */
    override fun intersects(x: Double, y: Double, w: Double, h: Double): Boolean {
        if (npoints <= 0 || !boundingBox.intersects(x, y, w, h)) {
            return false
        }

        val cross = getCrossings(x, y, x + w, y + h)
        return (cross == null || !cross.isEmpty)
    }

    /** {@inheritDoc} */
    override fun intersects(r: Rectangle2D): Boolean {
        return intersects(r.x, r.y, r.width, r.height)
    }

    /** {@inheritDoc} */
    override fun contains(x: Double, y: Double, w: Double, h: Double): Boolean {
        if (npoints <= 0 || !boundingBox.intersects(x, y, w, h)) {
            return false
        }

        val cross = getCrossings(x, y, x + w, y + h)
        return (cross != null && cross.covers(y, y + h))
    }

    /** {@inheritDoc} */
    override fun contains(r: Rectangle2D): Boolean {
        return contains(r.x, r.y, r.width, r.height)
    }

    /** Returns an iterator object that iterates along the boundary of this `Polygon` and provides
     * access to the geometry of the outline of this `Polygon`.  An optional [AffineTransform]
     * can be specified so that the coordinates returned in the iteration are transformed
     * accordingly.
     * @param at an optional `AffineTransform` to be applied to the coordinates as they are
     * returned in the iteration, or `null` if untransformed coordinates are desired
     * @return a [PathIterator] object that provides access to the geometry of this `Polygon`. */
    override fun getPathIterator(at: AffineTransform?): PathIterator {
        return PolygonPathIterator(
            this, at
        )
    }

    /** Returns an iterator object that iterates along the boundary of the `Shape` and provides
     * access to the geometry of the outline of the `Shape`.  Only SEG_MOVETO, SEG_LINETO, and
     * SEG_CLOSE point types are returned by the iterator. Since polygons are already flat, the
     * `flatness` parameter is ignored.  An optional `AffineTransform` can be specified
     * in which case the coordinates returned in the iteration are transformed accordingly.
     * @param at       an optional `AffineTransform` to be applied to the coordinates as they are
     * returned in the iteration, or `null` if untransformed coordinates are desired
     * @param flatness the maximum amount that the control points for a given curve can vary from
     * collinear before a subdivided curve is replaced by a straight line connecting the
     * endpoints. Since polygons are already flat the `flatness` parameter is ignored.
     * @return a `PathIterator` object that provides access to the `Shape` object's geometry. */
    override fun getPathIterator(at: AffineTransform, flatness: Double): PathIterator {
        return getPathIterator(at)
    }

    internal class PolygonPathIterator(
        var poly: Polygon,
        var transform: AffineTransform?
    ) : PathIterator {
        var index: Int = 0

        /** Returns the winding rule for determining the interior of the path.
         * @return an integer representing the current winding rule.
         * @see PathIterator.WIND_NON_ZERO */
        override val windingRule: Int
            get() = PathIterator.WIND_EVEN_ODD

        init {
            if (poly.npoints == 0) {
                // Prevent a spurious SEG_CLOSE segment
                index = 1
            }
        }

        /** Tests if there are more points to read.
         * @return `true` if there are more points to read; `false` otherwise. */
        override val isDone = index > poly.npoints

        /** Moves the iterator forwards, along the primary direction of traversal, to the next
         * segment of the path when there are more points in that direction. */
        override fun next() {
            index++
        }

        /** Returns the coordinates and type of the current path segment in the iteration.
         * The return value is the path segment type: SEG_MOVETO, SEG_LINETO, or SEG_CLOSE.
         * A `float` array of length 2 must be passed in and
         * can be used to store the coordinates of the point(s).
         * Each point is stored as a pair of `float` x,&nbsp;y
         * coordinates.  SEG_MOVETO and SEG_LINETO types return one
         * point, and SEG_CLOSE does not return any points.
         * @param coords a `float` array that specifies the coordinates of the point(s)
         * @return an integer representing the type and coordinates of the current path segment.
         * @see PathIterator.SEG_MOVETO
         * @see PathIterator.SEG_LINETO
         * @see PathIterator.SEG_CLOSE */
        override fun currentSegment(coords: FloatArray): Int {
            if (index >= poly.npoints) {
                return PathIterator.SEG_CLOSE
            }
            coords[0] = poly.xpoints[index].toFloat()
            coords[1] = poly.ypoints[index].toFloat()
            if (transform != null) {
                transform!!.transform(coords, 0, coords, 0, 1)
            }
            return (if (index == 0) PathIterator.SEG_MOVETO else PathIterator.SEG_LINETO)
        }

        /** Returns the coordinates and type of the current path segment in the iteration.
         * The return value is the path segment type: SEG_MOVETO, SEG_LINETO, or SEG_CLOSE.
         * A `double` array of length 2 must be passed in and
         * can be used to store the coordinates of the point(s).
         * Each point is stored as a pair of `double` x,&nbsp;y coordinates.
         * SEG_MOVETO and SEG_LINETO types return one point,
         * and SEG_CLOSE does not return any points.
         * @param coords a `double` array that specifies the coordinates of the point(s)
         * @return an integer representing the type and coordinates of the current path segment.
         * @see PathIterator.SEG_MOVETO
         * @see PathIterator.SEG_LINETO
         * @see PathIterator.SEG_CLOSE */
        override fun currentSegment(coords: DoubleArray): Int {
            if (index >= poly.npoints) {
                return PathIterator.SEG_CLOSE
            }
            coords[0] = poly.xpoints[index].toDouble()
            coords[1] = poly.ypoints[index].toDouble()
            if (transform != null) {
                transform!!.transform(coords, 0, coords, 0, 1)
            }
            return (if (index == 0) PathIterator.SEG_MOVETO else PathIterator.SEG_LINETO)
        }
    }

    companion object {
        /** Use serialVersionUID from JDK 1.1 for interoperability. */
        private const val serialVersionUID = -6460061437900069969L

        /*
         * Default length for xpoints and ypoints.
         */
        private const val MIN_LENGTH = 4
    }
}
