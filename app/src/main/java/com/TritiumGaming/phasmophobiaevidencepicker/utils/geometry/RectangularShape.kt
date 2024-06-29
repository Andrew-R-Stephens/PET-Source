package com.TritiumGaming.phasmophobiaevidencepicker.utils.geometry

import android.os.Build
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor

/**
 * `RectangularShape` is the base class for a number of
 * [Shape] objects whose geometry is defined by a rectangular frame.
 * This class does not directly specify any specific geometry by
 * itself, but merely provides manipulation methods inherited by
 * a whole category of `Shape` objects.
 * The manipulation methods provided by this class can be used to
 * query and modify the rectangular frame, which provides a reference
 * for the subclasses to define their geometry.
 *
 * @author Jim Graham
 * @since 1.2
 */
abstract class RectangularShape protected constructor() : Shape, Cloneable {
    /**
     * Returns the X coordinate of the upper-left corner of
     * the framing rectangle in `double` precision.
     *
     * @return the X coordinate of the upper-left corner of
     * the framing rectangle.
     * @since 1.2
     */
    abstract val x: Double

    /**
     * Returns the Y coordinate of the upper-left corner of
     * the framing rectangle in `double` precision.
     *
     * @return the Y coordinate of the upper-left corner of
     * the framing rectangle.
     * @since 1.2
     */
    abstract val y: Double

    /**
     * Returns the width of the framing rectangle in
     * `double` precision.
     *
     * @return the width of the framing rectangle.
     * @since 1.2
     */
    abstract val width: Double

    /**
     * Returns the height of the framing rectangle
     * in `double` precision.
     *
     * @return the height of the framing rectangle.
     * @since 1.2
     */
    abstract val height: Double

    val minX: Double
        /**
         * Returns the smallest X coordinate of the framing
         * rectangle of the `Shape` in `double`
         * precision.
         *
         * @return the smallest X coordinate of the framing
         * rectangle of the `Shape`.
         * @since 1.2
         */
        get() = x

    val minY: Double
        /**
         * Returns the smallest Y coordinate of the framing
         * rectangle of the `Shape` in `double`
         * precision.
         *
         * @return the smallest Y coordinate of the framing
         * rectangle of the `Shape`.
         * @since 1.2
         */
        get() = y

    val maxX: Double
        /**
         * Returns the largest X coordinate of the framing
         * rectangle of the `Shape` in `double`
         * precision.
         *
         * @return the largest X coordinate of the framing
         * rectangle of the `Shape`.
         * @since 1.2
         */
        get() = x + width

    val maxY: Double
        /**
         * Returns the largest Y coordinate of the framing
         * rectangle of the `Shape` in `double`
         * precision.
         *
         * @return the largest Y coordinate of the framing
         * rectangle of the `Shape`.
         * @since 1.2
         */
        get() = y + height

    val centerX: Double
        /**
         * Returns the X coordinate of the center of the framing
         * rectangle of the `Shape` in `double`
         * precision.
         *
         * @return the X coordinate of the center of the framing rectangle
         * of the `Shape`.
         * @since 1.2
         */
        get() = x + width / 2.0

    val centerY: Double
        /**
         * Returns the Y coordinate of the center of the framing
         * rectangle of the `Shape` in `double`
         * precision.
         *
         * @return the Y coordinate of the center of the framing rectangle
         * of the `Shape`.
         * @since 1.2
         */
        get() = y + height / 2.0

    var frame: Rectangle2D
        /**
         * Returns the framing [Rectangle2D]
         * that defines the overall shape of this object.
         *
         * @return a `Rectangle2D`, specified in
         * `double` coordinates.
         * @see .setFrame
         * @see .setFrame
         * @since 1.2
         */
        get() = Rectangle2D.Rectangle2DDouble(
            x, y, width, height
        )
        /**
         * Sets the framing rectangle of this `Shape` to
         * be the specified `Rectangle2D`.  The framing rectangle is
         * used by the subclasses of `RectangularShape` to define
         * their geometry.
         *
         * @param r the specified `Rectangle2D`
         * @see .getFrame
         *
         * @since 1.2
         */
        set(r) {
            setFrame(r.x, r.y, r.width, r.height)
        }

    /**
     * Determines whether the `RectangularShape` is empty.
     * When the `RectangularShape` is empty, it encloses no
     * area.
     *
     * @return `true` if the `RectangularShape` is empty;
     * `false` otherwise.
     * @since 1.2
     */
    abstract val isEmpty: Boolean

    /**
     * Sets the location and size of the framing rectangle of this
     * `Shape` to the specified rectangular values.
     *
     * @param x the X coordinate of the upper-left corner of the
     * specified rectangular shape
     * @param y the Y coordinate of the upper-left corner of the
     * specified rectangular shape
     * @param w the width of the specified rectangular shape
     * @param h the height of the specified rectangular shape
     * @see .getFrame
     *
     * @since 1.2
     */
    abstract fun setFrame(x: Double, y: Double, w: Double, h: Double)

    /**
     * Sets the location and size of the framing rectangle of this
     * `Shape` to the specified [Point2D] and
     * [Dimension2D], respectively.  The framing rectangle is used
     * by the subclasses of `RectangularShape` to define
     * their geometry.
     *
     * @param loc  the specified `Point2D`
     * @param size the specified `Dimension2D`
     * @see .getFrame
     *
     * @since 1.2
     */
    fun setFrame(loc: Point2D, size: Dimension2D) {
        setFrame(loc.x, loc.y, size.width, size.height)
    }

    /**
     * Sets the diagonal of the framing rectangle of this `Shape`
     * based on the two specified coordinates.  The framing rectangle is
     * used by the subclasses of `RectangularShape` to define
     * their geometry.
     *
     * @param x1 the X coordinate of the start point of the specified diagonal
     * @param y1 the Y coordinate of the start point of the specified diagonal
     * @param x2 the X coordinate of the end point of the specified diagonal
     * @param y2 the Y coordinate of the end point of the specified diagonal
     * @since 1.2
     */
    fun setFrameFromDiagonal(
        x1: Double, y1: Double,
        x2: Double, y2: Double
    ) {
        var x1 = x1
        var y1 = y1
        var x2 = x2
        var y2 = y2
        if (x2 < x1) {
            val t = x1
            x1 = x2
            x2 = t
        }
        if (y2 < y1) {
            val t = y1
            y1 = y2
            y2 = t
        }
        setFrame(x1, y1, x2 - x1, y2 - y1)
    }

    /**
     * Sets the diagonal of the framing rectangle of this `Shape`
     * based on two specified `Point2D` objects.  The framing
     * rectangle is used by the subclasses of `RectangularShape`
     * to define their geometry.
     *
     * @param p1 the start `Point2D` of the specified diagonal
     * @param p2 the end `Point2D` of the specified diagonal
     * @since 1.2
     */
    fun setFrameFromDiagonal(p1: Point2D, p2: Point2D) {
        setFrameFromDiagonal(p1.x, p1.y, p2.x, p2.y)
    }

    /**
     * Sets the framing rectangle of this `Shape`
     * based on the specified center point coordinates and corner point
     * coordinates.  The framing rectangle is used by the subclasses of
     * `RectangularShape` to define their geometry.
     *
     * @param centerX the X coordinate of the specified center point
     * @param centerY the Y coordinate of the specified center point
     * @param cornerX the X coordinate of the specified corner point
     * @param cornerY the Y coordinate of the specified corner point
     * @since 1.2
     */
    fun setFrameFromCenter(
        centerX: Double, centerY: Double,
        cornerX: Double, cornerY: Double
    ) {
        val halfW = abs(cornerX - centerX)
        val halfH = abs(cornerY - centerY)
        setFrame(centerX - halfW, centerY - halfH, halfW * 2.0, halfH * 2.0)
    }

    /**
     * Sets the framing rectangle of this `Shape` based on a
     * specified center `Point2D` and corner
     * `Point2D`.  The framing rectangle is used by the subclasses
     * of `RectangularShape` to define their geometry.
     *
     * @param center the specified center `Point2D`
     * @param corner the specified corner `Point2D`
     * @since 1.2
     */
    fun setFrameFromCenter(center: Point2D, corner: Point2D) {
        setFrameFromCenter(
            center.x, center.y,
            corner.x, corner.y
        )
    }

    /**
     * {@inheritDoc}
     *
     * @since 1.2
     */
    override fun contains(p: Point2D): Boolean {
        return contains(p.x, p.y)
    }

    /**
     * {@inheritDoc}
     *
     * @since 1.2
     */
    override fun contains(r: Rectangle2D): Boolean {
        return contains(r.x, r.y, r.width, r.height)
    }

    /**
     * {@inheritDoc}
     *
     * @since 1.2
     */
    override fun intersects(r: Rectangle2D): Boolean {
        return intersects(r.x, r.y, r.width, r.height)
    }

    override val bounds: Rectangle
        get() {
            val width = width
            val height = height
            if (width < 0 || height < 0) {
                return Rectangle()
            }
            val x = x
            val y = y
            val x1 = floor(x)
            val y1 = floor(y)
            val x2 = ceil(x + width)
            val y2 = ceil(y + height)
            return Rectangle(
                x1.toInt().toDouble(), y1.toInt().toDouble(),
                (x2 - x1).toInt().toDouble(), (y2 - y1).toInt().toDouble()
            )
        }

    override val bounds2D: Rectangle2D
        get() = Rectangle2D.Rectangle2DDouble(0.0, 0.0, 0.0, 0.0)

    /**
     * Returns an iterator object that iterates along the
     * `Shape` object's boundary and provides access to a
     * flattened view of the outline of the `Shape`
     * object's geometry.
     *
     *
     * Only SEG_MOVETO, SEG_LINETO, and SEG_CLOSE point types will
     * be returned by the iterator.
     *
     *
     * The amount of subdivision of the curved segments is controlled
     * by the `flatness` parameter, which specifies the
     * maximum distance that any point on the unflattened transformed
     * curve can deviate from the returned flattened path segments.
     * An optional [AffineTransform] can
     * be specified so that the coordinates returned in the iteration are
     * transformed accordingly.
     *
     * @param at       an optional `AffineTransform` to be applied to the
     * coordinates as they are returned in the iteration,
     * or `null` if untransformed coordinates are desired.
     * @param flatness the maximum distance that the line segments used to
     * approximate the curved segments are allowed to deviate
     * from any point on the original curve
     * @return a `PathIterator` object that provides access to
     * the `Shape` object's flattened geometry.
     * @since 1.2
     */
    override fun getPathIterator(at: AffineTransform, flatness: Double): PathIterator {
        return FlatteningPathIterator(getPathIterator(at), flatness)
    }

    /**
     * Creates a new object of the same class and with the same
     * contents as this object.
     *
     * @return a clone of this instance.
     * @throws OutOfMemoryError if there is not enough memory.
     * @see java.lang.Cloneable
     *
     * @since 1.2
     */
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
}
