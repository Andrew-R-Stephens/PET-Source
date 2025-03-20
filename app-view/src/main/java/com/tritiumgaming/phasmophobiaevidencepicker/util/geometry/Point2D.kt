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
package com.tritiumgaming.phasmophobiaevidencepicker.util.geometry

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import kotlin.math.sqrt

/**
 * The `Point2D` class defines a point representing a location
 * in `(x,y)` coordinate space.
 *
 *
 * This class is only the abstract superclass for all objects that
 * store a 2D coordinate.
 * The actual storage representation of the coordinates is left to
 * the subclass.
 *
 * @author Jim Graham
 * @since 1.2
 */
abstract class Point2D
/**
 * This is an abstract class that cannot be instantiated directly.
 * Type-specific implementation subclasses are available for
 * instantiation and provide a number of formats for storing
 * the information necessary to satisfy the various accessor
 * methods below.
 *
 * @see Point2DFloat
 *
 * @see Point2DDouble
 *
 * @see Point
 *
 * @since 1.2
 */
protected constructor() : Cloneable {
    /**
     * The `Float` class defines a point specified in float
     * precision.
     *
     * @since 1.2
     */
    class Point2DFloat : Point2D, Serializable {
        /**
         * The X coordinate of this `Point2D`.
         */
        override var x: Double = 0.0

        /**
         * The Y coordinate of this `Point2D`.
         */
        override var y: Double = 0.0

        /**
         * Constructs and initializes a `Point2D` with
         * coordinates (0,&nbsp;0).
         */
        constructor()

        /**
         * Constructs and initializes a `Point2D` with
         * the specified coordinates.
         *
         * @param x the X coordinate of the newly
         * constructed `Point2D`
         * @param y the Y coordinate of the newly
         * constructed `Point2D`
         * @since 1.2
         */
        constructor(x: Float, y: Float) {
            this.x = x.toDouble()
            this.y = y.toDouble()
        }

        /**
         * {@inheritDoc}
         */
        override fun setLocation(x: Double, y: Double) {
            this.x = x
            this.y = y
        }

        /**
         * Sets the location of this `Point2D` to the
         * specified `float` coordinates.
         *
         * @param x the new X coordinate of this `Point2D`
         * @param y the new Y coordinate of this `Point2D`
         * @since 1.2
         */
        fun setLocation(x: Float, y: Float) {
            this.x = x.toDouble()
            this.y = y.toDouble()
        }

        /**
         * Returns a `String` that represents the value
         * of this `Point2D`.
         *
         * @return a string representation of this `Point2D`.
         * @since 1.2
         */
        override fun toString(): String {
            return "Point2D.Float[$x, $y]"
        }

        companion object {
            /**
             * Use serialVersionUID from JDK 1.6 for interoperability.
             */
            private const val serialVersionUID = -2870572449815403710L
        }
    }

    /**
     * The `Double` class defines a point specified in
     * `double` precision.
     *
     * @since 1.2
     */
    class Point2DDouble : Point2D, Serializable {
        /**
         * {@inheritDoc}
         *
         * @since 1.2
         */
        /**
         * The X coordinate of this `Point2D`.
         *
         * @serial
         * @since 1.2
         */
        override var x: Double = 0.0

        /**
         * {@inheritDoc}
         *
         * @since 1.2
         */
        /**
         * The Y coordinate of this `Point2D`.
         *
         * @serial
         * @since 1.2
         */
        override var y: Double = 0.0

        /**
         * Constructs and initializes a `Point2D` with
         * coordinates (0,&nbsp;0).
         *
         * @since 1.2
         */
        constructor()

        /**
         * Constructs and initializes a `Point2D` with the
         * specified coordinates.
         *
         * @param x the X coordinate of the newly
         * constructed `Point2D`
         * @param y the Y coordinate of the newly
         * constructed `Point2D`
         * @since 1.2
         */
        constructor(x: Double, y: Double) {
            this.x = x
            this.y = y
        }

        /**
         * {@inheritDoc}
         *
         * @since 1.2
         */
        override fun setLocation(x: Double, y: Double) {
            this.x = x
            this.y = y
        }

        /**
         * Returns a `String` that represents the value
         * of this `Point2D`.
         *
         * @return a string representation of this `Point2D`.
         * @since 1.2
         */
        override fun toString(): String {
            return "Point2D.Double[$x, $y]"
        }

        companion object {
            /**
             * Use serialVersionUID from JDK 1.6 for interoperability.
             */
            private const val serialVersionUID = 6150783262733311327L
        }
    }

    /**
     * Returns the X coordinate of this `Point2D` in
     * `double` precision.
     *
     * @return the X coordinate of this `Point2D`.
     * @since 1.2
     */
    abstract val x: Double

    /**
     * Returns the Y coordinate of this `Point2D` in
     * `double` precision.
     *
     * @return the Y coordinate of this `Point2D`.
     * @since 1.2
     */
    abstract val y: Double

    /**
     * Sets the location of this `Point2D` to the
     * specified `double` coordinates.
     *
     * @param x the new X coordinate of this `Point2D`
     * @param y the new Y coordinate of this `Point2D`
     * @since 1.2
     */
    abstract fun setLocation(x: Double, y: Double)

    /**
     * Sets the location of this `Point2D` to the same
     * coordinates as the specified `Point2D` object.
     *
     * @param p the specified `Point2D` to which to set
     * this `Point2D`
     * @since 1.2
     */
    fun setLocation(p: Point2D) {
        setLocation(p.x, p.y)
    }

    /**
     * Returns the square of the distance from this
     * `Point2D` to a specified point.
     *
     * @param px the X coordinate of the specified point to be measured
     * against this `Point2D`
     * @param py the Y coordinate of the specified point to be measured
     * against this `Point2D`
     * @return the square of the distance between this
     * `Point2D` and the specified point.
     * @since 1.2
     */
    fun distanceSq(px: Double, py: Double): Double {
        var px = px
        var py = py
        px -= x
        py -= y
        return (px * px + py * py)
    }

    /**
     * Returns the square of the distance from this
     * `Point2D` to a specified `Point2D`.
     *
     * @param pt the specified point to be measured
     * against this `Point2D`
     * @return the square of the distance between this
     * `Point2D` to a specified `Point2D`.
     * @since 1.2
     */
    fun distanceSq(pt: Point2D): Double {
        val px = pt.x - this.x
        val py = pt.y - this.y
        return (px * px + py * py)
    }

    /**
     * Returns the distance from this `Point2D` to
     * a specified point.
     *
     * @param px the X coordinate of the specified point to be measured
     * against this `Point2D`
     * @param py the Y coordinate of the specified point to be measured
     * against this `Point2D`
     * @return the distance between this `Point2D`
     * and a specified point.
     * @since 1.2
     */
    fun distance(px: Double, py: Double): Double {
        var px = px
        var py = py
        px -= x
        py -= y
        return sqrt(px * px + py * py)
    }

    /**
     * Returns the distance from this `Point2D` to a
     * specified `Point2D`.
     *
     * @param pt the specified point to be measured
     * against this `Point2D`
     * @return the distance between this `Point2D` and
     * the specified `Point2D`.
     * @since 1.2
     */
    fun distance(pt: Point2D): Double {
        val px = pt.x - this.x
        val py = pt.y - this.y
        return sqrt(px * px + py * py)
    }

    /**
     * Creates a new object of the same class and with the
     * same contents as this object.
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

    /**
     * Returns the hashcode for this `Point2D`.
     *
     * @return a hash code for this `Point2D`.
     */
    override fun hashCode(): Int {
        var bits = java.lang.Double.doubleToLongBits(x)
        bits = bits xor java.lang.Double.doubleToLongBits(y) * 31
        return ((bits.toInt()) xor ((bits shr 32).toInt()))
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
     * @since 1.2
     */
    override fun equals(obj: Any?): Boolean {
        if (obj is Point2D) {
            return (x == obj.x) && (y == obj.y)
        }
        return super.equals(obj)
    }

    companion object {
        /**
         * Returns the square of the distance between two points.
         *
         * @param x1 the X coordinate of the first specified point
         * @param y1 the Y coordinate of the first specified point
         * @param x2 the X coordinate of the second specified point
         * @param y2 the Y coordinate of the second specified point
         * @return the square of the distance between the two
         * sets of specified coordinates.
         * @since 1.2
         */
        fun distanceSq(
            x1: Double, y1: Double,
            x2: Double, y2: Double
        ): Double {
            var x1 = x1
            var y1 = y1
            x1 -= x2
            y1 -= y2
            return (x1 * x1 + y1 * y1)
        }

        /**
         * Returns the distance between two points.
         *
         * @param x1 the X coordinate of the first specified point
         * @param y1 the Y coordinate of the first specified point
         * @param x2 the X coordinate of the second specified point
         * @param y2 the Y coordinate of the second specified point
         * @return the distance between the two sets of specified
         * coordinates.
         * @since 1.2
         */
        fun distance(
            x1: Double, y1: Double,
            x2: Double, y2: Double
        ): Double {
            var x1 = x1
            var y1 = y1
            x1 -= x2
            y1 -= y2
            return sqrt(x1 * x1 + y1 * y1)
        }
    }
}
