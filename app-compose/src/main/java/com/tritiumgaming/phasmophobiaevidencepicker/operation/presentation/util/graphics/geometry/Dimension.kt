/*
 * Copyright (c) 1995, 2021, Oracle and/or its affiliates. All rights reserved.
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
package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.util.graphics.geometry

import java.io.Serializable
import kotlin.math.ceil


/**
 * The `Dimension` class encapsulates the width and
 * height of a component (in integer precision) in a single object.
 * The class is
 * associated with certain properties of components. Several methods
 * defined by the `Component` class and the
 * `LayoutManager` interface return a
 * `Dimension` object.
 *
 *
 * Normally the values of `width`
 * and `height` are non-negative integers.
 * The constructors that allow you to create a dimension do
 * not prevent you from setting a negative value for these properties.
 * If the value of `width` or `height` is
 * negative, the behavior of some methods defined by other objects is
 * undefined.
 *
 * @author Sami Shaio
 * @author Arthur van Hoff
 * @noinspection ALL
 * @since 1.0
 */
class Dimension
/**
 * Creates an instance of `Dimension` with a width
 * of zero and a height of zero.
 */ @JvmOverloads constructor(
    /**
     * The width dimension; negative values can be used.
     */
    override var width: Double = 0.0,
    /**
     * The height dimension; negative values can be used.
     */
    override var height: Double = 0.0
) : Dimension2D(), Serializable {
    /**
     * Creates an instance of `Dimension` whose width
     * and height are the same as for the specified dimension.
     *
     * @param d the specified dimension for the
     * `width` and
     * `height` values
     */
    constructor(d: Dimension) : this(d.width, d.height)

    /**
     * Sets the size of this `Dimension` object to
     * the specified width and height in double precision.
     * Note that if `width` or `height`
     * are larger than `Integer.MAX_VALUE`, they will
     * be reset to `Integer.MAX_VALUE`.
     *
     * @param width  the new width for the `Dimension` object
     * @param height the new height for the `Dimension` object
     * @since 1.2
     */
    override fun setSize(width: Double, height: Double) {
        this.width = ceil(width)
        this.height = ceil(height)
    }

    var size: Dimension
        /**
         * Gets the size of this `Dimension` object.
         * This method is included for completeness, to parallel the
         * `getSize` method defined by `Component`.
         *
         * @return the size of this dimension, a new instance of
         * `Dimension` with the same width and height
         * @since 1.1
         */
        get() = Dimension(width, height)
        /**
         * Sets the size of this `Dimension` object to the specified size.
         * This method is included for completeness, to parallel the
         * `setSize` method defined by `Component`.
         *
         * @param d the new size for this `Dimension` object
         * @since 1.1
         */
        set(d) {
            setSize(d.width, d.height)
        }

    /**
     * Sets the size of this `Dimension` object
     * to the specified width and height.
     * This method is included for completeness, to parallel the
     * `setSize` method defined by `Component`.
     *
     * @param width  the new width for this `Dimension` object
     * @param height the new height for this `Dimension` object
     * @since 1.1
     */
    fun setSize(width: Int, height: Int) {
        this.width = width.toDouble()
        this.height = height.toDouble()
    }

    /**
     * Checks whether two dimension objects have equal values.
     */
    override fun equals(obj: Any?): Boolean {
        if (obj is Dimension) {
            val d = obj
            return (width == d.width) && (height == d.height)
        }
        return false
    }

    /**
     * Returns the hash code for this `Dimension`.
     *
     * @return a hash code for this `Dimension`
     */
    override fun hashCode(): Int {
        val sum = width + height
        return (sum * (sum + 1) / 2 + width).toInt()
    }

    /**
     * Returns a string representation of the values of this
     * `Dimension` object's `height` and
     * `width` fields. This method is intended to be used only
     * for debugging purposes, and the content and format of the returned
     * string may vary between implementations. The returned string may be
     * empty but may not be `null`.
     *
     * @return a string representation of this `Dimension`
     * object
     */
    override fun toString(): String {
        return javaClass.name + "[width=" + width + ",height=" + height + "]"
    }

    companion object {
        /**
         * Use serialVersionUID from JDK 1.1 for interoperability.
         */
        private const val serialVersionUID = 4723952579491349524L
    }
}
