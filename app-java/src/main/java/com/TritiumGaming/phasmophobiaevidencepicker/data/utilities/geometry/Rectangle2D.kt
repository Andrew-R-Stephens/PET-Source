package com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.geometry

import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.geometry.Rectangle2D.Rectangle2DDouble
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.geometry.Rectangle2D.Rectangle2DFloat
import java.io.Serializable
import kotlin.math.max
import kotlin.math.min

/** The `Rectangle2D` class describes a rectangle
 * defined by a location `(x,y)` and dimension `(w x h)`.
 *
 *
 * This class is only the abstract superclass for all objects that store a 2D rectangle.
 * The actual storage representation of the coordinates is left to the subclass.  */
abstract class Rectangle2D

/** This is an abstract class that cannot be instantiated directly.
 * Type-specific implementation subclasses are available for
 * instantiation and provide a number of formats for storing
 * the information necessary to satisfy the various accessor
 * methods below.
 * @see Rectangle2DFloat
 *
 * @see Rectangle2DDouble
 *
 * @see Rectangle
 */
protected constructor() : RectangularShape() {
    /** The `Float` class defines a rectangle specified in float coordinates.  */
    class Rectangle2DFloat : Rectangle2D, Serializable {

        /** The X coordinate of this `Rectangle2D`.  */
        override var x: Double = 0.0
        /** The Y coordinate of this `Rectangle2D`. */
        override var y: Double = 0.0

        /** The width of this `Rectangle2D`.  */
        override var width: Double = 0.0
        /** The height of this `Rectangle2D`.  */
        override var height: Double = 0.0

        override var isEmpty = (width <= 0.0f) || (height <= 0.0f)
        override val bounds: Rectangle
            get() = Rectangle(x, y, width, height)
        override val bounds2D: Rectangle2D
            get() = Rectangle2DFloat(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat())

        /** Constructs a new `Rectangle2D`, initialized to
         * location (0.0,&nbsp;0.0) and size (0.0,&nbsp;0.0).  */
        constructor()

        /** Constructs and initializes a `Rectangle2D`
         * from the specified `float` coordinates.
         * @param x the X coordinate of the upper-left corner
         * of the newly constructed `Rectangle2D`
         * @param y the Y coordinate of the upper-left corner
         * of the newly constructed `Rectangle2D`
         * @param w the width of the newly constructed
         * `Rectangle2D`
         * @param h the height of the newly constructed
         * `Rectangle2D`
         */
        constructor(x: Float, y: Float, w: Float, h: Float) {
            setRect(x, y, w, h)
        }

        /** Sets the location and size of this `Rectangle2D`
         * to the specified `float` values.
         * @param x the X coordinate of the upper-left corner
         * of this `Rectangle2D`
         * @param y the Y coordinate of the upper-left corner
         * of this `Rectangle2D`
         * @param w the width of this `Rectangle2D`
         * @param h the height of this `Rectangle2D`
         */
        fun setRect(x: Float, y: Float, w: Float, h: Float) {
            this.x = x.toDouble()
            this.y = y.toDouble()
            this.width = w.toDouble()
            this.height = h.toDouble()
        }

        /** {@inheritDoc}  */
        override fun setRect(x: Double, y: Double, w: Double, h: Double) {
            this.x = x
            this.y = y
            this.width = w
            this.height = h
        }

        /** {@inheritDoc}  */
        override fun setRect(r: Rectangle2D) {
            this.x = r.x
            this.y = r.y
            this.width = r.width
            this.height = r.height
        }

        /** {@inheritDoc}  */
        override fun outcode(x: Double, y: Double): Int {
            /*
             * Note on casts to double below.  If the arithmetic of
             * x+w or y+h is done in float, then some bits may be
             * lost if the binary exponents of x/y and w/h are not
             * similar.  By converting to double before the addition
             * we force the addition to be carried out in double to
             * avoid rounding error in the comparison.
             *
             * See bug 4320890 for problems that this inaccuracy causes.
             */
            var out = 0
            if (this.width <= 0) {
                out = out or (OUT_LEFT or OUT_RIGHT)
            } else if (x < this.x) {
                out = out or OUT_LEFT
            } else if (x > this.x + width) {
                out = out or OUT_RIGHT
            }
            if (this.height <= 0) {
                out = out or (OUT_TOP or OUT_BOTTOM)
            } else if (y < this.y) {
                out = out or OUT_TOP
            } else if (y > this.y + height) {
                out = out or OUT_BOTTOM
            }
            return out
        }

        /** {@inheritDoc}  */
        override fun createIntersection(r: Rectangle2D): Rectangle2D {
            val dest: Rectangle2D =
                if (r is Rectangle2DFloat) {
                    Rectangle2DFloat()
                } else {
                    Rectangle2DDouble()
                }
            intersect(
                this, r, dest
            )
            return dest
        }

        /** {@inheritDoc}  */
        override fun createUnion(r: Rectangle2D): Rectangle2D {
            val dest: Rectangle2D =
                if (r is Rectangle2DFloat) {
                    Rectangle2DFloat()
                } else {
                    Rectangle2DDouble()
                }
            union(
                this, r, dest
            )
            return dest
        }

        /** Returns the `String` representation of this
         * `Rectangle2D`.
         * @return a `String` representing this
         * `Rectangle2D`.
         */
        override fun toString(): String {
            return (javaClass.name
                    + "[x=" + x + ",y=" + y + ",w=" + width + ",h=" + height + "]")
        }

        companion object {
            /** Use serialVersionUID from JDK 1.6 for interoperability.  */
            private const val serialVersionUID = 3798716824173675777L
        }
    }

    /** The `Double` class defines a rectangle specified in double coordinates.  */
    class Rectangle2DDouble : Rectangle2D, Serializable {
        /** {@inheritDoc}  */
        /** The X coordinate of this `Rectangle2D`.  */
        override var x: Double = 0.0
        /** {@inheritDoc}  */
        /** The Y coordinate of this `Rectangle2D`.  */
        override var y: Double = 0.0

        /** {@inheritDoc}  */
        /** The width of this `Rectangle2D`.  */
        override var width: Double = 0.0
        /** {@inheritDoc}  */
        /** The height of this `Rectangle2D`.  */
        override var height: Double = 0.0

        /** {@inheritDoc}  */
        override var isEmpty = (width <= 0.0) || (height <= 0.0)
        override val bounds: Rectangle
            get() = Rectangle(x, y, width, height)
        override val bounds2D: Rectangle2D
            get() = Rectangle2DDouble(x, y, width, height)

        /** Constructs a new `Rectangle2D`, initialized to
         * location (0,&nbsp;0) and size (0,&nbsp;0).  */
        constructor()

        /** Constructs and initializes a `Rectangle2D`
         * from the specified `double` coordinates.
         * @param x the X coordinate of the upper-left corner
         * of the newly constructed `Rectangle2D`
         * @param y the Y coordinate of the upper-left corner
         * of the newly constructed `Rectangle2D`
         * @param w the width of the newly constructed
         * `Rectangle2D`
         * @param h the height of the newly constructed
         * `Rectangle2D`
         */
        constructor(x: Double, y: Double, w: Double, h: Double) {
            setRect(x, y, w, h)
        }

        /** {@inheritDoc}  */
        override fun setRect(x: Double, y: Double, w: Double, h: Double) {
            this.x = x
            this.y = y
            this.width = w
            this.height = h
        }

        /** {@inheritDoc}  */
        override fun setRect(r: Rectangle2D) {
            this.x = r.x
            this.y = r.y
            this.width = r.width
            this.height = r.height
        }

        /** {@inheritDoc}  */
        override fun outcode(x: Double, y: Double): Int {
            var out = 0
            if (this.width <= 0) {
                out = out or (OUT_LEFT or OUT_RIGHT)
            } else if (x < this.x) {
                out = out or OUT_LEFT
            } else if (x > this.x + this.width) {
                out = out or OUT_RIGHT
            }
            if (this.height <= 0) {
                out = out or (OUT_TOP or OUT_BOTTOM)
            } else if (y < this.y) {
                out = out or OUT_TOP
            } else if (y > this.y + this.height) {
                out = out or OUT_BOTTOM
            }
            return out
        }

        /** {@inheritDoc}  */
        override fun createIntersection(r: Rectangle2D): Rectangle2D {
            val dest: Rectangle2D = Rectangle2DDouble()
            intersect(
                this, r, dest
            )
            return dest
        }

        /** {@inheritDoc}  */
        override fun createUnion(r: Rectangle2D): Rectangle2D {
            val dest: Rectangle2D = Rectangle2DDouble()
            union(
                this, r, dest
            )
            return dest
        }

        /** Returns the `String` representation of this
         * `Rectangle2D`.
         * @return a `String` representing this
         * `Rectangle2D`.
         */
        override fun toString(): String {
            return (javaClass.name
                    + "[x=" + x +
                    ",y=" + y +
                    ",w=" + width +
                    ",h=" + height + "]")
        }

        companion object {
            /** Use serialVersionUID from JDK 1.6 for interoperability.  */
            private const val serialVersionUID = 7771313791441850493L
        }
    }

    /** Sets the location and size of this `Rectangle2D`
     * to the specified `double` values.
     * @param x the X coordinate of the upper-left corner of this `Rectangle2D`
     * @param y the Y coordinate of the upper-left corner of this `Rectangle2D`
     * @param w the width of this `Rectangle2D`
     * @param h the height of this `Rectangle2D`
     */
    abstract fun setRect(x: Double, y: Double, w: Double, h: Double)

    /** Sets this `Rectangle2D` to be the same as the specified `Rectangle2D`.
     * @param r the specified `Rectangle2D`
     */
    open fun setRect(r: Rectangle2D) {
        setRect(r.x, r.y, r.width, r.height)
    }

    /** Tests if the specified line segment intersects the interior of this
     * `Rectangle2D`.
     * @param x1 the X coordinate of the start point of the specified line segment
     * @param y1 the Y coordinate of the start point of the specified line segment
     * @param x2 the X coordinate of the end point of the specified line segment
     * @param y2 the Y coordinate of the end point of the specified line segment
     * @return `true` if the specified line segment intersects
     * the interior of this `Rectangle2D`; `false` otherwise.
     */
    fun intersectsLine(x1: Double, y1: Double, x2: Double, y2: Double): Boolean {
        var x1 = x1
        var y1 = y1
        var out1: Int
        var out2: Int
        if ((outcode(x2, y2).also { out2 = it }) == 0) {
            return true
        }
        while ((outcode(x1, y1).also { out1 = it }) != 0) {
            if ((out1 and out2) != 0) {
                return false
            }
            if ((out1 and (OUT_LEFT or OUT_RIGHT)) != 0) {
                var x = x
                if ((out1 and OUT_RIGHT) != 0) {
                    x += width
                }
                y1 = y1 + (x - x1) * (y2 - y1) / (x2 - x1)
                x1 = x
            } else {
                var y = y
                if ((out1 and OUT_BOTTOM) != 0) {
                    y += height
                }
                x1 = x1 + (y - y1) * (x2 - x1) / (y2 - y1)
                y1 = y
            }
        }
        return true
    }

    /** Tests if the specified line segment intersects the interior of this `Rectangle2D`.
     * @param l the specified [Line2D] to test for intersection
     * with the interior of this `Rectangle2D`
     * @return `true` if the specified `Line2D`
     * intersects the interior of this `Rectangle2D`; `false` otherwise.
     */
    fun intersectsLine(l: Line2D): Boolean {
        return intersectsLine(l.x1, l.y1, l.x2, l.y2)
    }

    /** Determines where the specified coordinates lie with respect to this `Rectangle2D`.
     * This method computes a binary OR of the appropriate mask values
     * indicating, for each side of this `Rectangle2D`,
     * whether or not the specified coordinates are on the same side
     * of the edge as the rest of this `Rectangle2D`.
     * @param x the specified X coordinate
     * @param y the specified Y coordinate
     * @return the logical OR of all appropriate out codes.
     * @see .OUT_LEFT
     *
     * @see .OUT_TOP
     *
     * @see .OUT_RIGHT
     *
     * @see .OUT_BOTTOM
     */
    abstract fun outcode(x: Double, y: Double): Int

    /** Determines where the specified [Point2D] lies with
     * respect to this `Rectangle2D`.
     * This method computes a binary OR of the appropriate mask values
     * indicating, for each side of this `Rectangle2D`,
     * whether or not the specified `Point2D` is on the same
     * side of the edge as the rest of this `Rectangle2D`.
     * @param p the specified `Point2D`
     * @return the logical OR of all appropriate out codes.
     * @see .OUT_LEFT
     *
     * @see .OUT_TOP
     *
     * @see .OUT_RIGHT
     *
     * @see .OUT_BOTTOM
     */
    fun outcode(p: Point2D): Int {
        return outcode(p.x, p.y)
    }

    /** Sets the location and size of the outer bounds of this
     * `Rectangle2D` to the specified rectangular values.
     * @param x the X coordinate of the upper-left corner of this `Rectangle2D`
     * @param y the Y coordinate of the upper-left corner of this `Rectangle2D`
     * @param w the width of this `Rectangle2D`
     * @param h the height of this `Rectangle2D`
     */
    override fun setFrame(x: Double, y: Double, w: Double, h: Double) {
        setRect(x, y, w, h)
    }

    /** {@inheritDoc}  */
    override val bounds2D: Rectangle2D
        get() = super.bounds2D

    /** {@inheritDoc}  */
    override fun contains(x: Double, y: Double): Boolean {
        val x0 = x
        val y0 = y
        return (x >= x0 && y >= y0 && x < x0 + width && y < y0 + height)
    }

    /** {@inheritDoc}  */
    override fun intersects(x: Double, y: Double, w: Double, h: Double): Boolean {
        if (isEmpty || w <= 0 || h <= 0) {
            return false
        }
        val x0 = x
        val y0 = y
        return (x + w > x0 && y + h > y0 && x < x0 + width && y < y0 + height)
    }

    /** {@inheritDoc}  */
    override fun contains(x: Double, y: Double, w: Double, h: Double): Boolean {
        if (isEmpty || w <= 0 || h <= 0) {
            return false
        }
        val x0 = x
        val y0 = y
        return (x >= x0 && y >= y0 && (x + w) <= x0 + width && (y + h) <= y0 + height)
    }

    /** Returns a new `Rectangle2D` object representing the
     * intersection of this `Rectangle2D` with the specified `Rectangle2D`.
     * @param r the `Rectangle2D` to be intersected with this `Rectangle2D`
     * @return the largest `Rectangle2D` contained in both
     * the specified `Rectangle2D` and in this `Rectangle2D`.
     */
    abstract fun createIntersection(r: Rectangle2D): Rectangle2D

    /** Returns a new `Rectangle2D` object representing the
     * union of this `Rectangle2D` with the specified `Rectangle2D`.
     * @param r the `Rectangle2D` to be combined with this `Rectangle2D`
     * @return the smallest `Rectangle2D` containing both
     * the specified `Rectangle2D` and this `Rectangle2D`.
     */
    abstract fun createUnion(r: Rectangle2D): Rectangle2D

    /** Adds a point, specified by the double precision arguments
     * `newx` and `newy`, to this
     * `Rectangle2D`.  The resulting `Rectangle2D`
     * is the smallest `Rectangle2D` that
     * contains both the original `Rectangle2D` and the specified point.
     *
     *
     * After adding a point, a call to `contains` with the
     * added point as an argument does not necessarily return
     * `true`. The `contains` method does not
     * return `true` for points on the right or bottom
     * edges of a rectangle. Therefore, if the added point falls on
     * the right or bottom edge of the enlarged rectangle,
     * `contains` returns `false` for that point.
     * @param newx the X coordinate of the new point
     * @param newy the Y coordinate of the new point
     */
    fun add(newx: Double, newy: Double) {
        val x1 = min(minX, newx)
        val x2 = max(maxX, newx)
        val y1 = min(minY, newy)
        val y2 = max(maxY, newy)
        setRect(x1, y1, x2 - x1, y2 - y1)
    }

    /** Adds the `Point2D` object `pt` to this `Rectangle2D`.
     * The resulting `Rectangle2D` is the smallest
     * `Rectangle2D` that contains both the original
     * `Rectangle2D` and the specified `Point2D`.
     *
     *
     * After adding a point, a call to `contains` with the
     * added point as an argument does not necessarily return `true`. The `contains`
     * method does not return `true` for points on the right
     * or bottom edges of a rectangle. Therefore, if the added point falls
     * on the right or bottom edge of the enlarged rectangle,
     * `contains` returns `false` for that point.
     * @param pt the new `Point2D` to add to this `Rectangle2D`.
     */
    fun add(pt: Point2D) {
        add(pt.x, pt.y)
    }

    /** Adds a `Rectangle2D` object to this `Rectangle2D`.
     * The resulting `Rectangle2D` is the union of the two `Rectangle2D` objects.
     * @param r the `Rectangle2D` to add to this `Rectangle2D`.
     */
    fun add(r: Rectangle2D) {
        val x1 = min(minX, r.minX)
        val x2 = max(maxX, r.maxX)
        val y1 = min(minY, r.minY)
        val y2 = max(maxY, r.maxY)
        setRect(x1, y1, x2 - x1, y2 - y1)
    }

    /** Returns an iteration object that defines the boundary of this `Rectangle2D`.
     * The iterator for this class is multi-threaded safe, which means
     * that this `Rectangle2D` class guarantees that
     * modifications to the geometry of this `Rectangle2D`
     * object do not affect any iterations of that geometry that are already in process.
     * @param at an optional `AffineTransform` to be applied to
     * the coordinates as they are returned in the iteration, or
     * `null` if untransformed coordinates are desired
     * @return the `PathIterator` object that returns the
     * geometry of the outline of this `Rectangle2D`, one segment at a time.
     */
    override fun getPathIterator(at: AffineTransform?): PathIterator {
        return RectIterator(
            this, at
        )
    }

    /** Returns an iteration object that defines the boundary of the
     * flattened `Rectangle2D`.  Since rectangles are already
     * flat, the `flatness` parameter is ignored.
     * The iterator for this class is multi-threaded safe, which means
     * that this `Rectangle2D` class guarantees that
     * modifications to the geometry of this `Rectangle2D`
     * object do not affect any iterations of that geometry that are already in process.
     * @param at       an optional `AffineTransform` to be applied to
     * the coordinates as they are returned in the iteration, or
     * `null` if untransformed coordinates are desired
     * @param flatness the maximum distance that the line segments used to
     * approximate the curved segments are allowed to deviate from any
     * point on the original curve.  Since rectangles are already flat,
     * the `flatness` parameter is ignored.
     * @return the `PathIterator` object that returns the
     * geometry of the outline of this `Rectangle2D`, one segment at a time.
     */
    override fun getPathIterator(at: AffineTransform, flatness: Double): PathIterator {
        return RectIterator(
            this, at
        )
    }

    /** Returns the hashcode for this `Rectangle2D`.
     * @return the hashcode for this `Rectangle2D`.
     */
    override fun hashCode(): Int {
        var bits = java.lang.Double.doubleToLongBits(x)
        bits += java.lang.Double.doubleToLongBits(y) * 37
        bits += java.lang.Double.doubleToLongBits(width) * 43
        bits += java.lang.Double.doubleToLongBits(height) * 47
        return ((bits.toInt()) xor ((bits shr 32).toInt()))
    }

    /** Determines whether or not the specified `Object` is
     * equal to this `Rectangle2D`. The specified
     * `Object` is equal to this `Rectangle2D`
     * if it is an instance of `Rectangle2D` and if its
     * location and size are the same as this `Rectangle2D`.
     * @param obj an `Object` to be compared with this `Rectangle2D`.
     * @return `true` if `obj` is an instance
     * of `Rectangle2D` and has the same values; `false` otherwise.
     */
    override fun equals(obj: Any?): Boolean {
        if (obj === this) {
            return true
        }
        if (obj is Rectangle2D) {
            return ((x == obj.x) &&
                    (y == obj.y) &&
                    (width == obj.width) &&
                    (height == obj.height))
        }
        return false
    }

    companion object {
        /** The bitmask that indicates that a point lies to the left of this `Rectangle2D`.  */
        const val OUT_LEFT: Int = 1

        /** The bitmask that indicates that a point lies above this `Rectangle2D`.  */
        const val OUT_TOP: Int = 2

        /** The bitmask that indicates that a point lies to the right of this `Rectangle2D`.  */
        const val OUT_RIGHT: Int = 4

        /** The bitmask that indicates that a point lies below this `Rectangle2D`.  */
        const val OUT_BOTTOM: Int = 8

        /** Intersects the pair of specified source `Rectangle2D`
         * objects and puts the result into the specified destination
         * `Rectangle2D` object.  One of the source rectangles
         * can also be the destination to avoid creating a third Rectangle2D
         * object, but in this case the original points of this source
         * rectangle will be overwritten by this method.
         * @param src1 the first of a pair of `Rectangle2D`
         * objects to be intersected with each other
         * @param src2 the second of a pair of `Rectangle2D`
         * objects to be intersected with each other
         * @param dest the `Rectangle2D` that holds the
         * results of the intersection of `src1` and `src2`
         */
        @JvmStatic
        fun intersect(
            src1: Rectangle2D,
            src2: Rectangle2D,
            dest: Rectangle2D
        ) {
            val x1 = max(src1.minX, src2.minX)
            val y1 = max(src1.minY, src2.minY)
            val x2 = min(src1.maxX, src2.maxX)
            val y2 = min(src1.maxY, src2.maxY)
            dest.setFrame(x1, y1, x2 - x1, y2 - y1)
        }

        /** Unions the pair of source `Rectangle2D` objects
         * and puts the result into the specified destination
         * `Rectangle2D` object.  One of the source rectangles
         * can also be the destination to avoid creating a third Rectangle2D
         * object, but in this case the original points of this source
         * rectangle will be overwritten by this method.
         * @param src1 the first of a pair of `Rectangle2D`
         * objects to be combined with each other
         * @param src2 the second of a pair of `Rectangle2D`
         * objects to be combined with each other
         * @param dest the `Rectangle2D` that holds the
         * results of the union of `src1` and `src2`
         */
        @JvmStatic
        fun union(
            src1: Rectangle2D,
            src2: Rectangle2D,
            dest: Rectangle2D
        ) {
            val x1 = min(src1.minX, src2.minX)
            val y1 = min(src1.minY, src2.minY)
            val x2 = max(src1.maxX, src2.maxX)
            val y2 = max(src1.maxY, src2.maxY)
            dest.setFrameFromDiagonal(x1, y1, x2, y2)
        }
    }
}
