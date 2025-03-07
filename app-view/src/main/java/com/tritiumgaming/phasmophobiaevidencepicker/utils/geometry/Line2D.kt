/*
 * Copyright (c) 1997, 2021, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package com.tritiumgaming.phasmophobiaevidencepicker.utils.geometry

import android.os.Build
import androidx.annotation.RequiresApi
import com.tritiumgaming.phasmophobiaevidencepicker.utils.geometry.Line2D.Line2DDouble
import com.tritiumgaming.phasmophobiaevidencepicker.utils.geometry.Line2D.Line2DFloat
import com.tritiumgaming.phasmophobiaevidencepicker.utils.geometry.Point2D.Point2DDouble
import com.tritiumgaming.phasmophobiaevidencepicker.utils.geometry.Point2D.Point2DFloat
import com.tritiumgaming.phasmophobiaevidencepicker.utils.geometry.Rectangle2D.Rectangle2DDouble
import com.tritiumgaming.phasmophobiaevidencepicker.utils.geometry.Rectangle2D.Rectangle2DFloat
import java.io.Serial
import java.io.Serializable
import kotlin.math.sqrt

/**
 * This `Line2D` represents a line segment in `(x,y)`
 * coordinate space.
 *
 *
 * This class is only the abstract superclass for all objects that
 * store a 2D line segment.
 * The actual storage representation of the coordinates is left to
 * the subclass.
 *
 * @author Jim Graham
 * @since 1.2
 */
abstract class Line2D
/** This is an abstract class that cannot be instantiated directly.
 * Type-specific implementation subclasses are available for
 * instantiation and provide a number of formats for storing
 * the information necessary to satisfy the various accessory
 * methods below.
 * @see Line2DFloat
 * @see Line2DDouble
 */
protected constructor() : Shape, Cloneable {
    /** A line segment specified with float coordinates.  */

    class Line2DFloat : Line2D, Serializable {
        /** The X coordinate of the start point of the line segment.  */
        override var x1: Double = 0.0

        /** The Y coordinate of the start point of the line segment.  */
        override var y1: Double = 0.0

        /** The X coordinate of the end point of the line segment.  */
        override var x2: Double = 0.0

        /** The Y coordinate of the end point of the line segment.  */
        override var y2: Double = 0.0

        override val p1: Point2D
            get() = Point2DFloat(x1.toFloat(), y1.toFloat())
        override val p2: Point2D
            get() = Point2DFloat(x2.toFloat(), y2.toFloat())

        /** Constructs and initializes a Line with coordinates (0, 0)  (0, 0).  */
        constructor()

        /** Constructs and initializes a Line from the specified coordinates.
         * @param x1 the X coordinate of the start point
         * @param y1 the Y coordinate of the start point
         * @param x2 the X coordinate of the end point
         * @param y2 the Y coordinate of the end point
         */
        constructor(x1: Float, y1: Float, x2: Float, y2: Float) {
            setLine(x1, y1, x2, y2)
        }

        /** Constructs and initializes a `Line2D` from the
         * specified `Point2D` objects.
         * @param p1 the start `Point2D` of this line segment
         * @param p2 the end `Point2D` of this line segment
         */
        constructor(p1: Point2D, p2: Point2D) {
            setLine(p1, p2)
        }

        /** {@inheritDoc}  */
        override fun setLine(x1: Double, y1: Double, x2: Double, y2: Double) {
            this.x1 = x1
            this.y1 = y1
            this.x2 = x2
            this.y2 = y2
        }

        override val bounds: Rectangle
            get() = bounds2D.bounds

        override val bounds2D: Rectangle2D
            get() {
                val x: Float
                val y: Float
                val w: Float
                val h: Float
                if (x1 < x2) {
                    x = x1.toFloat()
                    w = (x2 - x1).toFloat()
                } else {
                    x = x2.toFloat()
                    w = (x1 - x2).toFloat()
                }
                if (y1 < y2) {
                    y = y1.toFloat()
                    h = (y2 - y1).toFloat()
                } else {
                    y = y2.toFloat()
                    h = (y1 - y2).toFloat()
                }
                return Rectangle2DFloat(x, y, w, h)
            }

        /** Sets the location of the end points of this `Line2D`
         * to the specified float coordinates.
         * @param x1 the X coordinate of the start point
         * @param y1 the Y coordinate of the start point
         * @param x2 the X coordinate of the end point
         * @param y2 the Y coordinate of the end point
         */
        fun setLine(x1: Float, y1: Float, x2: Float, y2: Float) {
            this.x1 = x1.toDouble()
            this.y1 = y1.toDouble()
            this.x2 = x2.toDouble()
            this.y2 = y2.toDouble()
        }

        companion object {
            /** Use serialVersionUID from JDK 1.6 for interoperability.  */
            @Serial
            private val serialVersionUID = 6161772511649436349L
        }
    }

    /** A line segment specified with double coordinates.  */
    class Line2DDouble : Line2D, Serializable {

        /** {@inheritDoc}  */
        /** The X coordinate of the start point of the line segment.  */
        override var x1: Double = 0.0
        /** {@inheritDoc}  */
        /** The Y coordinate of the start point of the line segment.  */
        override var y1: Double = 0.0

        /** {@inheritDoc}  */
        /** The X coordinate of the end point of the line segment.  */
        override var x2: Double = 0.0
        /** {@inheritDoc}  */
        /** The Y coordinate of the end point of the line segment.  */
        override var y2: Double = 0.0

        override val p1: Point2D
            get() = Point2DDouble(x1, y1)
        override val p2: Point2D
            get() = Point2DDouble(x2, y2)

        /** Constructs and initializes a Line with coordinates (0, 0)  (0, 0).  */
        constructor()

        /** Constructs and initializes a `Line2D` from the
         * specified coordinates.
         * @param x1 the X coordinate of the start point
         * @param y1 the Y coordinate of the start point
         * @param x2 the X coordinate of the end point
         * @param y2 the Y coordinate of the end point
         */
        constructor(x1: Double, y1: Double, x2: Double, y2: Double) {
            setLine(x1, y1, x2, y2)
        }

        /** Constructs and initializes a `Line2D` from the
         * specified `Point2D` objects.
         * @param p1 the start `Point2D` of this line segment
         * @param p2 the end `Point2D` of this line segment
         */
        constructor(p1: Point2D, p2: Point2D) {
            setLine(p1, p2)
        }

        /** {@inheritDoc}  */
        override fun setLine(x1: Double, y1: Double, x2: Double, y2: Double) {
            this.x1 = x1
            this.y1 = y1
            this.x2 = x2
            this.y2 = y2
        }

        override val bounds: Rectangle
            get() = TODO("Not yet implemented")
        override val bounds2D: Rectangle2D
            get() {
                val x: Double
                val y: Double
                val w: Double
                val h: Double
                if (x1 < x2) {
                    x = x1
                    w = x2 - x1
                } else {
                    x = x2
                    w = x1 - x2
                }
                if (y1 < y2) {
                    y = y1
                    h = y2 - y1
                } else {
                    y = y2
                    h = y1 - y2
                }
                return Rectangle2DDouble(x, y, w, h)
            }

        companion object {
            /** Use serialVersionUID from JDK 1.6 for interoperability.  */
            @Serial
            private val serialVersionUID = 7979627399746467499L
        }
    }

    /** Returns the X coordinate of the start point in double precision.
     * @return the X coordinate of the start point of this
     * `Line2D` object.
     */
    abstract val x1: Double

    /** Returns the Y coordinate of the start point in double precision.
     * @return the Y coordinate of the start point of this
     * `Line2D` object.
     */
    abstract val y1: Double

    /** Returns the start `Point2D` of this `Line2D`.
     * @return the start `Point2D` of this `Line2D`.
     */
    abstract val p1: Point2D

    /** Returns the X coordinate of the end point in double precision.
     * @return the X coordinate of the end point of this
     * `Line2D` object.
     */
    abstract val x2: Double

    /** Returns the Y coordinate of the end point in double precision.
     * @return the Y coordinate of the end point of this
     * `Line2D` object.
     */
    abstract val y2: Double

    /** Returns the end `Point2D` of this `Line2D`.
     * @return the end `Point2D` of this `Line2D`.
     */
    abstract val p2: Point2D

    /** Sets the location of the end points of this `Line2D` to
     * the specified double coordinates.
     * @param x1 the X coordinate of the start point
     * @param y1 the Y coordinate of the start point
     * @param x2 the X coordinate of the end point
     * @param y2 the Y coordinate of the end point
     */
    abstract fun setLine(x1: Double, y1: Double, x2: Double, y2: Double)

    /** Sets the location of the end points of this `Line2D` to
     * the specified `Point2D` coordinates.
     * @param p1 the start `Point2D` of the line segment
     * @param p2 the end `Point2D` of the line segment
     */
    fun setLine(p1: Point2D, p2: Point2D) {
        setLine(p1.x, p1.y, p2.x, p2.y)
    }

    /** Sets the location of the end points of this `Line2D` to
     * the same as those end points of the specified `Line2D`.
     * @param l the specified `Line2D`
     */
    fun setLine(l: Line2D) {
        setLine(l.x1, l.y1, l.x2, l.y2)
    }

    /** Returns an indicator of where the specified point
     * `(px,py)` lies with respect to this line segment.
     * See the method comments of
     * [.relativeCCW]
     * to interpret the return value.
     * @param px the X coordinate of the specified point
     * to be compared with this `Line2D`
     * @param py the Y coordinate of the specified point
     * to be compared with this `Line2D`
     * @return an integer that indicates the position of the specified
     * coordinates with respect to this `Line2D`
     * @see .relativeCCW
     */
    fun relativeCCW(px: Double, py: Double): Int {
        return relativeCCW(
            x1, y1, x2, y2, px, py
        )
    }

    /** Returns an indicator of where the specified `Point2D`
     * lies with respect to this line segment. See the method comments of
     * [.relativeCCW]
     * to interpret the return value.
     * @param p the specified `Point2D` to be compared with this `Line2D`
     * @return an integer that indicates the position of the specified
     * `Point2D` with respect to this `Line2D`
     * @see .relativeCCW
     */
    fun relativeCCW(p: Point2D): Int {
        return relativeCCW(
            x1, y1, x2, y2,
            p.x, p.y
        )
    }

    /**
     * Tests if the line segment from `(x1,y1)` to
     * `(x2,y2)` intersects this line segment.
     * @param x1 the X coordinate of the start point of the specified line segment
     * @param y1 the Y coordinate of the start point of the specified line segment
     * @param x2 the X coordinate of the end point of the specified line segment
     * @param y2 the Y coordinate of the end point of the specified line segment
     * @return `true` if this line segment and the specified line segment
     * intersect each other; `false` otherwise.
     */
    fun intersectsLine(x1: Double, y1: Double, x2: Double, y2: Double): Boolean {
        return linesIntersect(
            x1, y1, x2, y2,
            this.x1, this.y1, this.x2, this.y2
        )
    }

    /** Tests if the specified line segment intersects this line segment.
     * @param l the specified `Line2D`
     * @return `true` if this line segment and the specified line
     * segment intersect each other; `false` otherwise.
     */
    fun intersectsLine(l: Line2D): Boolean {
        return linesIntersect(
            l.x1, l.y1, l.x2, l.y2,
            x1, y1, x2, y2
        )
    }

    /** Returns the square of the distance from a point to this line segment.
     * The distance measured is the distance between the specified
     * point and the closest point between the current line's end points.
     * If the specified point intersects the line segment in between the
     * end points, this method returns 0.0.
     * @param px the X coordinate of the specified point being
     * measured against this line segment
     * @param py the Y coordinate of the specified point being
     * measured against this line segment
     * @return a double value that is the square of the distance from the
     * specified point to the current line segment.
     * @see .ptLineDistSq
     */
    fun ptSegDistSq(px: Double, py: Double): Double {
        return ptSegDistSq(
            x1, y1, x2, y2, px, py
        )
    }

    /** Returns the square of the distance from a `Point2D` to
     * this line segment.
     * The distance measured is the distance between the specified
     * point and the closest point between the current line's end points.
     * If the specified point intersects the line segment in between the
     * end points, this method returns 0.0.
     * @param pt the specified `Point2D` being measured against
     * this line segment.
     * @return a double value that is the square of the distance from the
     * specified `Point2D` to the current
     * line segment.
     * @see .ptLineDistSq
     */
    fun ptSegDistSq(pt: Point2D): Double {
        return ptSegDistSq(
            x1, y1, x2, y2,
            pt.x, pt.y
        )
    }

    /** Returns the distance from a point to this line segment.
     * The distance measured is the distance between the specified
     * point and the closest point between the current line's end points.
     * If the specified point intersects the line segment in between the
     * end points, this method returns 0.0.
     * @param px the X coordinate of the specified point being measured against this line segment
     * @param py the Y coordinate of the specified point being measured against this line segment
     * @return a double value that is the distance from the specified
     * point to the current line segment.
     * @see .ptLineDist
     */
    fun ptSegDist(px: Double, py: Double): Double {
        return ptSegDist(
            x1, y1, x2, y2, px, py
        )
    }

    /** Returns the distance from a `Point2D` to this line segment.
     * The distance measured is the distance between the specified
     * point and the closest point between the current line's end points.
     * If the specified point intersects the line segment in between the
     * end points, this method returns 0.0.
     * @param pt the specified `Point2D` being measured against this line segment
     * @return a double value that is the distance from the specified
     * `Point2D` to the current line segment.
     * @see .ptLineDist
     */
    fun ptSegDist(pt: Point2D): Double {
        return ptSegDist(
            x1, y1, x2, y2,
            pt.x, pt.y
        )
    }

    /** Returns the square of the distance from a point to this line.
     * The distance measured is the distance between the specified
     * point and the closest point on the infinitely-extended line
     * defined by this `Line2D`.  If the specified point
     * intersects the line, this method returns 0.0.
     * @param px the X coordinate of the specified point being measured against this line
     * @param py the Y coordinate of the specified point being measured against this line
     * @return a double value that is the square of the distance from a
     * specified point to the current line.
     * @see .ptSegDistSq
     */
    fun ptLineDistSq(px: Double, py: Double): Double {
        return ptLineDistSq(
            x1, y1, x2, y2, px, py
        )
    }

    /** Returns the square of the distance from a specified
     * `Point2D` to this line.
     * The distance measured is the distance between the specified
     * point and the closest point on the infinitely-extended line
     * defined by this `Line2D`.  If the specified point
     * intersects the line, this method returns 0.0.
     * @param pt the specified `Point2D` being measured against this line
     * @return a double value that is the square of the distance from a
     * specified `Point2D` to the current line.
     * @see .ptSegDistSq
     */
    fun ptLineDistSq(pt: Point2D): Double {
        return ptLineDistSq(
            x1, y1, x2, y2,
            pt.x, pt.y
        )
    }

    /** Returns the distance from a point to this line.
     * The distance measured is the distance between the specified
     * point and the closest point on the infinitely-extended line
     * defined by this `Line2D`.  If the specified point
     * intersects the line, this method returns 0.0.
     * @param px the X coordinate of the specified point being measured against this line
     * @param py the Y coordinate of the specified point being measured against this line
     * @return a double value that is the distance from a specified point to the current line.
     * @see .ptSegDist
     */
    fun ptLineDist(px: Double, py: Double): Double {
        return ptLineDist(
            x1, y1, x2, y2, px, py
        )
    }

    /**
     * Returns the distance from a `Point2D` to this line.
     * The distance measured is the distance between the specified
     * point and the closest point on the infinitely-extended line
     * defined by this `Line2D`.  If the specified point
     * intersects the line, this method returns 0.0.
     *
     * @param pt the specified `Point2D` being measured
     * @return a double value that is the distance from a specified
     * `Point2D` to the current line.
     * @see .ptSegDist
     * @since 1.2
     */
    fun ptLineDist(pt: Point2D): Double {
        return ptLineDist(
            x1, y1, x2, y2,
            pt.x, pt.y
        )
    }

    /**
     * Tests if a specified coordinate is inside the boundary of this
     * `Line2D`.  This method is required to implement the
     * [Shape] interface, but in the case of `Line2D`
     * objects it always returns `false` since a line contains
     * no area.
     *
     * @param x the X coordinate of the specified point to be tested
     * @param y the Y coordinate of the specified point to be tested
     * @return `false` because a `Line2D` contains
     * no area.
     * @since 1.2
     */
    override fun contains(x: Double, y: Double): Boolean {
        return false
    }

    /**
     * Tests if a given `Point2D` is inside the boundary of
     * this `Line2D`.
     * This method is required to implement the [Shape] interface,
     * but in the case of `Line2D` objects it always returns
     * `false` since a line contains no area.
     *
     * @param p the specified `Point2D` to be tested
     * @return `false` because a `Line2D` contains
     * no area.
     * @since 1.2
     */
    override fun contains(p: Point2D): Boolean {
        return false
    }

    /**
     * {@inheritDoc}
     *
     * @since 1.2
     */
    override fun intersects(x: Double, y: Double, w: Double, h: Double): Boolean {
        return intersects(Rectangle2DDouble(x, y, w, h))
    }

    /**
     * {@inheritDoc}
     *
     * @since 1.2
     */
    override fun intersects(r: Rectangle2D): Boolean {
        return r.intersectsLine(x1, y1, x2, y2)
    }

    /**
     * Tests if the interior of this `Line2D` entirely contains
     * the specified set of rectangular coordinates.
     * This method is required to implement the `Shape` interface,
     * but in the case of `Line2D` objects it always returns
     * false since a line contains no area.
     *
     * @param x the X coordinate of the upper-left corner of the
     * specified rectangular area
     * @param y the Y coordinate of the upper-left corner of the
     * specified rectangular area
     * @param w the width of the specified rectangular area
     * @param h the height of the specified rectangular area
     * @return `false` because a `Line2D` contains
     * no area.
     * @since 1.2
     */
    override fun contains(x: Double, y: Double, w: Double, h: Double): Boolean {
        return false
    }

    /**
     * Tests if the interior of this `Line2D` entirely contains
     * the specified `Rectangle2D`.
     * This method is required to implement the `Shape` interface,
     * but in the case of `Line2D` objects it always returns
     * `false` since a line contains no area.
     *
     * @param r the specified `Rectangle2D` to be tested
     * @return `false` because a `Line2D` contains
     * no area.
     * @since 1.2
     */
    override fun contains(r: Rectangle2D): Boolean {
        return false
    }

    /**
     * Returns an iteration object that defines the boundary of this
     * `Line2D`.
     * The iterator for this class is not multi-threaded safe,
     * which means that this `Line2D` class does not
     * guarantee that modifications to the geometry of this
     * `Line2D` object do not affect any iterations of that
     * geometry that are already in process.
     *
     * @param at the specified [AffineTransform]
     * @return a [PathIterator] that defines the boundary of this
     * `Line2D`.
     * @since 1.2
     */
    override fun getPathIterator(at: AffineTransform?): PathIterator {
        return LineIterator(
            this, at
        )
    }

    /**
     * Returns an iteration object that defines the boundary of this
     * flattened `Line2D`.
     * The iterator for this class is not multi-threaded safe,
     * which means that this `Line2D` class does not
     * guarantee that modifications to the geometry of this
     * `Line2D` object do not affect any iterations of that
     * geometry that are already in process.
     *
     * @param at       the specified `AffineTransform`
     * @param flatness the maximum amount that the control points for a
     * given curve can vary from colinear before a subdivided
     * curve is replaced by a straight line connecting the
     * end points.  Since a `Line2D` object is
     * always flat, this parameter is ignored.
     * @return a `PathIterator` that defines the boundary of the
     * flattened `Line2D`
     * @since 1.2
     */
    override fun getPathIterator(at: AffineTransform, flatness: Double): PathIterator {
        return LineIterator(
            this, at
        )
    }

    /**
     * Creates a new object of the same class as this object.
     *
     * @return a clone of this instance.
     * @throws OutOfMemoryError if there is not enough memory.
     * @see java.lang.Cloneable
     *
     * @since 1.2
     */
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
        /** Returns an indicator of where the specified point
         * `(px,py)` lies with respect to the line segment from
         * `(x1,y1)` to `(x2,y2)`.
         * The return value can be either 1, -1, or 0 and indicates
         * in which direction the specified line must pivot around its
         * first end point, `(x1,y1)`, in order to point at the
         * specified point `(px,py)`.
         *
         * A return value of 1 indicates that the line segment must
         * turn in the direction that takes the positive X axis towards
         * the negative Y axis.  In the default coordinate system used by
         * Java 2D, this direction is counterclockwise.
         *
         * A return value of -1 indicates that the line segment must
         * turn in the direction that takes the positive X axis towards
         * the positive Y axis.  In the default coordinate system, this
         * direction is clockwise.
         *
         * A return value of 0 indicates that the point lies
         * exactly on the line segment.  Note that an indicator value
         * of 0 is rare and not useful for determining collinearity
         * because of floating point rounding issues.
         *
         * If the point is colinear with the line segment, but
         * not between the end points, then the value will be -1 if the point
         * lies "beyond `(x1,y1)`" or 1 if the point lies
         * "beyond `(x2,y2)`".
         * @param x1 the X coordinate of the start point of the specified line segment
         * @param y1 the Y coordinate of the start point of the specified line segment
         * @param x2 the X coordinate of the end point of the specified line segment
         * @param y2 the Y coordinate of the end point of the specified line segment
         * @param px the X coordinate of the specified point to be
         * compared with the specified line segment
         * @param py the Y coordinate of the specified point to be
         * compared with the specified line segment
         * @return an integer that indicates the position of the third specified
         * coordinates with respect to the line segment formed
         * by the first two specified coordinates.
         */
        fun relativeCCW(
            x1: Double, y1: Double,
            x2: Double, y2: Double,
            px: Double, py: Double
        ): Int {
            var x2 = x2
            var y2 = y2
            var px = px
            var py = py
            x2 -= x1
            y2 -= y1
            px -= x1
            py -= y1
            var ccw = px * y2 - py * x2
            if (ccw == 0.0) {
                // The point is colinear, classify based on which side of
                // the segment the point falls on.  We can calculate a
                // relative value using the projection of px,py onto the
                // segment - a negative value indicates the point projects
                // outside of the segment in the direction of the particular
                // endpoint used as the origin for the projection.
                ccw = px * x2 + py * y2
                if (ccw > 0.0) {
                    // Reverse the projection to be relative to the original x2,y2
                    // x2 and y2 are simply negated.
                    // px and py need to have (x2 - x1) or (y2 - y1) subtracted
                    //    from them (based on the original values)
                    // Since we really want to get a positive answer when the
                    //    point is "beyond (x2,y2)", then we want to calculate
                    //    the inverse anyway - thus we leave x2 & y2 negated.
                    px -= x2
                    py -= y2
                    ccw = px * x2 + py * y2
                    if (ccw < 0.0) {
                        ccw = 0.0
                    }
                }
            }
            return if ((ccw < 0.0)) -1 else (if ((ccw > 0.0)) 1 else 0)
        }

        /** Tests if the line segment from `(x1,y1)` to
         * `(x2,y2)` intersects the line segment from `(x3,y3)`
         * to `(x4,y4)`.
         * @param x1 the X coordinate of the start point of the first specified line segment
         * @param y1 the Y coordinate of the start point of the first specified line segment
         * @param x2 the X coordinate of the end point of the first specified line segment
         * @param y2 the Y coordinate of the end point of the first specified line segment
         * @param x3 the X coordinate of the start point of the second specified line segment
         * @param y3 the Y coordinate of the start point of the second specified line segment
         * @param x4 the X coordinate of the end point of the second specified line segment
         * @param y4 the Y coordinate of the end point of the second specified line segment
         * @return `true` if the first specified line segment
         * and the second specified line segment intersect each other; `false` otherwise.
         */
        fun linesIntersect(
            x1: Double, y1: Double,
            x2: Double, y2: Double,
            x3: Double, y3: Double,
            x4: Double, y4: Double
        ): Boolean {
            return ((relativeCCW(x1, y1, x2, y2, x3, y3) *
                    relativeCCW(x1, y1, x2, y2, x4, y4) <= 0)
                    && (relativeCCW(x3, y3, x4, y4, x1, y1) *
                    relativeCCW(x3, y3, x4, y4, x2, y2) <= 0))
        }

        /** Returns the square of the distance from a point to a line segment.
         * The distance measured is the distance between the specified
         * point and the closest point between the specified end points.
         * If the specified point intersects the line segment in between the
         * end points, this method returns 0.0.
         * @param x1 the X coordinate of the start point of the specified line segment
         * @param y1 the Y coordinate of the start point of the specified line segment
         * @param x2 the X coordinate of the end point of the specified line segment
         * @param y2 the Y coordinate of the end point of the specified line segment
         * @param px the X coordinate of the specified point being
         * measured against the specified line segment
         * @param py the Y coordinate of the specified point being
         * measured against the specified line segment
         * @return a double value that is the square of the distance from the
         * specified point to the specified line segment.
         * @see .ptLineDistSq
         */
        @JvmStatic
        fun ptSegDistSq(
            x1: Double, y1: Double,
            x2: Double, y2: Double,
            px: Double, py: Double
        ): Double {
            // Adjust vectors relative to x1,y1
            // x2,y2 becomes relative vector from x1,y1 to end of segment
            var x2 = x2
            var y2 = y2
            var px = px
            var py = py
            x2 -= x1
            y2 -= y1
            // px,py becomes relative vector from x1,y1 to test point
            px -= x1
            py -= y1
            var dotprod = px * x2 + py * y2
            val projlenSq: Double
            if (dotprod <= 0.0) {
                // px,py is on the side of x1,y1 away from x2,y2
                // distance to segment is length of px,py vector
                // "length of its (clipped) projection" is now 0.0
                projlenSq = 0.0
            } else {
                // switch to backwards vectors relative to x2,y2
                // x2,y2 are already the negative of x1,y1=>x2,y2
                // to get px,py to be the negative of px,py=>x2,y2
                // the dot product of two negated vectors is the same
                // as the dot product of the two normal vectors
                px = x2 - px
                py = y2 - py
                dotprod = px * x2 + py * y2
                projlenSq = if (dotprod <= 0.0) {
                    // px,py is on the side of x2,y2 away from x1,y1
                    // distance to segment is length of (backwards) px,py vector
                    // "length of its (clipped) projection" is now 0.0
                    0.0
                } else {
                    // px,py is between x1,y1 and x2,y2
                    // dotprod is the length of the px,py vector
                    // projected on the x2,y2=>x1,y1 vector times the
                    // length of the x2,y2=>x1,y1 vector
                    dotprod * dotprod / (x2 * x2 + y2 * y2)
                }
            }
            // Distance to line is now the length of the relative point
            // vector minus the length of its projection onto the line
            // (which is zero if the projection falls outside the range
            //  of the line segment).
            var lenSq = px * px + py * py - projlenSq
            if (lenSq < 0) {
                lenSq = 0.0
            }
            return lenSq
        }

        /** Returns the distance from a point to a line segment.
         * The distance measured is the distance between the specified
         * point and the closest point between the specified end points.
         * If the specified point intersects the line segment in between the
         * end points, this method returns 0.0.
         * @param x1 the X coordinate of the start point of the specified line segment
         * @param y1 the Y coordinate of the start point of the specified line segment
         * @param x2 the X coordinate of the end point of the specified line segment
         * @param y2 the Y coordinate of the end point of the specified line segment
         * @param px the X coordinate of the specified point being
         * measured against the specified line segment
         * @param py the Y coordinate of the specified point being
         * measured against the specified line segment
         * @return a double value that is the distance from the specified point
         * to the specified line segment.
         * @see .ptLineDist
         */
        @JvmStatic
        fun ptSegDist(
            x1: Double, y1: Double,
            x2: Double, y2: Double,
            px: Double, py: Double
        ): Double {
            return sqrt(ptSegDistSq(x1, y1, x2, y2, px, py))
        }

        /** Returns the square of the distance from a point to a line.
         * The distance measured is the distance between the specified
         * point and the closest point on the infinitely-extended line
         * defined by the specified coordinates.  If the specified point
         * intersects the line, this method returns 0.0.
         * @param x1 the X coordinate of the start point of the specified line
         * @param y1 the Y coordinate of the start point of the specified line
         * @param x2 the X coordinate of the end point of the specified line
         * @param y2 the Y coordinate of the end point of the specified line
         * @param px the X coordinate of the specified point being
         * measured against the specified line
         * @param py the Y coordinate of the specified point being
         * measured against the specified line
         * @return a double value that is the square of the distance from the
         * specified point to the specified line.
         * @see .ptSegDistSq
         */
        fun ptLineDistSq(
            x1: Double, y1: Double,
            x2: Double, y2: Double,
            px: Double, py: Double
        ): Double {
            // Adjust vectors relative to x1,y1
            // x2,y2 becomes relative vector from x1,y1 to end of segment
            var x2 = x2
            var y2 = y2
            var px = px
            var py = py
            x2 -= x1
            y2 -= y1
            // px,py becomes relative vector from x1,y1 to test point
            px -= x1
            py -= y1
            val dotprod = px * x2 + py * y2
            // dotprod is the length of the px,py vector
            // projected on the x1,y1=>x2,y2 vector times the
            // length of the x1,y1=>x2,y2 vector
            val projlenSq = dotprod * dotprod / (x2 * x2 + y2 * y2)
            // Distance to line is now the length of the relative point
            // vector minus the length of its projection onto the line
            var lenSq = px * px + py * py - projlenSq
            if (lenSq < 0) {
                lenSq = 0.0
            }
            return lenSq
        }

        /** Returns the distance from a point to a line.
         * The distance measured is the distance between the specified
         * point and the closest point on the infinitely-extended line
         * defined by the specified coordinates.  If the specified point
         * intersects the line, this method returns 0.0.
         * @param x1 the X coordinate of the start point of the specified line
         * @param y1 the Y coordinate of the start point of the specified line
         * @param x2 the X coordinate of the end point of the specified line
         * @param y2 the Y coordinate of the end point of the specified line
         * @param px the X coordinate of the specified point being
         * measured against the specified line
         * @param py the Y coordinate of the specified point being
         * measured against the specified line
         * @return a double value that is the distance from the specified point to the specified line.
         * @see .ptSegDist
         */
        fun ptLineDist(
            x1: Double, y1: Double,
            x2: Double, y2: Double,
            px: Double, py: Double
        ): Double {
            return sqrt(ptLineDistSq(x1, y1, x2, y2, px, py))
        }
    }
}
