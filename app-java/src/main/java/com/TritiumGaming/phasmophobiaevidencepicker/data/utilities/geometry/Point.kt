package com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.geometry

import java.io.Serializable
import kotlin.math.floor

/**
 * A point representing a location in `(x,y)` coordinate space,
 * specified in integer precision.
 *
 * @author Sami Shaio
 * @since 1.0
 */
class Point (
    override var x: Double = 0.0,
    override var y: Double = 0.0
) : Point2D(), Serializable {

    constructor(x: Int, y: Int) : this(x.toDouble(), y.toDouble())
    constructor(p: Point) : this(p.x, p.y)

    var location: Point
        /**
         * Returns the location of this point.
         * This method is included for completeness, to parallel the
         * `getLocation` method of `Component`.
         *
         * @return a copy of this point, at the same location
         * @since 1.1
         */
        get() = Point(x, y)
        /**
         * Sets the location of the point to the specified location.
         * This method is included for completeness, to parallel the
         * `setLocation` method of `Component`.
         *
         * @param p a point, the new location for this point
         * @since 1.1
         */
        set(p) {
            setLocation(p.x, p.y)
        }

    /**
     * Changes the point to have the specified location.
     *
     *
     * This method is included for completeness, to parallel the
     * `setLocation` method of `Component`.
     * Its behavior is identical with `move(int,&nbsp;int)`.
     *
     * @param x the X coordinate of the new location
     * @param y the Y coordinate of the new location
     * @since 1.1
     */
    fun setLocation(x: Int, y: Int) {
        move(x, y)
    }

    /**
     * Sets the location of this point to the specified double coordinates.
     * The double values will be rounded to integer values.
     * Any number smaller than `Integer.MIN_VALUE`
     * will be reset to `MIN_VALUE`, and any number
     * larger than `Integer.MAX_VALUE` will be
     * reset to `MAX_VALUE`.
     *
     * @param x the X coordinate of the new location
     * @param y the Y coordinate of the new location
     * @see .getLocation
     */
    override fun setLocation(x: Double, y: Double) {
        this.x = floor(x + 0.5)
        this.y = floor(y + 0.5)
    }

    /**
     * Moves this point to the specified location in the
     * `(x,y)` coordinate plane. This method
     * is identical with `setLocation(int,&nbsp;int)`.
     *
     * @param x the X coordinate of the new location
     * @param y the Y coordinate of the new location
     */
    fun move(x: Int, y: Int) {
        this.x = x.toDouble()
        this.y = y.toDouble()
    }

    /**
     * Translates this point, at location `(x,y)`,
     * by `dx` along the `x` axis and `dy`
     * along the `y` axis so that it now represents the point
     * `(x+dx,y+dy)`.
     *
     * @param dx the distance to move this point
     * along the X axis
     * @param dy the distance to move this point
     * along the Y axis
     */
    fun translate(dx: Int, dy: Int) {
        this.x += dx
        this.y += dy
    }

    /**
     * Determines whether or not two points are equal. Two instances of
     * `Point2D` are equal if the values of their
     * `x` and `y` member fields, representing
     * their position in the coordinate space, are the same.
     *
     * @param obj an object to be compared with this `Point2D`
     * @return `true` if the object to be compared is
     * an instance of `Point2D` and has
     * the same values; `false` otherwise.
     */
    override fun equals(obj: Any?): Boolean {
        if (obj is Point) {
            return (x == obj.x) && (y == obj.y)
        }
        return super.equals(obj)
    }

    /**
     * Returns a string representation of this point and its location
     * in the `(x,y)` coordinate space. This method is
     * intended to be used only for debugging purposes, and the content
     * and format of the returned string may vary between implementations.
     * The returned string may be empty but may not be `null`.
     *
     * @return a string representation of this point
     */
    override fun toString(): String {
        return javaClass.name + "[x=" + x + ",y=" + y + "]"
    }

    companion object {
        /**
         * Use serialVersionUID from JDK 1.1 for interoperability.
         */
        private const val serialVersionUID = -5276940640259749850L
    }
}
