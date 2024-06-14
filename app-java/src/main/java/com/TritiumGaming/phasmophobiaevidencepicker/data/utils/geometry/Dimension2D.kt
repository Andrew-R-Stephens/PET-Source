package com.TritiumGaming.phasmophobiaevidencepicker.data.utils.geometry

import android.os.Build
import androidx.annotation.RequiresApi

/**
 * The `Dimension2D` class is to encapsulate a width
 * and a height dimension.
 *
 *
 * This class is only the abstract superclass for all objects that
 * store a 2D dimension.
 * The actual storage representation of the sizes is left to
 * the subclass.
 *
 * @author Jim Graham
 * @since 1.2
 */
abstract class Dimension2D
/**
 * This is an abstract class that cannot be instantiated directly.
 * Type-specific implementation subclasses are available for
 * instantiation and provide a number of formats for storing
 * the information necessary to satisfy the various accessor
 * methods below.
 *
 * @see Dimension
 *
 * @since 1.2
 */
protected constructor() : Cloneable {
    /**
     * Returns the width of this `Dimension` in double
     * precision.
     *
     * @return the width of this `Dimension`.
     * @since 1.2
     */
    abstract val width: Double

    /**
     * Returns the height of this `Dimension` in double
     * precision.
     *
     * @return the height of this `Dimension`.
     * @since 1.2
     */
    abstract val height: Double

    /**
     * Sets the size of this `Dimension` object to the
     * specified width and height.
     * This method is included for completeness, to parallel the
     *
     * @param width  the new width for the `Dimension`
     * object
     * @param height the new height for the `Dimension`
     * object
     * @since 1.2
     */
    abstract fun setSize(width: Double, height: Double)

    /**
     * Sets the size of this `Dimension2D` object to
     * match the specified size.
     * This method is included for completeness, to parallel the
     * `getSize` method of `Component`.
     *
     * @param d the new size for the `Dimension2D`
     * object
     * @since 1.2
     */
    fun setSize(d: Dimension2D) {
        setSize(d.width, d.height)
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
}
