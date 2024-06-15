/*
 * Copyright (c) 1996, 2021, Oracle and/or its affiliates. All rights reserved.
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
package com.TritiumGaming.phasmophobiaevidencepicker.utils.geometry

import android.os.Build
import androidx.annotation.RequiresApi
import com.TritiumGaming.phasmophobiaevidencepicker.utils.geometry.Path2D.Path2DDouble
import com.TritiumGaming.phasmophobiaevidencepicker.utils.geometry.Point2D.Point2DDouble
import com.TritiumGaming.phasmophobiaevidencepicker.utils.geometry.Point2D.Point2DFloat
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.round
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * The `AffineTransform` class represents a 2D affine transform
 * that performs a linear mapping from 2D coordinates to other 2D
 * coordinates that preserves the "straightness" and
 * "parallelness" of lines.  Affine transformations can be constructed
 * using sequences of translations, scales, flips, rotations, and shears.
 *
 *
 * Such a coordinate transformation can be represented by a 3 row by
 * 3 column matrix with an implied last row of [ 0 0 1 ].  This matrix
 * transforms source coordinates `(x,y)` into
 * destination coordinates `(x',y')` by considering
 * them to be a column vector and multiplying the coordinate vector
 * by the matrix according to the following process:
 * <pre>
 * [ x']   [  m00  m01  m02  ] [ x ]   [ m00x + m01y + m02 ]
 * [ y'] = [  m10  m11  m12  ] [ y ] = [ m10x + m11y + m12 ]
 * [ 1 ]   [   0    0    1   ] [ 1 ]   [         1         ]
</pre> *
 * <h2><a id="quadrantapproximation">Handling 90-Degree Rotations</a></h2>
 *
 *
 * In some variations of the `rotate` methods in the
 * `AffineTransform` class, a double-precision argument
 * specifies the angle of rotation in radians.
 * These methods have special handling for rotations of approximately
 * 90 degrees (including multiples such as 180, 270, and 360 degrees),
 * so that the common case of quadrant rotation is handled more
 * efficiently.
 * This special handling can cause angles very close to multiples of
 * 90 degrees to be treated as if they were exact multiples of
 * 90 degrees.
 * For small multiples of 90 degrees the range of angles treated
 * as a quadrant rotation is approximately 0.00000121 degrees wide.
 * This section explains why such special care is needed and how
 * it is implemented.
 *
 *
 * Since 90 degrees is represented as `PI/2` in radians,
 * and since PI is a transcendental (and therefore irrational) number,
 * it is not possible to exactly represent a multiple of 90 degrees as
 * an exact double precision value measured in radians.
 * As a result it is theoretically impossible to describe quadrant
 * rotations (90, 180, 270 or 360 degrees) using these values.
 * Double precision floating point values can get very close to
 * non-zero multiples of `PI/2` but never close enough
 * for the sine or cosine to be exactly 0.0, 1.0 or -1.0.
 * The implementations of `Math.sin()` and
 * `Math.cos()` correspondingly never return 0.0
 * for any case other than `Math.sin(0.0)`.
 * These same implementations do, however, return exactly 1.0 and
 * -1.0 for some range of numbers around each multiple of 90
 * degrees since the correct answer is so close to 1.0 or -1.0 that
 * the double precision significand cannot represent the difference
 * as accurately as it can for numbers that are near 0.0.
 *
 *
 * The net result of these issues is that if the
 * `Math.sin()` and `Math.cos()` methods
 * are used to directly generate the values for the matrix modifications
 * during these radian-based rotation operations then the resulting
 * transform is never strictly classifiable as a quadrant rotation
 * even for a simple case like `rotate(Math.PI/2.0)`,
 * due to minor variations in the matrix caused by the non-0.0 values
 * obtained for the sine and cosine.
 * If these transforms are not classified as quadrant rotations then
 * subsequent code which attempts to optimize further operations based
 * upon the type of the transform will be relegated to its most general
 * implementation.
 *
 *
 * Because quadrant rotations are fairly common,
 * this class should handle these cases reasonably quickly, both in
 * applying the rotations to the transform and in applying the resulting
 * transform to the coordinates.
 * To facilitate this optimal handling, the methods which take an angle
 * of rotation measured in radians attempt to detect angles that are
 * intended to be quadrant rotations and treat them as such.
 * These methods therefore treat an angle *theta* as a quadrant
 * rotation if either `Math.sin(*theta*)` or
 * `Math.cos(*theta*)` returns exactly 1.0 or -1.0.
 * As a rule of thumb, this property holds true for a range of
 * approximately 0.0000000211 radians (or 0.00000121 degrees) around
 * small multiples of `Math.PI/2.0`.
 *
 * @author Jim Graham
 * @noinspection PatternVariableCanBeUsed
 * @since 1.2
 */
@Suppress("ConstPropertyName", "ConstPropertyName", "ConstPropertyName", "ConstPropertyName",
    "ConstPropertyName", "ConstPropertyName", "ConstPropertyName", "ConstPropertyName",
    "ConstPropertyName", "ConstPropertyName", "ConstPropertyName", "ConstPropertyName",
    "ConstPropertyName", "ConstPropertyName", "ConstPropertyName", "ConstPropertyName",
    "ConstPropertyName"
)
class AffineTransform : Cloneable, Serializable {
    /** Returns the `m00` element of the 3x3 affine transformation matrix.
     * This matrix factor determines how input X coordinates will affect output
     * X coordinates and is one element of the scale of the transform.
     * To measure the full amount by which X coordinates are stretched or
     * contracted by this transform, use the following code:
     * <pre>
     * Point2D p = new Point2D.Double(1, 0);
     * p = tx.deltaTransform(p, p);
     * double scaleX = p.distance(0, 0);
    </pre>
     * @return a double value that is `m00` element of the
     * 3x3 affine transformation matrix.
     * @see .getMatrix */
    /** The X coordinate scaling element of the 3x3
     * affine transformation matrix. */
    var scaleX: Double

    /** Returns the Y coordinate shearing element (m10) of the 3x3
     * affine transformation matrix.
     * @return a double value that is the Y coordinate of the shearing
     * element of the affine transformation matrix.
     * @see .getMatrix */
    /** The Y coordinate shearing element of the 3x3
     * affine transformation matrix. */
    var shearY: Double = 0.0

    /** Returns the X coordinate shearing element (m01) of the 3x3
     * affine transformation matrix.
     * @return a double value that is the X coordinate of the shearing
     * element of the affine transformation matrix.
     * @see .getMatrix */
    /** The X coordinate shearing element of the 3x3
     * affine transformation matrix. */
    var shearX: Double = 0.0

    /**
     * Returns the `m11` element of the 3x3 affine transformation matrix.
     * This matrix factor determines how input Y coordinates will affect output
     * Y coordinates and is one element of the scale of the transform.
     * To measure the full amount by which Y coordinates are stretched or
     * contracted by this transform, use the following code:
     * <pre>
     * Point2D p = new Point2D.Double(0, 1);
     * p = tx.deltaTransform(p, p);
     * double scaleY = p.distance(0, 0);
    </pre> *
     *
     * @return a double value that is `m11` element of the
     * 3x3 affine transformation matrix.
     * @see .getMatrix
     *
     * @since 1.2
     */
    /**
     * The Y coordinate scaling element of the 3x3
     * affine transformation matrix.
     *
     * @serial
     */
    var scaleY: Double

    /**
     * Returns the X coordinate of the translation element (m02) of the
     * 3x3 affine transformation matrix.
     *
     * @return a double value that is the X coordinate of the translation
     * element of the affine transformation matrix.
     * @see .getMatrix
     *
     * @since 1.2
     */
    /**
     * The X coordinate of the translation element of the
     * 3x3 affine transformation matrix.
     *
     * @serial
     */
    var translateX: Double = 0.0

    /**
     * Returns the Y coordinate of the translation element (m12) of the
     * 3x3 affine transformation matrix.
     *
     * @return a double value that is the Y coordinate of the translation
     * element of the affine transformation matrix.
     * @see .getMatrix
     *
     * @since 1.2
     */
    /**
     * The Y coordinate of the translation element of the
     * 3x3 affine transformation matrix.
     *
     * @serial
     */
    var translateY: Double = 0.0

    /**
     * This field keeps track of which components of the matrix need to
     * be applied when performing a transformation.
     *
     * @see .APPLY_IDENTITY
     *
     * @see .APPLY_TRANSLATE
     *
     * @see .APPLY_SCALE
     *
     * @see .APPLY_SHEAR
     */
    @Transient
    var state: Int = 0

    /** This field caches the current transformation type of the matrix.
     * @see .TYPE_IDENTITY
     * @see .TYPE_TRANSLATION
     * @see .TYPE_UNIFORM_SCALE
     * @see .TYPE_GENERAL_SCALE
     * @see .TYPE_FLIP
     * @see .TYPE_QUADRANT_ROTATION
     * @see .TYPE_GENERAL_ROTATION
     * @see .TYPE_GENERAL_TRANSFORM
     * @see .TYPE_UNKNOWN
     * @see .getType */
    @Transient
    private var type = 0

    private constructor(
        m00: Double, m10: Double,
        m01: Double, m11: Double,
        m02: Double, m12: Double,
        state: Int
    ) {
        this.scaleX = m00
        this.shearY = m10
        this.shearX = m01
        this.scaleY = m11
        this.translateX = m02
        this.translateY = m12
        this.state = state
        this.type = TYPE_UNKNOWN
    }

    /** Constructs a new `AffineTransform` representing the
     * Identity transformation. */
    constructor() {
        scaleY = 1.0
        scaleX = scaleY
        // m01 = m10 = m02 = m12 = 0.0;         /* Not needed. */
        // state = APPLY_IDENTITY;              /* Not needed. */
        // type = TYPE_IDENTITY;                /* Not needed. */
    }

    /** Constructs a new `AffineTransform` that is a copy of
     * the specified `AffineTransform` object.
     * @param Tx the `AffineTransform` object to copy */
    constructor(Tx: AffineTransform) {
        this.scaleX = Tx.scaleX
        this.shearY = Tx.shearY
        this.shearX = Tx.shearX
        this.scaleY = Tx.scaleY
        this.translateX = Tx.translateX
        this.translateY = Tx.translateY
        this.state = Tx.state
        this.type = Tx.type
    }

    /** Constructs a new `AffineTransform` from 6 floating point
     * values representing the 6 specifiable entries of the 3x3
     * transformation matrix.
     * @param m00 the X coordinate scaling element of the 3x3 matrix
     * @param m10 the Y coordinate shearing element of the 3x3 matrix
     * @param m01 the X coordinate shearing element of the 3x3 matrix
     * @param m11 the Y coordinate scaling element of the 3x3 matrix
     * @param m02 the X coordinate translation element of the 3x3 matrix
     * @param m12 the Y coordinate translation element of the 3x3 matrix */
    constructor(
        m00: Float, m10: Float,
        m01: Float, m11: Float,
        m02: Float, m12: Float
    ) {
        this.scaleX = m00.toDouble()
        this.shearY = m10.toDouble()
        this.shearX = m01.toDouble()
        this.scaleY = m11.toDouble()
        this.translateX = m02.toDouble()
        this.translateY = m12.toDouble()
        updateState()
    }

    /** Constructs a new `AffineTransform` from an array of
     * floating point values representing either the 4 non-translation
     * entries or the 6 specifiable entries of the 3x3 transformation
     * matrix.  The values are retrieved from the array as
     * {&nbsp;m00&nbsp;m10&nbsp;m01&nbsp;m11&nbsp;[m02&nbsp;m12]}.
     * @param flatmatrix the float array containing the values to be set
     * in the new `AffineTransform` object. The length of the
     * array is assumed to be at least 4. If the length of the array is
     * less than 6, only the first 4 values are taken. If the length of
     * the array is greater than 6, the first 6 values are taken. */
    constructor(flatmatrix: FloatArray) {
        scaleX = flatmatrix[0].toDouble()
        shearY = flatmatrix[1].toDouble()
        shearX = flatmatrix[2].toDouble()
        scaleY = flatmatrix[3].toDouble()
        if (flatmatrix.size > 5) {
            translateX = flatmatrix[4].toDouble()
            translateY = flatmatrix[5].toDouble()
        }
        updateState()
    }

    /** Constructs a new `AffineTransform` from 6 double
     * precision values representing the 6 specifiable entries of the 3x3
     * transformation matrix.
     * @param m00 the X coordinate scaling element of the 3x3 matrix
     * @param m10 the Y coordinate shearing element of the 3x3 matrix
     * @param m01 the X coordinate shearing element of the 3x3 matrix
     * @param m11 the Y coordinate scaling element of the 3x3 matrix
     * @param m02 the X coordinate translation element of the 3x3 matrix
     * @param m12 the Y coordinate translation element of the 3x3 matrix */
    constructor(
        m00: Double, m10: Double,
        m01: Double, m11: Double,
        m02: Double, m12: Double
    ) {
        this.scaleX = m00
        this.shearY = m10
        this.shearX = m01
        this.scaleY = m11
        this.translateX = m02
        this.translateY = m12
        updateState()
    }

    /** Constructs a new `AffineTransform` from an array of
     * double precision values representing either the 4 non-translation
     * entries or the 6 specifiable entries of the 3x3 transformation
     * matrix. The values are retrieved from the array as
     * {&nbsp;m00&nbsp;m10&nbsp;m01&nbsp;m11&nbsp;[m02&nbsp;m12]}.
     * @param flatmatrix the double array containing the values to be set
     * in the new `AffineTransform` object. The length of the
     * array is assumed to be at least 4. If the length of the array is
     * less than 6, only the first 4 values are taken. If the length of
     * the array is greater than 6, the first 6 values are taken. */
    constructor(flatmatrix: DoubleArray) {
        scaleX = flatmatrix[0]
        shearY = flatmatrix[1]
        shearX = flatmatrix[2]
        scaleY = flatmatrix[3]
        if (flatmatrix.size > 5) {
            translateX = flatmatrix[4]
            translateY = flatmatrix[5]
        }
        updateState()
    }

    /** Retrieves the flag bits describing the conversion properties of this transform.
     * The return value is either one of the constants TYPE_IDENTITY
     * or TYPE_GENERAL_TRANSFORM, or a combination of the appropriate flag bits.
     * A valid combination of flag bits is an exclusive OR operation that can combine
     * the TYPE_TRANSLATION flag bit in addition to either of the
     * TYPE_UNIFORM_SCALE or TYPE_GENERAL_SCALE flag bits as well as either of the
     * TYPE_QUADRANT_ROTATION or TYPE_GENERAL_ROTATION flag bits.
     * @return the OR combination of any of the indicated flags that apply to this transform
     * @see .TYPE_IDENTITY
     * @see .TYPE_TRANSLATION
     * @see .TYPE_UNIFORM_SCALE
     * @see .TYPE_GENERAL_SCALE
     * @see .TYPE_QUADRANT_ROTATION
     * @see .TYPE_GENERAL_ROTATION
     * @see .TYPE_GENERAL_TRANSFORM */
    fun getType(): Int {
        if (type == TYPE_UNKNOWN) {
            calculateType()
        }
        return type
    }

    /** This is the utility function to calculate the flag bits when
     * they have not been cached. */
    private fun calculateType() {
        var ret = TYPE_IDENTITY
        val sgn0: Boolean
        val sgn1: Boolean
        var M0: Double
        var M1: Double
        var M2: Double
        var M3: Double
        updateState()
        when (state) {
            APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE -> {
                ret = TYPE_TRANSLATION
                if ((scaleX.also { M0 = it }) * (shearX.also { M2 = it }) + (shearY.also {
                        M3 = it
                    }) * (scaleY.also { M1 = it }) != 0.0) {
                    // Transformed unit vectors are not perpendicular...
                    this.type = TYPE_GENERAL_TRANSFORM
                    return
                }
                sgn0 = (M0 >= 0.0)
                sgn1 = (M1 >= 0.0)
                ret = if (sgn0 == sgn1) {
                    // sgn(M0) == sgn(M1) therefore sgn(M2) == -sgn(M3)
                    // This is the "unflipped" (right-handed) state
                    if (M0 != M1 || M2 != -M3) {
                        ret or (TYPE_GENERAL_ROTATION or TYPE_GENERAL_SCALE)
                    } else if (M0 * M1 - M2 * M3 != 1.0) {
                        ret or (TYPE_GENERAL_ROTATION or TYPE_UNIFORM_SCALE)
                    } else {
                        ret or TYPE_GENERAL_ROTATION
                    }
                } else {
                    // sgn(M0) == -sgn(M1) therefore sgn(M2) == sgn(M3)
                    // This is the "flipped" (left-handed) state
                    if (M0 != -M1 || M2 != M3) {
                        ret or (TYPE_GENERAL_ROTATION or
                                TYPE_FLIP or
                                TYPE_GENERAL_SCALE)
                    } else if (M0 * M1 - M2 * M3 != 1.0) {
                        ret or (TYPE_GENERAL_ROTATION or
                                TYPE_FLIP or
                                TYPE_UNIFORM_SCALE)
                    } else {
                        ret or (TYPE_GENERAL_ROTATION or TYPE_FLIP)
                    }
                }
            }

            APPLY_SHEAR or APPLY_SCALE -> {
                if ((scaleX.also { M0 = it }) * (shearX.also { M2 = it }) + (shearY.also {
                        M3 = it
                    }) * (scaleY.also { M1 = it }) != 0.0) {
                    this.type = TYPE_GENERAL_TRANSFORM
                    return
                }
                sgn0 = (M0 >= 0.0)
                sgn1 = (M1 >= 0.0)
                ret = if (sgn0 == sgn1) {
                    if (M0 != M1 || M2 != -M3) {
                        ret or (TYPE_GENERAL_ROTATION or TYPE_GENERAL_SCALE)
                    } else if (M0 * M1 - M2 * M3 != 1.0) {
                        ret or (TYPE_GENERAL_ROTATION or TYPE_UNIFORM_SCALE)
                    } else {
                        ret or TYPE_GENERAL_ROTATION
                    }
                } else {
                    if (M0 != -M1 || M2 != M3) {
                        ret or (TYPE_GENERAL_ROTATION or
                                TYPE_FLIP or
                                TYPE_GENERAL_SCALE)
                    } else if (M0 * M1 - M2 * M3 != 1.0) {
                        ret or (TYPE_GENERAL_ROTATION or
                                TYPE_FLIP or
                                TYPE_UNIFORM_SCALE)
                    } else {
                        ret or (TYPE_GENERAL_ROTATION or TYPE_FLIP)
                    }
                }
            }

            APPLY_SHEAR or APPLY_TRANSLATE -> {
                ret = TYPE_TRANSLATION
                sgn0 = ((shearX.also { M0 = it }) >= 0.0)
                sgn1 = ((shearY.also { M1 = it }) >= 0.0)
                ret = if (sgn0 != sgn1) {
                    // Different signs - simple 90 degree rotation
                    if (M0 != -M1) {
                        ret or (TYPE_QUADRANT_ROTATION or TYPE_GENERAL_SCALE)
                    } else if (M0 != 1.0 && M0 != -1.0) {
                        ret or (TYPE_QUADRANT_ROTATION or TYPE_UNIFORM_SCALE)
                    } else {
                        ret or TYPE_QUADRANT_ROTATION
                    }
                } else {
                    // Same signs - 90 degree rotation plus an axis flip too
                    if (M0 == M1) {
                        ret or (TYPE_QUADRANT_ROTATION or
                                TYPE_FLIP or
                                TYPE_UNIFORM_SCALE)
                    } else {
                        ret or (TYPE_QUADRANT_ROTATION or
                                TYPE_FLIP or
                                TYPE_GENERAL_SCALE)
                    }
                }
            }

            APPLY_SHEAR -> {
                sgn0 = ((shearX.also { M0 = it }) >= 0.0)
                sgn1 = ((shearY.also { M1 = it }) >= 0.0)
                ret = if (sgn0 != sgn1) {
                    if (M0 != -M1) {
                        ret or (TYPE_QUADRANT_ROTATION or TYPE_GENERAL_SCALE)
                    } else if (M0 != 1.0 && M0 != -1.0) {
                        ret or (TYPE_QUADRANT_ROTATION or TYPE_UNIFORM_SCALE)
                    } else {
                        ret or TYPE_QUADRANT_ROTATION
                    }
                } else {
                    if (M0 == M1) {
                        ret or (TYPE_QUADRANT_ROTATION or
                                TYPE_FLIP or
                                TYPE_UNIFORM_SCALE)
                    } else {
                        ret or (TYPE_QUADRANT_ROTATION or
                                TYPE_FLIP or
                                TYPE_GENERAL_SCALE)
                    }
                }
            }

            APPLY_SCALE or APPLY_TRANSLATE -> {
                ret = TYPE_TRANSLATION
                sgn0 = ((scaleX.also { M0 = it }) >= 0.0)
                sgn1 = ((scaleY.also { M1 = it }) >= 0.0)
                ret = if (sgn0 == sgn1) {
                    if (sgn0) {
                        // Both scaling factors non-negative - simple scale
                        // Note: APPLY_SCALE implies M0, M1 are not both 1
                        if (M0 == M1) {
                            ret or TYPE_UNIFORM_SCALE
                        } else {
                            ret or TYPE_GENERAL_SCALE
                        }
                    } else {
                        // Both scaling factors negative - 180 degree rotation
                        if (M0 != M1) {
                            ret or (TYPE_QUADRANT_ROTATION or TYPE_GENERAL_SCALE)
                        } else if (M0 != -1.0) {
                            ret or (TYPE_QUADRANT_ROTATION or TYPE_UNIFORM_SCALE)
                        } else {
                            ret or TYPE_QUADRANT_ROTATION
                        }
                    }
                } else {
                    // Scaling factor signs different - flip about some axis
                    if (M0 == -M1) {
                        if (M0 == 1.0 || M0 == -1.0) {
                            ret or TYPE_FLIP
                        } else {
                            ret or (TYPE_FLIP or TYPE_UNIFORM_SCALE)
                        }
                    } else {
                        ret or (TYPE_FLIP or TYPE_GENERAL_SCALE)
                    }
                }
            }

            APPLY_SCALE -> {
                sgn0 = ((scaleX.also { M0 = it }) >= 0.0)
                sgn1 = ((scaleY.also { M1 = it }) >= 0.0)
                ret = if (sgn0 == sgn1) {
                    if (sgn0) {
                        if (M0 == M1) {
                            ret or TYPE_UNIFORM_SCALE
                        } else {
                            ret or TYPE_GENERAL_SCALE
                        }
                    } else {
                        if (M0 != M1) {
                            ret or (TYPE_QUADRANT_ROTATION or TYPE_GENERAL_SCALE)
                        } else if (M0 != -1.0) {
                            ret or (TYPE_QUADRANT_ROTATION or TYPE_UNIFORM_SCALE)
                        } else {
                            ret or TYPE_QUADRANT_ROTATION
                        }
                    }
                } else {
                    if (M0 == -M1) {
                        if (M0 == 1.0 || M0 == -1.0) {
                            ret or TYPE_FLIP
                        } else {
                            ret or (TYPE_FLIP or TYPE_UNIFORM_SCALE)
                        }
                    } else {
                        ret or (TYPE_FLIP or TYPE_GENERAL_SCALE)
                    }
                }
            }

            APPLY_TRANSLATE -> ret = TYPE_TRANSLATION
            APPLY_IDENTITY -> {}
            else -> {
                stateError()
                ret = TYPE_TRANSLATION
                if ((scaleX.also { M0 = it }) * (shearX.also { M2 = it }) + (shearY.also {
                        M3 = it
                    }) * (scaleY.also { M1 = it }) != 0.0) {
                    this.type = TYPE_GENERAL_TRANSFORM
                    return
                }
                sgn0 = (M0 >= 0.0)
                sgn1 = (M1 >= 0.0)
                ret = if (sgn0 == sgn1) {
                    if (M0 != M1 || M2 != -M3) {
                        ret or (TYPE_GENERAL_ROTATION or TYPE_GENERAL_SCALE)
                    } else if (M0 * M1 - M2 * M3 != 1.0) {
                        ret or (TYPE_GENERAL_ROTATION or TYPE_UNIFORM_SCALE)
                    } else {
                        ret or TYPE_GENERAL_ROTATION
                    }
                } else {
                    if (M0 != -M1 || M2 != M3) {
                        ret or (TYPE_GENERAL_ROTATION or
                                TYPE_FLIP or
                                TYPE_GENERAL_SCALE)
                    } else if (M0 * M1 - M2 * M3 != 1.0) {
                        ret or (TYPE_GENERAL_ROTATION or
                                TYPE_FLIP or
                                TYPE_UNIFORM_SCALE)
                    } else {
                        ret or (TYPE_GENERAL_ROTATION or TYPE_FLIP)
                    }
                }
            }
        }
        this.type = ret
    }

    val determinant: Double
        /**
         * Returns the determinant of the matrix representation of the transform.
         * The determinant is useful both to determine if the transform can
         * be inverted and to get a single value representing the
         * combined X and Y scaling of the transform.
         *
         *
         * If the determinant is non-zero, then this transform is
         * invertible and the various methods that depend on the inverse
         * transform do not need to throw a
         * If the determinant is zero then this transform can not be
         * inverted since the transform maps all input coordinates onto
         * a line or a point.
         * If the determinant is near enough to zero then inverse transform
         * operations might not carry enough precision to produce meaningful
         * results.
         *
         *
         * If this transform represents a uniform scale, as indicated by
         * the `getType` method then the determinant also
         * represents the square of the uniform scale factor by which all of
         * the points are expanded from or contracted towards the origin.
         * If this transform represents a non-uniform scale or more general
         * transform then the determinant is not likely to represent a
         * value useful for any purpose other than determining if inverse
         * transforms are possible.
         *
         *
         * Mathematically, the determinant is calculated using the formula:
         * <pre>
         * |  m00  m01  m02  |
         * |  m10  m11  m12  |  =  m00 * m11 - m01 * m10
         * |   0    0    1   |
        </pre> *
         *
         * @return the determinant of the matrix used to transform the
         * coordinates.
         * @see .getType
         *
         * @see .createInverse
         *
         * @see .inverseTransform
         *
         * @see .TYPE_UNIFORM_SCALE
         *
         * @since 1.2
         */
        get() {
            when (state) {
                APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE, APPLY_SHEAR or APPLY_SCALE -> return scaleX * scaleY - shearX * shearY
                APPLY_SHEAR or APPLY_TRANSLATE, APPLY_SHEAR -> return -(shearX * shearY)
                APPLY_SCALE or APPLY_TRANSLATE, APPLY_SCALE -> return scaleX * scaleY
                APPLY_TRANSLATE, APPLY_IDENTITY -> return 1.0
                else -> {
                    stateError()
                    return scaleX * scaleY - shearX * shearY
                }
            }
        }

    /**
     * Manually recalculates the state of the transform when the matrix
     * changes too much to predict the effects on the state.
     * The following table specifies what the various settings of the
     * state field say about the values of the corresponding matrix
     * element fields.
     * Note that the rules governing the SCALE fields are slightly
     * different depending on whether the SHEAR flag is also set.
     * <pre>
     * SCALE            SHEAR          TRANSLATE
     * m00/m11          m01/m10          m02/m12
     *
     * IDENTITY             1.0              0.0              0.0
     * TRANSLATE (TR)       1.0              0.0          not both 0.0
     * SCALE (SC)       not both 1.0         0.0              0.0
     * TR | SC          not both 1.0         0.0          not both 0.0
     * SHEAR (SH)           0.0          not both 0.0         0.0
     * TR | SH              0.0          not both 0.0     not both 0.0
     * SC | SH          not both 0.0     not both 0.0         0.0
     * TR | SC | SH     not both 0.0     not both 0.0     not both 0.0
    </pre> *
     */
    fun updateState() {
        if (shearX == 0.0 && shearY == 0.0) {
            if (scaleX == 1.0 && scaleY == 1.0) {
                if (translateX == 0.0 && translateY == 0.0) {
                    state = APPLY_IDENTITY
                    type = TYPE_IDENTITY
                } else {
                    state = APPLY_TRANSLATE
                    type = TYPE_TRANSLATION
                }
            } else {
                if (translateX == 0.0 && translateY == 0.0) {
                    state = APPLY_SCALE
                    type = TYPE_UNKNOWN
                } else {
                    state = (APPLY_SCALE or APPLY_TRANSLATE)
                    type = TYPE_UNKNOWN
                }
            }
        } else {
            if (scaleX == 0.0 && scaleY == 0.0) {
                if (translateX == 0.0 && translateY == 0.0) {
                    state = APPLY_SHEAR
                    type = TYPE_UNKNOWN
                } else {
                    state = (APPLY_SHEAR or APPLY_TRANSLATE)
                    type = TYPE_UNKNOWN
                }
            } else {
                if (translateX == 0.0 && translateY == 0.0) {
                    state = (APPLY_SHEAR or APPLY_SCALE)
                    type = TYPE_UNKNOWN
                } else {
                    state = (APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE)
                    type = TYPE_UNKNOWN
                }
            }
        }
    }

    /*
     * Convenience method used internally to throw exceptions when
     * a case was forgotten in a switch statement.
     */
    private fun stateError() {
        throw InternalError("missing case in transform state switch")
    }

    /**
     * Retrieves the 6 specifiable values in the 3x3 affine transformation
     * matrix and places them into an array of double precisions values.
     * The values are stored in the array as
     * {&nbsp;m00&nbsp;m10&nbsp;m01&nbsp;m11&nbsp;m02&nbsp;m12&nbsp;}.
     * An array of 4 doubles can also be specified, in which case only the
     * first four elements representing the non-transform
     * parts of the array are retrieved and the values are stored into
     * the array as {&nbsp;m00&nbsp;m10&nbsp;m01&nbsp;m11&nbsp;}
     *
     * @param flatmatrix the double array used to store the returned
     * values.
     * @see .getScaleX
     *
     * @see .getScaleY
     *
     * @see .getShearX
     *
     * @see .getShearY
     *
     * @see .getTranslateX
     *
     * @see .getTranslateY
     *
     * @since 1.2
     */
    fun getMatrix(flatmatrix: DoubleArray) {
        flatmatrix[0] = scaleX
        flatmatrix[1] = shearY
        flatmatrix[2] = shearX
        flatmatrix[3] = scaleY
        if (flatmatrix.size > 5) {
            flatmatrix[4] = translateX
            flatmatrix[5] = translateY
        }
    }

    /**
     * Concatenates this transform with a translation transformation.
     * This is equivalent to calling concatenate(T), where T is an
     * `AffineTransform` represented by the following matrix:
     * <pre>
     * [   1    0    tx  ]
     * [   0    1    ty  ]
     * [   0    0    1   ]
    </pre> *
     *
     * @param tx the distance by which coordinates are translated in the
     * X axis direction
     * @param ty the distance by which coordinates are translated in the
     * Y axis direction
     * @since 1.2
     */
    fun translate(tx: Double, ty: Double) {
        when (state) {
            APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE -> {
                translateX = tx * scaleX + ty * shearX + translateX
                translateY = tx * shearY + ty * scaleY + translateY
                if (translateX == 0.0 && translateY == 0.0) {
                    state = APPLY_SHEAR or APPLY_SCALE
                    if (type != TYPE_UNKNOWN) {
                        type -= TYPE_TRANSLATION
                    }
                }
                return
            }

            APPLY_SHEAR or APPLY_SCALE -> {
                translateX = tx * scaleX + ty * shearX
                translateY = tx * shearY + ty * scaleY
                if (translateX != 0.0 || translateY != 0.0) {
                    state = APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE
                    type = type or TYPE_TRANSLATION
                }
                return
            }

            APPLY_SHEAR or APPLY_TRANSLATE -> {
                translateX = ty * shearX + translateX
                translateY = tx * shearY + translateY
                if (translateX == 0.0 && translateY == 0.0) {
                    state = APPLY_SHEAR
                    if (type != TYPE_UNKNOWN) {
                        type -= TYPE_TRANSLATION
                    }
                }
                return
            }

            APPLY_SHEAR -> {
                translateX = ty * shearX
                translateY = tx * shearY
                if (translateX != 0.0 || translateY != 0.0) {
                    state = APPLY_SHEAR or APPLY_TRANSLATE
                    type = type or TYPE_TRANSLATION
                }
                return
            }

            APPLY_SCALE or APPLY_TRANSLATE -> {
                translateX = tx * scaleX + translateX
                translateY = ty * scaleY + translateY
                if (translateX == 0.0 && translateY == 0.0) {
                    state = APPLY_SCALE
                    if (type != TYPE_UNKNOWN) {
                        type -= TYPE_TRANSLATION
                    }
                }
                return
            }

            APPLY_SCALE -> {
                translateX = tx * scaleX
                translateY = ty * scaleY
                if (translateX != 0.0 || translateY != 0.0) {
                    state = APPLY_SCALE or APPLY_TRANSLATE
                    type = type or TYPE_TRANSLATION
                }
                return
            }

            APPLY_TRANSLATE -> {
                translateX = tx + translateX
                translateY = ty + translateY
                if (translateX == 0.0 && translateY == 0.0) {
                    state = APPLY_IDENTITY
                    type = TYPE_IDENTITY
                }
                return
            }

            APPLY_IDENTITY -> {
                translateX = tx
                translateY = ty
                if (tx != 0.0 || ty != 0.0) {
                    state = APPLY_TRANSLATE
                    type = TYPE_TRANSLATION
                }
            }

            else -> {
                stateError()
                /* NOTREACHED */
                return
            }
        }
    }

    private fun rotate90() {
        var M0 = scaleX
        scaleX = shearX
        shearX = -M0
        M0 = shearY
        shearY = scaleY
        scaleY = -M0
        var state = rot90conversion[state]
        if ((state and (APPLY_SHEAR or APPLY_SCALE)) == APPLY_SCALE && (
                    scaleX == 1.0) && (scaleY == 1.0)
        ) {
            state -= APPLY_SCALE
        }
        this.state = state
        type = TYPE_UNKNOWN
    }

    private fun rotate180() {
        scaleX = -scaleX
        scaleY = -scaleY
        val state = this.state
        if ((state and (APPLY_SHEAR)) != 0) {
            // If there was a shear, then this rotation has no
            // effect on the state.
            shearX = -shearX
            shearY = -shearY
        } else {
            // No shear means the SCALE state may toggle when
            // m00 and m11 are negated.
            if (scaleX == 1.0 && scaleY == 1.0) {
                this.state = state and APPLY_SCALE.inv()
            } else {
                this.state = state or APPLY_SCALE
            }
        }
        type = TYPE_UNKNOWN
    }

    private fun rotate270() {
        var M0 = scaleX
        scaleX = -shearX
        shearX = M0
        M0 = shearY
        shearY = -scaleY
        scaleY = M0
        var state = rot90conversion[state]
        if ((state and (APPLY_SHEAR or APPLY_SCALE)) == APPLY_SCALE && (
                    scaleX == 1.0) && (scaleY == 1.0)
        ) {
            state -= APPLY_SCALE
        }
        this.state = state
        type = TYPE_UNKNOWN
    }

    /**
     * Concatenates this transform with a rotation transformation.
     * This is equivalent to calling concatenate(R), where R is an
     * `AffineTransform` represented by the following matrix:
     * <pre>
     * [   cos(theta)    -sin(theta)    0   ]
     * [   sin(theta)     cos(theta)    0   ]
     * [       0              0         1   ]
    </pre> *
     * Rotating by a positive angle theta rotates points on the positive
     * X axis toward the positive Y axis.
     * Note also the discussion of
     * [Handling 90-Degree Rotations](#quadrantapproximation)
     * above.
     *
     * @param theta the angle of rotation measured in radians
     * @since 1.2
     */
    fun rotate(theta: Double) {
        val sin = sin(theta)
        if (sin == 1.0) {
            rotate90()
        } else if (sin == -1.0) {
            rotate270()
        } else {
            val cos = cos(theta)
            if (cos == -1.0) {
                rotate180()
            } else if (cos != 1.0) {
                var M0 = scaleX
                var M1 = shearX
                scaleX = cos * M0 + sin * M1
                shearX = -sin * M0 + cos * M1
                M0 = shearY
                M1 = scaleY
                shearY = cos * M0 + sin * M1
                scaleY = -sin * M0 + cos * M1
                updateState()
            }
        }
    }

    /**
     * Concatenates this transform with a transform that rotates
     * coordinates around an anchor point.
     * This operation is equivalent to translating the coordinates so
     * that the anchor point is at the origin (S1), then rotating them
     * about the new origin (S2), and finally translating so that the
     * intermediate origin is restored to the coordinates of the original
     * anchor point (S3).
     *
     *
     * This operation is equivalent to the following sequence of calls:
     * <pre>
     * translate(anchorx, anchory);      // S3: final translation
     * rotate(theta);                    // S2: rotate around anchor
     * translate(-anchorx, -anchory);    // S1: translate anchor to origin
    </pre> *
     * Rotating by a positive angle theta rotates points on the positive
     * X axis toward the positive Y axis.
     * Note also the discussion of
     * [Handling 90-Degree Rotations](#quadrantapproximation)
     * above.
     *
     * @param theta   the angle of rotation measured in radians
     * @param anchorx the X coordinate of the rotation anchor point
     * @param anchory the Y coordinate of the rotation anchor point
     * @since 1.2
     */
    fun rotate(theta: Double, anchorx: Double, anchory: Double) {
        // REMIND: Simple for now - optimize later
        translate(anchorx, anchory)
        rotate(theta)
        translate(-anchorx, -anchory)
    }

    /**
     * Concatenates this transform with a transform that rotates
     * coordinates according to a rotation vector.
     * All coordinates rotate about the origin by the same amount.
     * The amount of rotation is such that coordinates along the former
     * positive X axis will subsequently align with the vector pointing
     * from the origin to the specified vector coordinates.
     * If both `vecx` and `vecy` are 0.0,
     * no additional rotation is added to this transform.
     * This operation is equivalent to calling:
     * <pre>
     * rotate(Math.atan2(vecy, vecx));
    </pre> *
     *
     * @param vecx the X coordinate of the rotation vector
     * @param vecy the Y coordinate of the rotation vector
     * @since 1.6
     */
    fun rotate(vecx: Double, vecy: Double) {
        if (vecy == 0.0) {
            if (vecx < 0.0) {
                rotate180()
            }
            // If vecx > 0.0 - no rotation
            // If vecx == 0.0 - undefined rotation - treat as no rotation
        } else if (vecx == 0.0) {
            if (vecy > 0.0) {
                rotate90()
            } else {  // vecy must be < 0.0
                rotate270()
            }
        } else {
            val len = sqrt(vecx * vecx + vecy * vecy)
            val sin = vecy / len
            val cos = vecx / len
            var M0 = scaleX
            var M1 = shearX
            scaleX = cos * M0 + sin * M1
            shearX = -sin * M0 + cos * M1
            M0 = shearY
            M1 = scaleY
            shearY = cos * M0 + sin * M1
            scaleY = -sin * M0 + cos * M1
            updateState()
        }
    }

    /**
     * Concatenates this transform with a transform that rotates
     * coordinates around an anchor point according to a rotation
     * vector.
     * All coordinates rotate about the specified anchor coordinates
     * by the same amount.
     * The amount of rotation is such that coordinates along the former
     * positive X axis will subsequently align with the vector pointing
     * from the origin to the specified vector coordinates.
     * If both `vecx` and `vecy` are 0.0,
     * the transform is not modified in any way.
     * This method is equivalent to calling:
     * <pre>
     * rotate(Math.atan2(vecy, vecx), anchorx, anchory);
    </pre> *
     *
     * @param vecx    the X coordinate of the rotation vector
     * @param vecy    the Y coordinate of the rotation vector
     * @param anchorx the X coordinate of the rotation anchor point
     * @param anchory the Y coordinate of the rotation anchor point
     * @since 1.6
     */
    fun rotate(
        vecx: Double, vecy: Double,
        anchorx: Double, anchory: Double
    ) {
        // REMIND: Simple for now - optimize later
        translate(anchorx, anchory)
        rotate(vecx, vecy)
        translate(-anchorx, -anchory)
    }

    /**
     * Concatenates this transform with a transform that rotates
     * coordinates by the specified number of quadrants.
     * This is equivalent to calling:
     * <pre>
     * rotate(numquadrants * Math.PI / 2.0);
    </pre> *
     * Rotating by a positive number of quadrants rotates points on
     * the positive X axis toward the positive Y axis.
     *
     * @param numquadrants the number of 90 degree arcs to rotate by
     * @since 1.6
     */
    fun quadrantRotate(numquadrants: Int) {
        when (numquadrants and 3) {
            0 -> {}
            1 -> rotate90()
            2 -> rotate180()
            3 -> rotate270()
        }
    }

    /**
     * Concatenates this transform with a transform that rotates
     * coordinates by the specified number of quadrants around
     * the specified anchor point.
     * This method is equivalent to calling:
     * <pre>
     * rotate(numquadrants * Math.PI / 2.0, anchorx, anchory);
    </pre> *
     * Rotating by a positive number of quadrants rotates points on
     * the positive X axis toward the positive Y axis.
     *
     * @param numquadrants the number of 90 degree arcs to rotate by
     * @param anchorx      the X coordinate of the rotation anchor point
     * @param anchory      the Y coordinate of the rotation anchor point
     * @since 1.6
     */
    fun quadrantRotate(
        numquadrants: Int,
        anchorx: Double, anchory: Double
    ) {
        when (numquadrants and 3) {
            0 -> return
            1 -> {
                translateX += anchorx * (scaleX - shearX) + anchory * (shearX + scaleX)
                translateY += anchorx * (shearY - scaleY) + anchory * (scaleY + shearY)
                rotate90()
            }

            2 -> {
                translateX += anchorx * (scaleX + scaleX) + anchory * (shearX + shearX)
                translateY += anchorx * (shearY + shearY) + anchory * (scaleY + scaleY)
                rotate180()
            }

            3 -> {
                translateX += anchorx * (scaleX + shearX) + anchory * (shearX - scaleX)
                translateY += anchorx * (shearY + scaleY) + anchory * (scaleY - shearY)
                rotate270()
            }
        }
        state = if (translateX == 0.0 && translateY == 0.0) {
            state and APPLY_TRANSLATE.inv()
        } else {
            state or APPLY_TRANSLATE
        }
    }

    /**
     * Concatenates this transform with a scaling transformation.
     * This is equivalent to calling concatenate(S), where S is an
     * `AffineTransform` represented by the following matrix:
     * <pre>
     * [   sx   0    0   ]
     * [   0    sy   0   ]
     * [   0    0    1   ]
    </pre> *
     *
     * @param sx the factor by which coordinates are scaled along the
     * X axis direction
     * @param sy the factor by which coordinates are scaled along the
     * Y axis direction
     * @since 1.2
     */
    fun scale(sx: Double, sy: Double) {
        var state = this.state
        when (state) {
            APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE, APPLY_SHEAR or APPLY_SCALE -> {
                scaleX *= sx
                scaleY *= sy
                shearX *= sy
                shearY *= sx
                if (shearX == 0.0 && shearY == 0.0) {
                    state = state and APPLY_TRANSLATE
                    if (scaleX == 1.0 && scaleY == 1.0) {
                        this.type = (if (state == APPLY_IDENTITY
                        ) TYPE_IDENTITY
                        else TYPE_TRANSLATION)
                    } else {
                        state = state or APPLY_SCALE
                        this.type = TYPE_UNKNOWN
                    }
                    this.state = state
                }
                return
            }

            APPLY_SHEAR or APPLY_TRANSLATE, APPLY_SHEAR -> {
                shearX *= sy
                shearY *= sx
                if (shearX == 0.0 && shearY == 0.0) {
                    state = state and APPLY_TRANSLATE
                    if (scaleX == 1.0 && scaleY == 1.0) {
                        this.type = (if (state == APPLY_IDENTITY
                        ) TYPE_IDENTITY
                        else TYPE_TRANSLATION)
                    } else {
                        state = state or APPLY_SCALE
                        this.type = TYPE_UNKNOWN
                    }
                    this.state = state
                }
                return
            }

            APPLY_SCALE or APPLY_TRANSLATE, APPLY_SCALE -> {
                scaleX *= sx
                scaleY *= sy
                if (scaleX == 1.0 && scaleY == 1.0) {
                    this.state = (APPLY_TRANSLATE.let { state = state and it; state })
                    this.type = (if (state == APPLY_IDENTITY
                    ) TYPE_IDENTITY
                    else TYPE_TRANSLATION)
                } else {
                    this.type = TYPE_UNKNOWN
                }
                return
            }

            APPLY_TRANSLATE, APPLY_IDENTITY -> {
                scaleX = sx
                scaleY = sy
                if (sx != 1.0 || sy != 1.0) {
                    this.state = state or APPLY_SCALE
                    this.type = TYPE_UNKNOWN
                }
            }

            else -> {
                stateError()
                scaleX *= sx
                scaleY *= sy
                shearX *= sy
                shearY *= sx
                if (shearX == 0.0 && shearY == 0.0) {
                    state = state and APPLY_TRANSLATE
                    if (scaleX == 1.0 && scaleY == 1.0) {
                        this.type = (if (state == APPLY_IDENTITY
                        ) TYPE_IDENTITY
                        else TYPE_TRANSLATION)
                    } else {
                        state = state or APPLY_SCALE
                        this.type = TYPE_UNKNOWN
                    }
                    this.state = state
                }
                return
            }
        }
    }

    /**
     * Concatenates this transform with a shearing transformation.
     * This is equivalent to calling concatenate(SH), where SH is an
     * `AffineTransform` represented by the following matrix:
     * <pre>
     * [   1   shx   0   ]
     * [  shy   1    0   ]
     * [   0    0    1   ]
    </pre> *
     *
     * @param shx the multiplier by which coordinates are shifted in the
     * direction of the positive X axis as a factor of their Y coordinate
     * @param shy the multiplier by which coordinates are shifted in the
     * direction of the positive Y axis as a factor of their X coordinate
     * @since 1.2
     */
    fun shear(shx: Double, shy: Double) {
        val state = this.state
        when (state) {
            APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE, APPLY_SHEAR or APPLY_SCALE -> {
                var M0 = scaleX
                var M1 = shearX
                scaleX = M0 + M1 * shy
                shearX = M0 * shx + M1

                M0 = shearY
                M1 = scaleY
                shearY = M0 + M1 * shy
                scaleY = M0 * shx + M1
                updateState()
                return
            }

            APPLY_SHEAR or APPLY_TRANSLATE, APPLY_SHEAR -> {
                scaleX = shearX * shy
                scaleY = shearY * shx
                if (scaleX != 0.0 || scaleY != 0.0) {
                    this.state = state or APPLY_SCALE
                }
                this.type = TYPE_UNKNOWN
                return
            }

            APPLY_SCALE or APPLY_TRANSLATE, APPLY_SCALE -> {
                shearX = scaleX * shx
                shearY = scaleY * shy
                if (shearX != 0.0 || shearY != 0.0) {
                    this.state = state or APPLY_SHEAR
                }
                this.type = TYPE_UNKNOWN
                return
            }

            APPLY_TRANSLATE, APPLY_IDENTITY -> {
                shearX = shx
                shearY = shy
                if (shearX != 0.0 || shearY != 0.0) {
                    this.state = state or APPLY_SCALE or APPLY_SHEAR
                    this.type = TYPE_UNKNOWN
                }
            }

            else -> {
                stateError()
                /* NOTREACHED */
                return
            }
        }
    }

    /**
     * Resets this transform to the Identity transform.
     *
     * @since 1.2
     */
    fun setToIdentity() {
        scaleY = 1.0
        scaleX = scaleY
        translateY = 0.0
        translateX = translateY
        shearX = translateX
        shearY = shearX
        state = APPLY_IDENTITY
        type = TYPE_IDENTITY
    }

    /**
     * Sets this transform to a translation transformation.
     * The matrix representing this transform becomes:
     * <pre>
     * [   1    0    tx  ]
     * [   0    1    ty  ]
     * [   0    0    1   ]
    </pre> *
     *
     * @param tx the distance by which coordinates are translated in the
     * X axis direction
     * @param ty the distance by which coordinates are translated in the
     * Y axis direction
     * @since 1.2
     */
    fun setToTranslation(tx: Double, ty: Double) {
        scaleX = 1.0
        shearY = 0.0
        shearX = 0.0
        scaleY = 1.0
        translateX = tx
        translateY = ty
        if (tx != 0.0 || ty != 0.0) {
            state = APPLY_TRANSLATE
            type = TYPE_TRANSLATION
        } else {
            state = APPLY_IDENTITY
            type = TYPE_IDENTITY
        }
    }

    /**
     * Sets this transform to a rotation transformation.
     * The matrix representing this transform becomes:
     * <pre>
     * [   cos(theta)    -sin(theta)    0   ]
     * [   sin(theta)     cos(theta)    0   ]
     * [       0              0         1   ]
    </pre> *
     * Rotating by a positive angle theta rotates points on the positive
     * X axis toward the positive Y axis.
     * Note also the discussion of
     * [Handling 90-Degree Rotations](#quadrantapproximation)
     * above.
     *
     * @param theta the angle of rotation measured in radians
     * @since 1.2
     */
    fun setToRotation(theta: Double) {
        var sin = sin(theta)
        val cos: Double
        if (sin == 1.0 || sin == -1.0) {
            cos = 0.0
            state = APPLY_SHEAR
            type = TYPE_QUADRANT_ROTATION
        } else {
            cos = cos(theta)
            if (cos == -1.0) {
                sin = 0.0
                state = APPLY_SCALE
                type = TYPE_QUADRANT_ROTATION
            } else if (cos == 1.0) {
                sin = 0.0
                state = APPLY_IDENTITY
                type = TYPE_IDENTITY
            } else {
                state = APPLY_SHEAR or APPLY_SCALE
                type = TYPE_GENERAL_ROTATION
            }
        }
        scaleX = cos
        shearY = sin
        shearX = -sin
        scaleY = cos
        translateX = 0.0
        translateY = 0.0
    }

    /**
     * Sets this transform to a translated rotation transformation.
     * This operation is equivalent to translating the coordinates so
     * that the anchor point is at the origin (S1), then rotating them
     * about the new origin (S2), and finally translating so that the
     * intermediate origin is restored to the coordinates of the original
     * anchor point (S3).
     *
     *
     * This operation is equivalent to the following sequence of calls:
     * <pre>
     * setToTranslation(anchorx, anchory); // S3: final translation
     * rotate(theta);                      // S2: rotate around anchor
     * translate(-anchorx, -anchory);      // S1: translate anchor to origin
    </pre> *
     * The matrix representing this transform becomes:
     * <pre>
     * [   cos(theta)    -sin(theta)    x-x*cos+y*sin  ]
     * [   sin(theta)     cos(theta)    y-x*sin-y*cos  ]
     * [       0              0               1        ]
    </pre> *
     * Rotating by a positive angle theta rotates points on the positive
     * X axis toward the positive Y axis.
     * Note also the discussion of
     * [Handling 90-Degree Rotations](#quadrantapproximation)
     * above.
     *
     * @param theta   the angle of rotation measured in radians
     * @param anchorx the X coordinate of the rotation anchor point
     * @param anchory the Y coordinate of the rotation anchor point
     * @since 1.2
     */
    fun setToRotation(theta: Double, anchorx: Double, anchory: Double) {
        setToRotation(theta)
        val sin = shearY
        val oneMinusCos = 1.0 - scaleX
        translateX = anchorx * oneMinusCos + anchory * sin
        translateY = anchory * oneMinusCos - anchorx * sin
        if (translateX != 0.0 || translateY != 0.0) {
            state = state or APPLY_TRANSLATE
            type = type or TYPE_TRANSLATION
        }
    }

    /**
     * Sets this transform to a rotation transformation that rotates
     * coordinates according to a rotation vector.
     * All coordinates rotate about the origin by the same amount.
     * The amount of rotation is such that coordinates along the former
     * positive X axis will subsequently align with the vector pointing
     * from the origin to the specified vector coordinates.
     * If both `vecx` and `vecy` are 0.0,
     * the transform is set to an identity transform.
     * This operation is equivalent to calling:
     * <pre>
     * setToRotation(Math.atan2(vecy, vecx));
    </pre> *
     *
     * @param vecx the X coordinate of the rotation vector
     * @param vecy the Y coordinate of the rotation vector
     * @since 1.6
     */
    fun setToRotation(vecx: Double, vecy: Double) {
        val sin: Double
        val cos: Double
        if (vecy == 0.0) {
            sin = 0.0
            if (vecx < 0.0) {
                cos = -1.0
                state = APPLY_SCALE
                type = TYPE_QUADRANT_ROTATION
            } else {
                cos = 1.0
                state = APPLY_IDENTITY
                type = TYPE_IDENTITY
            }
        } else if (vecx == 0.0) {
            cos = 0.0
            sin = if ((vecy > 0.0)) 1.0 else -1.0
            state = APPLY_SHEAR
            type = TYPE_QUADRANT_ROTATION
        } else {
            val len = sqrt(vecx * vecx + vecy * vecy)
            cos = vecx / len
            sin = vecy / len
            state = APPLY_SHEAR or APPLY_SCALE
            type = TYPE_GENERAL_ROTATION
        }
        scaleX = cos
        shearY = sin
        shearX = -sin
        scaleY = cos
        translateX = 0.0
        translateY = 0.0
    }

    /**
     * Sets this transform to a rotation transformation that rotates
     * coordinates around an anchor point according to a rotation
     * vector.
     * All coordinates rotate about the specified anchor coordinates
     * by the same amount.
     * The amount of rotation is such that coordinates along the former
     * positive X axis will subsequently align with the vector pointing
     * from the origin to the specified vector coordinates.
     * If both `vecx` and `vecy` are 0.0,
     * the transform is set to an identity transform.
     * This operation is equivalent to calling:
     * <pre>
     * setToTranslation(Math.atan2(vecy, vecx), anchorx, anchory);
    </pre> *
     *
     * @param vecx    the X coordinate of the rotation vector
     * @param vecy    the Y coordinate of the rotation vector
     * @param anchorx the X coordinate of the rotation anchor point
     * @param anchory the Y coordinate of the rotation anchor point
     * @since 1.6
     */
    fun setToRotation(
        vecx: Double, vecy: Double,
        anchorx: Double, anchory: Double
    ) {
        setToRotation(vecx, vecy)
        val sin = shearY
        val oneMinusCos = 1.0 - scaleX
        translateX = anchorx * oneMinusCos + anchory * sin
        translateY = anchory * oneMinusCos - anchorx * sin
        if (translateX != 0.0 || translateY != 0.0) {
            state = state or APPLY_TRANSLATE
            type = type or TYPE_TRANSLATION
        }
    }

    /**
     * Sets this transform to a rotation transformation that rotates
     * coordinates by the specified number of quadrants.
     * This operation is equivalent to calling:
     * <pre>
     * setToRotation(numquadrants * Math.PI / 2.0);
    </pre> *
     * Rotating by a positive number of quadrants rotates points on
     * the positive X axis toward the positive Y axis.
     *
     * @param numquadrants the number of 90 degree arcs to rotate by
     * @since 1.6
     */
    fun setToQuadrantRotation(numquadrants: Int) {
        when (numquadrants and 3) {
            0 -> {
                scaleX = 1.0
                shearY = 0.0
                shearX = 0.0
                scaleY = 1.0
                translateX = 0.0
                translateY = 0.0
                state = APPLY_IDENTITY
                type = TYPE_IDENTITY
            }

            1 -> {
                scaleX = 0.0
                shearY = 1.0
                shearX = -1.0
                scaleY = 0.0
                translateX = 0.0
                translateY = 0.0
                state = APPLY_SHEAR
                type = TYPE_QUADRANT_ROTATION
            }

            2 -> {
                scaleX = -1.0
                shearY = 0.0
                shearX = 0.0
                scaleY = -1.0
                translateX = 0.0
                translateY = 0.0
                state = APPLY_SCALE
                type = TYPE_QUADRANT_ROTATION
            }

            3 -> {
                scaleX = 0.0
                shearY = -1.0
                shearX = 1.0
                scaleY = 0.0
                translateX = 0.0
                translateY = 0.0
                state = APPLY_SHEAR
                type = TYPE_QUADRANT_ROTATION
            }
        }
    }

    /**
     * Sets this transform to a translated rotation transformation
     * that rotates coordinates by the specified number of quadrants
     * around the specified anchor point.
     * This operation is equivalent to calling:
     * <pre>
     * setToRotation(numquadrants * Math.PI / 2.0, anchorx, anchory);
    </pre> *
     * Rotating by a positive number of quadrants rotates points on
     * the positive X axis toward the positive Y axis.
     *
     * @param numquadrants the number of 90 degree arcs to rotate by
     * @param anchorx      the X coordinate of the rotation anchor point
     * @param anchory      the Y coordinate of the rotation anchor point
     * @since 1.6
     */
    fun setToQuadrantRotation(
        numquadrants: Int,
        anchorx: Double, anchory: Double
    ) {
        when (numquadrants and 3) {
            0 -> {
                scaleX = 1.0
                shearY = 0.0
                shearX = 0.0
                scaleY = 1.0
                translateX = 0.0
                translateY = 0.0
                state = APPLY_IDENTITY
                type = TYPE_IDENTITY
            }

            1 -> {
                scaleX = 0.0
                shearY = 1.0
                shearX = -1.0
                scaleY = 0.0
                translateX = anchorx + anchory
                translateY = anchory - anchorx
                if (translateX == 0.0 && translateY == 0.0) {
                    state = APPLY_SHEAR
                    type = TYPE_QUADRANT_ROTATION
                } else {
                    state = APPLY_SHEAR or APPLY_TRANSLATE
                    type = TYPE_QUADRANT_ROTATION or TYPE_TRANSLATION
                }
            }

            2 -> {
                scaleX = -1.0
                shearY = 0.0
                shearX = 0.0
                scaleY = -1.0
                translateX = anchorx + anchorx
                translateY = anchory + anchory
                if (translateX == 0.0 && translateY == 0.0) {
                    state = APPLY_SCALE
                    type = TYPE_QUADRANT_ROTATION
                } else {
                    state = APPLY_SCALE or APPLY_TRANSLATE
                    type = TYPE_QUADRANT_ROTATION or TYPE_TRANSLATION
                }
            }

            3 -> {
                scaleX = 0.0
                shearY = -1.0
                shearX = 1.0
                scaleY = 0.0
                translateX = anchorx - anchory
                translateY = anchory + anchorx
                if (translateX == 0.0 && translateY == 0.0) {
                    state = APPLY_SHEAR
                    type = TYPE_QUADRANT_ROTATION
                } else {
                    state = APPLY_SHEAR or APPLY_TRANSLATE
                    type = TYPE_QUADRANT_ROTATION or TYPE_TRANSLATION
                }
            }
        }
    }

    /**
     * Sets this transform to a scaling transformation.
     * The matrix representing this transform becomes:
     * <pre>
     * [   sx   0    0   ]
     * [   0    sy   0   ]
     * [   0    0    1   ]
    </pre> *
     *
     * @param sx the factor by which coordinates are scaled along the
     * X axis direction
     * @param sy the factor by which coordinates are scaled along the
     * Y axis direction
     * @since 1.2
     */
    fun setToScale(sx: Double, sy: Double) {
        scaleX = sx
        shearY = 0.0
        shearX = 0.0
        scaleY = sy
        translateX = 0.0
        translateY = 0.0
        if (sx != 1.0 || sy != 1.0) {
            state = APPLY_SCALE
            type = TYPE_UNKNOWN
        } else {
            state = APPLY_IDENTITY
            type = TYPE_IDENTITY
        }
    }

    /**
     * Sets this transform to a shearing transformation.
     * The matrix representing this transform becomes:
     * <pre>
     * [   1   shx   0   ]
     * [  shy   1    0   ]
     * [   0    0    1   ]
    </pre> *
     *
     * @param shx the multiplier by which coordinates are shifted in the
     * direction of the positive X axis as a factor of their Y coordinate
     * @param shy the multiplier by which coordinates are shifted in the
     * direction of the positive Y axis as a factor of their X coordinate
     * @since 1.2
     */
    fun setToShear(shx: Double, shy: Double) {
        scaleX = 1.0
        shearX = shx
        shearY = shy
        scaleY = 1.0
        translateX = 0.0
        translateY = 0.0
        if (shx != 0.0 || shy != 0.0) {
            state = (APPLY_SHEAR or APPLY_SCALE)
            type = TYPE_UNKNOWN
        } else {
            state = APPLY_IDENTITY
            type = TYPE_IDENTITY
        }
    }

    /**
     * Sets this transform to a copy of the transform in the specified
     * `AffineTransform` object.
     *
     * @param Tx the `AffineTransform` object from which to
     * copy the transform
     * @since 1.2
     */
    fun setTransform(Tx: AffineTransform) {
        this.scaleX = Tx.scaleX
        this.shearY = Tx.shearY
        this.shearX = Tx.shearX
        this.scaleY = Tx.scaleY
        this.translateX = Tx.translateX
        this.translateY = Tx.translateY
        this.state = Tx.state
        this.type = Tx.type
    }

    /**
     * Sets this transform to the matrix specified by the 6
     * double precision values.
     *
     * @param m00 the X coordinate scaling element of the 3x3 matrix
     * @param m10 the Y coordinate shearing element of the 3x3 matrix
     * @param m01 the X coordinate shearing element of the 3x3 matrix
     * @param m11 the Y coordinate scaling element of the 3x3 matrix
     * @param m02 the X coordinate translation element of the 3x3 matrix
     * @param m12 the Y coordinate translation element of the 3x3 matrix
     * @since 1.2
     */
    fun setTransform(
        m00: Double, m10: Double,
        m01: Double, m11: Double,
        m02: Double, m12: Double
    ) {
        this.scaleX = m00
        this.shearY = m10
        this.shearX = m01
        this.scaleY = m11
        this.translateX = m02
        this.translateY = m12
        updateState()
    }

    /**
     * Concatenates an `AffineTransform Tx` to
     * this `AffineTransform` Cx in the most commonly useful
     * way to provide a new user space
     * that is mapped to the former user space by `Tx`.
     * Cx is updated to perform the combined transformation.
     * Transforming a point p by the updated transform Cx' is
     * equivalent to first transforming p by `Tx` and then
     * transforming the result by the original transform Cx like this:
     * Cx'(p) = Cx(Tx(p))
     * In matrix notation, if this transform Cx is
     * represented by the matrix [this] and `Tx` is represented
     * by the matrix [Tx] then this method does the following:
     * <pre>
     * [this] = [this] x [Tx]
    </pre> *
     *
     * @param Tx the `AffineTransform` object to be
     * concatenated with this `AffineTransform` object.
     * @see .preConcatenate
     *
     * @since 1.2
     */
    fun concatenate(Tx: AffineTransform) {
        var M0: Double
        var M1: Double
        val T01: Double
        val T10: Double
        val mystate = state
        val txstate = Tx.state
        when ((txstate shl HI_SHIFT) or mystate) {
            HI_IDENTITY or APPLY_IDENTITY, HI_IDENTITY or APPLY_TRANSLATE, HI_IDENTITY or APPLY_SCALE, HI_IDENTITY or APPLY_SCALE or APPLY_TRANSLATE, HI_IDENTITY or APPLY_SHEAR, HI_IDENTITY or APPLY_SHEAR or APPLY_TRANSLATE, HI_IDENTITY or APPLY_SHEAR or APPLY_SCALE, HI_IDENTITY or APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE -> return

            HI_SHEAR or HI_SCALE or HI_TRANSLATE or APPLY_IDENTITY -> {
                shearX = Tx.shearX
                shearY = Tx.shearY
                scaleX = Tx.scaleX
                scaleY = Tx.scaleY
                translateX = Tx.translateX
                translateY = Tx.translateY
                state = txstate
                type = Tx.type
                return
            }

            HI_SCALE or HI_TRANSLATE or APPLY_IDENTITY -> {
                scaleX = Tx.scaleX
                scaleY = Tx.scaleY
                translateX = Tx.translateX
                translateY = Tx.translateY
                state = txstate
                type = Tx.type
                return
            }

            HI_TRANSLATE or APPLY_IDENTITY -> {
                translateX = Tx.translateX
                translateY = Tx.translateY
                state = txstate
                type = Tx.type
                return
            }

            HI_SHEAR or HI_SCALE or APPLY_IDENTITY -> {
                shearX = Tx.shearX
                shearY = Tx.shearY
                scaleX = Tx.scaleX
                scaleY = Tx.scaleY
                state = txstate
                type = Tx.type
                return
            }

            HI_SCALE or APPLY_IDENTITY -> {
                scaleX = Tx.scaleX
                scaleY = Tx.scaleY
                state = txstate
                type = Tx.type
                return
            }

            HI_SHEAR or HI_TRANSLATE or APPLY_IDENTITY -> {
                translateX = Tx.translateX
                translateY = Tx.translateY
                shearX = Tx.shearX
                shearY = Tx.shearY
                run {
                    this.scaleY = 0.0
                    this.scaleX = this.scaleY
                }
                state = txstate
                type = Tx.type
                return
            }

            HI_SHEAR or APPLY_IDENTITY -> {
                shearX = Tx.shearX
                shearY = Tx.shearY
                run {
                    this.scaleY = 0.0
                    this.scaleX = this.scaleY
                }
                state = txstate
                type = Tx.type
                return
            }

            HI_TRANSLATE or APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE, HI_TRANSLATE or APPLY_SHEAR or APPLY_SCALE, HI_TRANSLATE or APPLY_SHEAR or APPLY_TRANSLATE, HI_TRANSLATE or APPLY_SHEAR, HI_TRANSLATE or APPLY_SCALE or APPLY_TRANSLATE, HI_TRANSLATE or APPLY_SCALE, HI_TRANSLATE or APPLY_TRANSLATE -> {
                translate(Tx.translateX, Tx.translateY)
                return
            }

            HI_SCALE or APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE, HI_SCALE or APPLY_SHEAR or APPLY_SCALE, HI_SCALE or APPLY_SHEAR or APPLY_TRANSLATE, HI_SCALE or APPLY_SHEAR, HI_SCALE or APPLY_SCALE or APPLY_TRANSLATE, HI_SCALE or APPLY_SCALE, HI_SCALE or APPLY_TRANSLATE -> {
                scale(Tx.scaleX, Tx.scaleY)
                return
            }

            HI_SHEAR or APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE, HI_SHEAR or APPLY_SHEAR or APPLY_SCALE -> {
                T01 = Tx.shearX
                T10 = Tx.shearY
                M0 = scaleX
                scaleX = shearX * T10
                shearX = M0 * T01
                M0 = shearY
                shearY = scaleY * T10
                scaleY = M0 * T01
                type = TYPE_UNKNOWN
                return
            }

            HI_SHEAR or APPLY_SHEAR or APPLY_TRANSLATE, HI_SHEAR or APPLY_SHEAR -> {
                scaleX = shearX * Tx.shearY
                shearX = 0.0
                scaleY = shearY * Tx.shearX
                shearY = 0.0
                state = mystate xor (APPLY_SHEAR or APPLY_SCALE)
                type = TYPE_UNKNOWN
                return
            }

            HI_SHEAR or APPLY_SCALE or APPLY_TRANSLATE, HI_SHEAR or APPLY_SCALE -> {
                shearX = scaleX * Tx.shearX
                scaleX = 0.0
                shearY = scaleY * Tx.shearY
                scaleY = 0.0
                state = mystate xor (APPLY_SHEAR or APPLY_SCALE)
                type = TYPE_UNKNOWN
                return
            }

            HI_SHEAR or APPLY_TRANSLATE -> {
                scaleX = 0.0
                shearX = Tx.shearX
                shearY = Tx.shearY
                scaleY = 0.0
                state = APPLY_TRANSLATE or APPLY_SHEAR
                type = TYPE_UNKNOWN
                return
            }
        }
        // If Tx has more than one attribute, it is not worth optimizing
        // all of those cases...
        val T00 = Tx.scaleX
        T01 = Tx.shearX
        val T02 = Tx.translateX
        T10 = Tx.shearY
        val T11 = Tx.scaleY
        val T12 = Tx.translateY
        when (mystate) {
            APPLY_SHEAR or APPLY_SCALE -> {
                state = mystate or txstate
                M0 = scaleX
                M1 = shearX
                scaleX = T00 * M0 + T10 * M1
                shearX = T01 * M0 + T11 * M1
                translateX += T02 * M0 + T12 * M1

                M0 = shearY
                M1 = scaleY
                shearY = T00 * M0 + T10 * M1
                scaleY = T01 * M0 + T11 * M1
                translateY += T02 * M0 + T12 * M1
                type = TYPE_UNKNOWN
                return
            }

            APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE -> {
                M0 = scaleX
                M1 = shearX
                scaleX = T00 * M0 + T10 * M1
                shearX = T01 * M0 + T11 * M1
                translateX += T02 * M0 + T12 * M1

                M0 = shearY
                M1 = scaleY
                shearY = T00 * M0 + T10 * M1
                scaleY = T01 * M0 + T11 * M1
                translateY += T02 * M0 + T12 * M1
                type = TYPE_UNKNOWN
                return
            }

            APPLY_SHEAR or APPLY_TRANSLATE, APPLY_SHEAR -> {
                M0 = shearX
                scaleX = T10 * M0
                shearX = T11 * M0
                translateX += T12 * M0

                M0 = shearY
                shearY = T00 * M0
                scaleY = T01 * M0
                translateY += T02 * M0
            }

            APPLY_SCALE or APPLY_TRANSLATE, APPLY_SCALE -> {
                M0 = scaleX
                scaleX = T00 * M0
                shearX = T01 * M0
                translateX += T02 * M0

                M0 = scaleY
                shearY = T10 * M0
                scaleY = T11 * M0
                translateY += T12 * M0
            }

            APPLY_TRANSLATE -> {
                scaleX = T00
                shearX = T01
                translateX += T02

                shearY = T10
                scaleY = T11
                translateY += T12
                state = txstate or APPLY_TRANSLATE
                type = TYPE_UNKNOWN
                return
            }

            else -> {
                stateError()
                state = mystate or txstate
                M0 = scaleX
                M1 = shearX
                scaleX = T00 * M0 + T10 * M1
                shearX = T01 * M0 + T11 * M1
                translateX += T02 * M0 + T12 * M1

                M0 = shearY
                M1 = scaleY
                shearY = T00 * M0 + T10 * M1
                scaleY = T01 * M0 + T11 * M1
                translateY += T02 * M0 + T12 * M1
                type = TYPE_UNKNOWN
                return
            }
        }
        updateState()
    }

    /**
     * Concatenates an `AffineTransform Tx` to
     * this `AffineTransform` Cx
     * in a less commonly used way such that `Tx` modifies the
     * coordinate transformation relative to the absolute pixel
     * space rather than relative to the existing user space.
     * Cx is updated to perform the combined transformation.
     * Transforming a point p by the updated transform Cx' is
     * equivalent to first transforming p by the original transform
     * Cx and then transforming the result by
     * `Tx` like this:
     * Cx'(p) = Tx(Cx(p))
     * In matrix notation, if this transform Cx
     * is represented by the matrix [this] and `Tx` is
     * represented by the matrix [Tx] then this method does the
     * following:
     * <pre>
     * [this] = [Tx] x [this]
    </pre> *
     *
     * @param Tx the `AffineTransform` object to be
     * concatenated with this `AffineTransform` object.
     * @see .concatenate
     *
     * @since 1.2
     */
    fun preConcatenate(Tx: AffineTransform) {
        var M0: Double
        var M1: Double
        val T00: Double
        val T01: Double
        val T10: Double
        val T11: Double
        var mystate = state
        val txstate = Tx.state
        when ((txstate shl HI_SHIFT) or mystate) {
            HI_IDENTITY or APPLY_IDENTITY, HI_IDENTITY or APPLY_TRANSLATE, HI_IDENTITY or APPLY_SCALE, HI_IDENTITY or APPLY_SCALE or APPLY_TRANSLATE, HI_IDENTITY or APPLY_SHEAR, HI_IDENTITY or APPLY_SHEAR or APPLY_TRANSLATE, HI_IDENTITY or APPLY_SHEAR or APPLY_SCALE, HI_IDENTITY or APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE ->                 // Tx is IDENTITY...
                return

            HI_TRANSLATE or APPLY_IDENTITY, HI_TRANSLATE or APPLY_SCALE, HI_TRANSLATE or APPLY_SHEAR, HI_TRANSLATE or APPLY_SHEAR or APPLY_SCALE -> {
                // Tx is TRANSLATE, this has no TRANSLATE
                translateX = Tx.translateX
                translateY = Tx.translateY
                state = mystate or APPLY_TRANSLATE
                type = type or TYPE_TRANSLATION
                return
            }

            HI_TRANSLATE or APPLY_TRANSLATE, HI_TRANSLATE or APPLY_SCALE or APPLY_TRANSLATE, HI_TRANSLATE or APPLY_SHEAR or APPLY_TRANSLATE, HI_TRANSLATE or APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE -> {
                // Tx is TRANSLATE, this has one too
                translateX = translateX + Tx.translateX
                translateY = translateY + Tx.translateY
                return
            }

            HI_SCALE or APPLY_TRANSLATE, HI_SCALE or APPLY_IDENTITY -> {
                // Only these two existing states need a new state
                state = mystate or APPLY_SCALE
                // Tx is SCALE, this is anything
                T00 = Tx.scaleX
                T11 = Tx.scaleY
                if ((mystate and APPLY_SHEAR) != 0) {
                    shearX = shearX * T00
                    shearY = shearY * T11
                    if ((mystate and APPLY_SCALE) != 0) {
                        scaleX = scaleX * T00
                        scaleY = scaleY * T11
                    }
                } else {
                    scaleX = scaleX * T00
                    scaleY = scaleY * T11
                }
                if ((mystate and APPLY_TRANSLATE) != 0) {
                    translateX = translateX * T00
                    translateY = translateY * T11
                }
                type = TYPE_UNKNOWN
                return
            }

            HI_SCALE or APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE, HI_SCALE or APPLY_SHEAR or APPLY_SCALE, HI_SCALE or APPLY_SHEAR or APPLY_TRANSLATE, HI_SCALE or APPLY_SHEAR, HI_SCALE or APPLY_SCALE or APPLY_TRANSLATE, HI_SCALE or APPLY_SCALE -> {
                T00 = Tx.scaleX
                T11 = Tx.scaleY
                if ((mystate and APPLY_SHEAR) != 0) {
                    shearX = shearX * T00
                    shearY = shearY * T11
                    if ((mystate and APPLY_SCALE) != 0) {
                        scaleX = scaleX * T00
                        scaleY = scaleY * T11
                    }
                } else {
                    scaleX = scaleX * T00
                    scaleY = scaleY * T11
                }
                if ((mystate and APPLY_TRANSLATE) != 0) {
                    translateX = translateX * T00
                    translateY = translateY * T11
                }
                type = TYPE_UNKNOWN
                return
            }

            HI_SHEAR or APPLY_SHEAR or APPLY_TRANSLATE, HI_SHEAR or APPLY_SHEAR -> {
                mystate = mystate or APPLY_SCALE
                state = mystate xor APPLY_SHEAR
                // Tx is SHEAR, this is anything
                T01 = Tx.shearX
                T10 = Tx.shearY

                M0 = scaleX
                scaleX = shearY * T01
                shearY = M0 * T10

                M0 = shearX
                shearX = scaleY * T01
                scaleY = M0 * T10

                M0 = translateX
                translateX = translateY * T01
                translateY = M0 * T10
                type = TYPE_UNKNOWN
                return
            }

            HI_SHEAR or APPLY_TRANSLATE, HI_SHEAR or APPLY_IDENTITY, HI_SHEAR or APPLY_SCALE or APPLY_TRANSLATE, HI_SHEAR or APPLY_SCALE -> {
                state = mystate xor APPLY_SHEAR
                T01 = Tx.shearX
                T10 = Tx.shearY

                M0 = scaleX
                scaleX = shearY * T01
                shearY = M0 * T10

                M0 = shearX
                shearX = scaleY * T01
                scaleY = M0 * T10

                M0 = translateX
                translateX = translateY * T01
                translateY = M0 * T10
                type = TYPE_UNKNOWN
                return
            }

            HI_SHEAR or APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE, HI_SHEAR or APPLY_SHEAR or APPLY_SCALE -> {
                T01 = Tx.shearX
                T10 = Tx.shearY

                M0 = scaleX
                scaleX = shearY * T01
                shearY = M0 * T10

                M0 = shearX
                shearX = scaleY * T01
                scaleY = M0 * T10

                M0 = translateX
                translateX = translateY * T01
                translateY = M0 * T10
                type = TYPE_UNKNOWN
                return
            }
        }
        // If Tx has more than one attribute, it is not worth optimizing
        // all of those cases...
        T00 = Tx.scaleX
        T01 = Tx.shearX
        var T02 = Tx.translateX
        T10 = Tx.shearY
        T11 = Tx.scaleY
        var T12 = Tx.translateY
        when (mystate) {
            APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE -> {
                M0 = translateX
                M1 = translateY
                T02 += M0 * T00 + M1 * T01
                T12 += M0 * T10 + M1 * T11

                translateX = T02
                translateY = T12

                M0 = scaleX
                M1 = shearY
                scaleX = M0 * T00 + M1 * T01
                shearY = M0 * T10 + M1 * T11

                M0 = shearX
                M1 = scaleY
                shearX = M0 * T00 + M1 * T01
                scaleY = M0 * T10 + M1 * T11
            }

            APPLY_SHEAR or APPLY_SCALE -> {
                translateX = T02
                translateY = T12

                M0 = scaleX
                M1 = shearY
                scaleX = M0 * T00 + M1 * T01
                shearY = M0 * T10 + M1 * T11

                M0 = shearX
                M1 = scaleY
                shearX = M0 * T00 + M1 * T01
                scaleY = M0 * T10 + M1 * T11
            }

            APPLY_SHEAR or APPLY_TRANSLATE -> {
                M0 = translateX
                M1 = translateY
                T02 += M0 * T00 + M1 * T01
                T12 += M0 * T10 + M1 * T11

                translateX = T02
                translateY = T12

                M0 = shearY
                scaleX = M0 * T01
                shearY = M0 * T11

                M0 = shearX
                shearX = M0 * T00
                scaleY = M0 * T10
            }

            APPLY_SHEAR -> {
                translateX = T02
                translateY = T12

                M0 = shearY
                scaleX = M0 * T01
                shearY = M0 * T11

                M0 = shearX
                shearX = M0 * T00
                scaleY = M0 * T10
            }

            APPLY_SCALE or APPLY_TRANSLATE -> {
                M0 = translateX
                M1 = translateY
                T02 += M0 * T00 + M1 * T01
                T12 += M0 * T10 + M1 * T11

                translateX = T02
                translateY = T12

                M0 = scaleX
                scaleX = M0 * T00
                shearY = M0 * T10

                M0 = scaleY
                shearX = M0 * T01
                scaleY = M0 * T11
            }

            APPLY_SCALE -> {
                translateX = T02
                translateY = T12

                M0 = scaleX
                scaleX = M0 * T00
                shearY = M0 * T10

                M0 = scaleY
                shearX = M0 * T01
                scaleY = M0 * T11
            }

            APPLY_TRANSLATE -> {
                M0 = translateX
                M1 = translateY
                T02 += M0 * T00 + M1 * T01
                T12 += M0 * T10 + M1 * T11

                translateX = T02
                translateY = T12

                scaleX = T00
                shearY = T10

                shearX = T01
                scaleY = T11

                state = mystate or txstate
                type = TYPE_UNKNOWN
                return
            }

            APPLY_IDENTITY -> {
                translateX = T02
                translateY = T12

                scaleX = T00
                shearY = T10

                shearX = T01
                scaleY = T11

                state = mystate or txstate
                type = TYPE_UNKNOWN
                return
            }

            else -> {
                stateError()
                M0 = translateX
                M1 = translateY
                T02 += M0 * T00 + M1 * T01
                T12 += M0 * T10 + M1 * T11

                translateX = T02
                translateY = T12

                M0 = scaleX
                M1 = shearY
                scaleX = M0 * T00 + M1 * T01
                shearY = M0 * T10 + M1 * T11

                M0 = shearX
                M1 = scaleY
                shearX = M0 * T00 + M1 * T01
                scaleY = M0 * T10 + M1 * T11
            }
        }
        updateState()
    }

    /**
     * Returns an `AffineTransform` object representing the
     * inverse transformation.
     * The inverse transform Tx' of this transform Tx
     * maps coordinates transformed by Tx back
     * to their original coordinates.
     * In other words, Tx'(Tx(p)) = p = Tx(Tx'(p)).
     *
     *
     * If this transform maps all coordinates onto a point or a line
     * then it will not have an inverse, since coordinates that do
     * not lie on the destination point or line will not have an inverse
     * mapping.
     * The `getDeterminant` method can be used to determine if this
     * transform has no inverse, in which case an exception will be
     * thrown if the `createInverse` method is called.
     *
     * @return a new `AffineTransform` object representing the
     * inverse transformation.
     * @throws NonInvertibleTransformException if the matrix cannot be inverted.
     * @see .getDeterminant
     *
     * @since 1.2
     */
    @Throws(NonInvertibleTransformException::class)
    fun createInverse(): AffineTransform {
        val det: Double
        when (state) {
            APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE -> {
                det = scaleX * scaleY - shearX * shearY
                if (abs(det) <= Double.MIN_VALUE) {
                    throw NonInvertibleTransformException(
                        "Determinant is " +
                                det
                    )
                }
                return AffineTransform(
                    scaleY / det, -shearY / det,
                    -shearX / det, scaleX / det,
                    (shearX * translateY - scaleY * translateX) / det,
                    (shearY * translateX - scaleX * translateY) / det,
                    (APPLY_SHEAR or
                            APPLY_SCALE or
                            APPLY_TRANSLATE)
                )
            }

            APPLY_SHEAR or APPLY_SCALE -> {
                det = scaleX * scaleY - shearX * shearY
                if (abs(det) <= Double.MIN_VALUE) {
                    throw NonInvertibleTransformException(
                        "Determinant is " +
                                det
                    )
                }
                return AffineTransform(
                    scaleY / det, -shearY / det,
                    -shearX / det, scaleX / det,
                    0.0, 0.0,
                    (APPLY_SHEAR or APPLY_SCALE)
                )
            }

            APPLY_SHEAR or APPLY_TRANSLATE -> {
                if (shearX == 0.0 || shearY == 0.0) {
                    throw NonInvertibleTransformException("Determinant is 0")
                }
                return AffineTransform(
                    0.0, 1.0 / shearX,
                    1.0 / shearY, 0.0,
                    -translateY / shearY, -translateX / shearX,
                    (APPLY_SHEAR or APPLY_TRANSLATE)
                )
            }

            APPLY_SHEAR -> {
                if (shearX == 0.0 || shearY == 0.0) {
                    throw NonInvertibleTransformException("Determinant is 0")
                }
                return AffineTransform(
                    0.0, 1.0 / shearX,
                    1.0 / shearY, 0.0,
                    0.0, 0.0,
                    (APPLY_SHEAR)
                )
            }

            APPLY_SCALE or APPLY_TRANSLATE -> {
                if (scaleX == 0.0 || scaleY == 0.0) {
                    throw NonInvertibleTransformException("Determinant is 0")
                }
                return AffineTransform(
                    1.0 / scaleX, 0.0,
                    0.0, 1.0 / scaleY,
                    -translateX / scaleX, -translateY / scaleY,
                    (APPLY_SCALE or APPLY_TRANSLATE)
                )
            }

            APPLY_SCALE -> {
                if (scaleX == 0.0 || scaleY == 0.0) {
                    throw NonInvertibleTransformException("Determinant is 0")
                }
                return AffineTransform(
                    1.0 / scaleX, 0.0,
                    0.0, 1.0 / scaleY,
                    0.0, 0.0,
                    (APPLY_SCALE)
                )
            }

            APPLY_TRANSLATE -> return AffineTransform(
                1.0, 0.0,
                0.0, 1.0,
                -translateX, -translateY,
                (APPLY_TRANSLATE)
            )

            APPLY_IDENTITY -> return AffineTransform()
            else -> {
                stateError()
                /* NOTREACHED */
                return AffineTransform()
            }
        }        /* NOTREACHED */
    }

    /**
     * Sets this transform to the inverse of itself.
     * The inverse transform Tx' of this transform Tx
     * maps coordinates transformed by Tx back
     * to their original coordinates.
     * In other words, Tx'(Tx(p)) = p = Tx(Tx'(p)).
     *
     *
     * If this transform maps all coordinates onto a point or a line
     * then it will not have an inverse, since coordinates that do
     * not lie on the destination point or line will not have an inverse
     * mapping.
     * The `getDeterminant` method can be used to determine if this
     * transform has no inverse, in which case an exception will be
     * thrown if the `invert` method is called.
     *
     * @throws NonInvertibleTransformException if the matrix cannot be inverted.
     * @see .getDeterminant
     *
     * @since 1.6
     */
    @Throws(NonInvertibleTransformException::class)
    fun invert() {
        val M00: Double
        val M01: Double
        val M02: Double
        val M10: Double
        val M11: Double
        val M12: Double
        val det: Double
        when (state) {
            APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE -> {
                M00 = scaleX
                M01 = shearX
                M02 = translateX
                M10 = shearY
                M11 = scaleY
                M12 = translateY
                det = M00 * M11 - M01 * M10
                if (abs(det) <= Double.MIN_VALUE) {
                    throw NonInvertibleTransformException(
                        "Determinant is " +
                                det
                    )
                }
                scaleX = M11 / det
                shearY = -M10 / det
                shearX = -M01 / det
                scaleY = M00 / det
                translateX = (M01 * M12 - M11 * M02) / det
                translateY = (M10 * M02 - M00 * M12) / det
            }

            APPLY_SHEAR or APPLY_SCALE -> {
                M00 = scaleX
                M01 = shearX
                M10 = shearY
                M11 = scaleY
                det = M00 * M11 - M01 * M10
                if (abs(det) <= Double.MIN_VALUE) {
                    throw NonInvertibleTransformException(
                        "Determinant is " +
                                det
                    )
                }
                scaleX = M11 / det
                shearY = -M10 / det
                shearX = -M01 / det
                scaleY = M00 / det
            }

            APPLY_SHEAR or APPLY_TRANSLATE -> {
                M01 = shearX
                M02 = translateX
                M10 = shearY
                M12 = translateY
                if (M01 == 0.0 || M10 == 0.0) {
                    throw NonInvertibleTransformException("Determinant is 0")
                }
                // m00 = 0.0;
                shearY = 1.0 / M01
                shearX = 1.0 / M10
                // m11 = 0.0;
                translateX = -M12 / M10
                translateY = -M02 / M01
            }

            APPLY_SHEAR -> {
                M01 = shearX
                M10 = shearY
                if (M01 == 0.0 || M10 == 0.0) {
                    throw NonInvertibleTransformException("Determinant is 0")
                }
                // m00 = 0.0;
                shearY = 1.0 / M01
                shearX = 1.0 / M10
            }

            APPLY_SCALE or APPLY_TRANSLATE -> {
                M00 = scaleX
                M02 = translateX
                M11 = scaleY
                M12 = translateY
                if (M00 == 0.0 || M11 == 0.0) {
                    throw NonInvertibleTransformException("Determinant is 0")
                }
                scaleX = 1.0 / M00
                // m10 = 0.0;
                // m01 = 0.0;
                scaleY = 1.0 / M11
                translateX = -M02 / M00
                translateY = -M12 / M11
            }

            APPLY_SCALE -> {
                M00 = scaleX
                M11 = scaleY
                if (M00 == 0.0 || M11 == 0.0) {
                    throw NonInvertibleTransformException("Determinant is 0")
                }
                scaleX = 1.0 / M00
                // m10 = 0.0;
                // m01 = 0.0;
                scaleY = 1.0 / M11
            }

            APPLY_TRANSLATE -> {
                // m00 = 1.0;
                // m10 = 0.0;
                // m01 = 0.0;
                // m11 = 1.0;
                translateX = -translateX
                translateY = -translateY
            }

            APPLY_IDENTITY -> {}
            else -> {
                stateError()
                /* NOTREACHED */
                return
            }
        }
    }

    /**
     * Transforms the specified `ptSrc` and stores the result
     * in `ptDst`.
     * If `ptDst` is `null`, a new [Point2D]
     * object is allocated and then the result of the transformation is
     * stored in this object.
     * In either case, `ptDst`, which contains the
     * transformed point, is returned for convenience.
     * If `ptSrc` and `ptDst` are the same
     * object, the input point is correctly overwritten with
     * the transformed point.
     *
     * @param ptSrc the specified `Point2D` to be transformed
     * @param ptDst the specified `Point2D` that stores the
     * result of transforming `ptSrc`
     * @return the `ptDst` after transforming
     * `ptSrc` and storing the result in `ptDst`.
     * @since 1.2
     */
    fun transform(ptSrc: Point2D, ptDst: Point2D?): Point2D {
        var ptDst = ptDst
        if (ptDst == null) {
            ptDst = if (ptSrc is Point2DDouble) {
                Point2DDouble()
            } else {
                Point2DFloat()
            }
        }
        // Copy source coords into local variables in case src == dst
        val x = ptSrc.x
        val y = ptSrc.y
        when (state) {
            APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE -> {
                ptDst.setLocation(
                    x * scaleX + y * shearX + translateX,
                    x * shearY + y * scaleY + translateY
                )
                return ptDst
            }

            APPLY_SHEAR or APPLY_SCALE -> {
                ptDst.setLocation(x * scaleX + y * shearX, x * shearY + y * scaleY)
                return ptDst
            }

            APPLY_SHEAR or APPLY_TRANSLATE -> {
                ptDst.setLocation(y * shearX + translateX, x * shearY + translateY)
                return ptDst
            }

            APPLY_SHEAR -> {
                ptDst.setLocation(y * shearX, x * shearY)
                return ptDst
            }

            APPLY_SCALE or APPLY_TRANSLATE -> {
                ptDst.setLocation(x * scaleX + translateX, y * scaleY + translateY)
                return ptDst
            }

            APPLY_SCALE -> {
                ptDst.setLocation(x * scaleX, y * scaleY)
                return ptDst
            }

            APPLY_TRANSLATE -> {
                ptDst.setLocation(x + translateX, y + translateY)
                return ptDst
            }

            APPLY_IDENTITY -> {
                ptDst.setLocation(x, y)
                return ptDst
            }

            else -> {
                stateError()
                /* NOTREACHED */
                return Point2DDouble()
            }
        }        /* NOTREACHED */
    }

    /**
     * Transforms an array of point objects by this transform.
     * If any element of the `ptDst` array is
     * `null`, a new `Point2D` object is allocated
     * and stored into that element before storing the results of the
     * transformation.
     *
     *
     * Note that this method does not take any precautions to
     * avoid problems caused by storing results into `Point2D`
     * objects that will be used as the source for calculations
     * further down the source array.
     * This method does guarantee that if a specified `Point2D`
     * object is both the source and destination for the same single point
     * transform operation then the results will not be stored until
     * the calculations are complete to avoid storing the results on
     * top of the operands.
     * If, however, the destination `Point2D` object for one
     * operation is the same object as the source `Point2D`
     * object for another operation further down the source array then
     * the original coordinates in that point are overwritten before
     * they can be converted.
     *
     * @param ptSrc  the array containing the source point objects
     * @param ptDst  the array into which the transform point objects are
     * returned
     * @param srcOff the offset to the first point object to be
     * transformed in the source array
     * @param dstOff the offset to the location of the first
     * transformed point object that is stored in the destination array
     * @param numPts the number of point objects to be transformed
     * @since 1.2
     */
    fun transform(
        ptSrc: Array<Point2D>, srcOff: Int,
        ptDst: Array<Point2D?>, dstOff: Int,
        numPts: Int
    ) {
        var srcOff = srcOff
        var dstOff = dstOff
        var numPts = numPts
        val state = this.state
        while (--numPts >= 0) {
            // Copy source coords into local variables in case src == dst
            val src = ptSrc[srcOff++]
            val x = src.x
            val y = src.y
            var dst = ptDst[dstOff++]
            if (dst == null) {
                dst = if (src is Point2DDouble) {
                    Point2DDouble()
                } else {
                    Point2DFloat()
                }
                ptDst[dstOff - 1] = dst
            }
            when (state) {
                APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE -> dst.setLocation(
                    x * scaleX + y * shearX + translateX,
                    x * shearY + y * scaleY + translateY
                )

                APPLY_SHEAR or APPLY_SCALE -> dst.setLocation(
                    x * scaleX + y * shearX,
                    x * shearY + y * scaleY
                )

                APPLY_SHEAR or APPLY_TRANSLATE -> dst.setLocation(
                    y * shearX + translateX,
                    x * shearY + translateY
                )

                APPLY_SHEAR -> dst.setLocation(y * shearX, x * shearY)
                APPLY_SCALE or APPLY_TRANSLATE -> dst.setLocation(
                    x * scaleX + translateX,
                    y * scaleY + translateY
                )

                APPLY_SCALE -> dst.setLocation(x * scaleX, y * scaleY)
                APPLY_TRANSLATE -> dst.setLocation(x + translateX, y + translateY)
                APPLY_IDENTITY -> dst.setLocation(x, y)
                else -> {
                    stateError()
                    /* NOTREACHED */
                    return
                }
            }
        }

        /* NOTREACHED */
    }

    /**
     * Transforms an array of floating point coordinates by this transform.
     * The two coordinate array sections can be exactly the same or
     * can be overlapping sections of the same array without affecting the
     * validity of the results.
     * This method ensures that no source coordinates are overwritten by a
     * previous operation before they can be transformed.
     * The coordinates are stored in the arrays starting at the specified
     * offset in the order `[x0, y0, x1, y1, ..., xn, yn]`.
     *
     * @param srcPts the array containing the source point coordinates.
     * Each point is stored as a pair of x,&nbsp;y coordinates.
     * @param dstPts the array into which the transformed point coordinates
     * are returned.  Each point is stored as a pair of x,&nbsp;y
     * coordinates.
     * @param srcOff the offset to the first point to be transformed
     * in the source array
     * @param dstOff the offset to the location of the first
     * transformed point that is stored in the destination array
     * @param numPts the number of points to be transformed
     * @since 1.2
     */
    fun transform(
        srcPts: FloatArray, srcOff: Int,
        dstPts: FloatArray, dstOff: Int,
        numPts: Int
    ) {
        var srcOff = srcOff
        var dstOff = dstOff
        var numPts = numPts
        val M00: Double
        val M01: Double
        val M02: Double
        val M10: Double
        val M11: Double
        val M12: Double // For caching
        if (dstPts == srcPts && dstOff > srcOff && dstOff < srcOff + numPts * 2) {
            // If the arrays overlap partially with the destination higher
            // than the source and we transform the coordinates normally
            // we would overwrite some of the later source coordinates
            // with results of previous transformations.
            // To get around this we use arraycopy to copy the points
            // to their final destination with correct overwrite
            // handling and then transform them in place in the new
            // safer location.
            System.arraycopy(srcPts, srcOff, dstPts, dstOff, numPts * 2)
            // srcPts = dstPts;         // They are known to be equal.
            srcOff = dstOff
        }
        when (state) {
            APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE -> {
                M00 = scaleX
                M01 = shearX
                M02 = translateX
                M10 = shearY
                M11 = scaleY
                M12 = translateY
                while (--numPts >= 0) {
                    val x = srcPts[srcOff++].toDouble()
                    val y = srcPts[srcOff++].toDouble()
                    dstPts[dstOff++] = (M00 * x + M01 * y + M02).toFloat()
                    dstPts[dstOff++] = (M10 * x + M11 * y + M12).toFloat()
                }
                return
            }

            APPLY_SHEAR or APPLY_SCALE -> {
                M00 = scaleX
                M01 = shearX
                M10 = shearY
                M11 = scaleY
                while (--numPts >= 0) {
                    val x = srcPts[srcOff++].toDouble()
                    val y = srcPts[srcOff++].toDouble()
                    dstPts[dstOff++] = (M00 * x + M01 * y).toFloat()
                    dstPts[dstOff++] = (M10 * x + M11 * y).toFloat()
                }
                return
            }

            APPLY_SHEAR or APPLY_TRANSLATE -> {
                M01 = shearX
                M02 = translateX
                M10 = shearY
                M12 = translateY
                while (--numPts >= 0) {
                    val x = srcPts[srcOff++].toDouble()
                    dstPts[dstOff++] = (M01 * srcPts[srcOff++] + M02).toFloat()
                    dstPts[dstOff++] = (M10 * x + M12).toFloat()
                }
                return
            }

            APPLY_SHEAR -> {
                M01 = shearX
                M10 = shearY
                while (--numPts >= 0) {
                    val x = srcPts[srcOff++].toDouble()
                    dstPts[dstOff++] = (M01 * srcPts[srcOff++]).toFloat()
                    dstPts[dstOff++] = (M10 * x).toFloat()
                }
                return
            }

            APPLY_SCALE or APPLY_TRANSLATE -> {
                M00 = scaleX
                M02 = translateX
                M11 = scaleY
                M12 = translateY
                while (--numPts >= 0) {
                    dstPts[dstOff++] = (M00 * srcPts[srcOff++] + M02).toFloat()
                    dstPts[dstOff++] = (M11 * srcPts[srcOff++] + M12).toFloat()
                }
                return
            }

            APPLY_SCALE -> {
                M00 = scaleX
                M11 = scaleY
                while (--numPts >= 0) {
                    dstPts[dstOff++] = (M00 * srcPts[srcOff++]).toFloat()
                    dstPts[dstOff++] = (M11 * srcPts[srcOff++]).toFloat()
                }
                return
            }

            APPLY_TRANSLATE -> {
                M02 = translateX
                M12 = translateY
                while (--numPts >= 0) {
                    dstPts[dstOff++] = (srcPts[srcOff++] + M02).toFloat()
                    dstPts[dstOff++] = (srcPts[srcOff++] + M12).toFloat()
                }
                return
            }

            APPLY_IDENTITY -> if (srcPts != dstPts || srcOff != dstOff) {
                System.arraycopy(
                    srcPts, srcOff, dstPts, dstOff,
                    numPts * 2
                )
            }

            else -> {
                stateError()
                /* NOTREACHED */
                return
            }
        }        /* NOTREACHED */
    }

    /**
     * Transforms an array of double precision coordinates by this transform.
     * The two coordinate array sections can be exactly the same or
     * can be overlapping sections of the same array without affecting the
     * validity of the results.
     * This method ensures that no source coordinates are
     * overwritten by a previous operation before they can be transformed.
     * The coordinates are stored in the arrays starting at the indicated
     * offset in the order `[x0, y0, x1, y1, ..., xn, yn]`.
     *
     * @param srcPts the array containing the source point coordinates.
     * Each point is stored as a pair of x,&nbsp;y coordinates.
     * @param dstPts the array into which the transformed point
     * coordinates are returned.  Each point is stored as a pair of
     * x,&nbsp;y coordinates.
     * @param srcOff the offset to the first point to be transformed
     * in the source array
     * @param dstOff the offset to the location of the first
     * transformed point that is stored in the destination array
     * @param numPts the number of point objects to be transformed
     * @since 1.2
     */
    fun transform(
        srcPts: DoubleArray, srcOff: Int,
        dstPts: DoubleArray, dstOff: Int,
        numPts: Int
    ) {
        var srcOff = srcOff
        var dstOff = dstOff
        var numPts = numPts
        val M00: Double
        val M01: Double
        val M02: Double
        val M10: Double
        val M11: Double
        val M12: Double // For caching
        if (dstPts == srcPts && dstOff > srcOff && dstOff < srcOff + numPts * 2) {
            // If the arrays overlap partially with the destination higher
            // than the source and we transform the coordinates normally
            // we would overwrite some of the later source coordinates
            // with results of previous transformations.
            // To get around this we use arraycopy to copy the points
            // to their final destination with correct overwrite
            // handling and then transform them in place in the new
            // safer location.
            System.arraycopy(srcPts, srcOff, dstPts, dstOff, numPts * 2)
            // srcPts = dstPts;         // They are known to be equal.
            srcOff = dstOff
        }
        when (state) {
            APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE -> {
                M00 = scaleX
                M01 = shearX
                M02 = translateX
                M10 = shearY
                M11 = scaleY
                M12 = translateY
                while (--numPts >= 0) {
                    val x = srcPts[srcOff++]
                    val y = srcPts[srcOff++]
                    dstPts[dstOff++] = M00 * x + M01 * y + M02
                    dstPts[dstOff++] = M10 * x + M11 * y + M12
                }
                return
            }

            APPLY_SHEAR or APPLY_SCALE -> {
                M00 = scaleX
                M01 = shearX
                M10 = shearY
                M11 = scaleY
                while (--numPts >= 0) {
                    val x = srcPts[srcOff++]
                    val y = srcPts[srcOff++]
                    dstPts[dstOff++] = M00 * x + M01 * y
                    dstPts[dstOff++] = M10 * x + M11 * y
                }
                return
            }

            APPLY_SHEAR or APPLY_TRANSLATE -> {
                M01 = shearX
                M02 = translateX
                M10 = shearY
                M12 = translateY
                while (--numPts >= 0) {
                    val x = srcPts[srcOff++]
                    dstPts[dstOff++] = M01 * srcPts[srcOff++] + M02
                    dstPts[dstOff++] = M10 * x + M12
                }
                return
            }

            APPLY_SHEAR -> {
                M01 = shearX
                M10 = shearY
                while (--numPts >= 0) {
                    val x = srcPts[srcOff++]
                    dstPts[dstOff++] = M01 * srcPts[srcOff++]
                    dstPts[dstOff++] = M10 * x
                }
                return
            }

            APPLY_SCALE or APPLY_TRANSLATE -> {
                M00 = scaleX
                M02 = translateX
                M11 = scaleY
                M12 = translateY
                while (--numPts >= 0) {
                    dstPts[dstOff++] = M00 * srcPts[srcOff++] + M02
                    dstPts[dstOff++] = M11 * srcPts[srcOff++] + M12
                }
                return
            }

            APPLY_SCALE -> {
                M00 = scaleX
                M11 = scaleY
                while (--numPts >= 0) {
                    dstPts[dstOff++] = M00 * srcPts[srcOff++]
                    dstPts[dstOff++] = M11 * srcPts[srcOff++]
                }
                return
            }

            APPLY_TRANSLATE -> {
                M02 = translateX
                M12 = translateY
                while (--numPts >= 0) {
                    dstPts[dstOff++] = srcPts[srcOff++] + M02
                    dstPts[dstOff++] = srcPts[srcOff++] + M12
                }
                return
            }

            APPLY_IDENTITY -> if (srcPts != dstPts || srcOff != dstOff) {
                System.arraycopy(
                    srcPts, srcOff, dstPts, dstOff,
                    numPts * 2
                )
            }

            else -> {
                stateError()
                /* NOTREACHED */
                return
            }
        }        /* NOTREACHED */
    }

    /**
     * Transforms an array of floating point coordinates by this transform
     * and stores the results into an array of doubles.
     * The coordinates are stored in the arrays starting at the specified
     * offset in the order `[x0, y0, x1, y1, ..., xn, yn]`.
     *
     * @param srcPts the array containing the source point coordinates.
     * Each point is stored as a pair of x,&nbsp;y coordinates.
     * @param dstPts the array into which the transformed point coordinates
     * are returned.  Each point is stored as a pair of x,&nbsp;y
     * coordinates.
     * @param srcOff the offset to the first point to be transformed
     * in the source array
     * @param dstOff the offset to the location of the first
     * transformed point that is stored in the destination array
     * @param numPts the number of points to be transformed
     * @since 1.2
     */
    fun transform(
        srcPts: FloatArray, srcOff: Int,
        dstPts: DoubleArray, dstOff: Int,
        numPts: Int
    ) {
        var srcOff = srcOff
        var dstOff = dstOff
        var numPts = numPts
        val M00: Double
        val M01: Double
        val M02: Double
        val M10: Double
        val M11: Double
        val M12: Double // For caching
        when (state) {
            APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE -> {
                M00 = scaleX
                M01 = shearX
                M02 = translateX
                M10 = shearY
                M11 = scaleY
                M12 = translateY
                while (--numPts >= 0) {
                    val x = srcPts[srcOff++].toDouble()
                    val y = srcPts[srcOff++].toDouble()
                    dstPts[dstOff++] = M00 * x + M01 * y + M02
                    dstPts[dstOff++] = M10 * x + M11 * y + M12
                }
                return
            }

            APPLY_SHEAR or APPLY_SCALE -> {
                M00 = scaleX
                M01 = shearX
                M10 = shearY
                M11 = scaleY
                while (--numPts >= 0) {
                    val x = srcPts[srcOff++].toDouble()
                    val y = srcPts[srcOff++].toDouble()
                    dstPts[dstOff++] = M00 * x + M01 * y
                    dstPts[dstOff++] = M10 * x + M11 * y
                }
                return
            }

            APPLY_SHEAR or APPLY_TRANSLATE -> {
                M01 = shearX
                M02 = translateX
                M10 = shearY
                M12 = translateY
                while (--numPts >= 0) {
                    val x = srcPts[srcOff++].toDouble()
                    dstPts[dstOff++] = M01 * srcPts[srcOff++] + M02
                    dstPts[dstOff++] = M10 * x + M12
                }
                return
            }

            APPLY_SHEAR -> {
                M01 = shearX
                M10 = shearY
                while (--numPts >= 0) {
                    val x = srcPts[srcOff++].toDouble()
                    dstPts[dstOff++] = M01 * srcPts[srcOff++]
                    dstPts[dstOff++] = M10 * x
                }
                return
            }

            APPLY_SCALE or APPLY_TRANSLATE -> {
                M00 = scaleX
                M02 = translateX
                M11 = scaleY
                M12 = translateY
                while (--numPts >= 0) {
                    dstPts[dstOff++] = M00 * srcPts[srcOff++] + M02
                    dstPts[dstOff++] = M11 * srcPts[srcOff++] + M12
                }
                return
            }

            APPLY_SCALE -> {
                M00 = scaleX
                M11 = scaleY
                while (--numPts >= 0) {
                    dstPts[dstOff++] = M00 * srcPts[srcOff++]
                    dstPts[dstOff++] = M11 * srcPts[srcOff++]
                }
                return
            }

            APPLY_TRANSLATE -> {
                M02 = translateX
                M12 = translateY
                while (--numPts >= 0) {
                    dstPts[dstOff++] = srcPts[srcOff++] + M02
                    dstPts[dstOff++] = srcPts[srcOff++] + M12
                }
                return
            }

            APPLY_IDENTITY -> while (--numPts >= 0) {
                dstPts[dstOff++] = srcPts[srcOff++].toDouble()
                dstPts[dstOff++] = srcPts[srcOff++].toDouble()
            }

            else -> {
                stateError()
                /* NOTREACHED */
                return
            }
        }        /* NOTREACHED */
    }

    /**
     * Transforms an array of double precision coordinates by this transform
     * and stores the results into an array of floats.
     * The coordinates are stored in the arrays starting at the specified
     * offset in the order `[x0, y0, x1, y1, ..., xn, yn]`.
     *
     * @param srcPts the array containing the source point coordinates.
     * Each point is stored as a pair of x,&nbsp;y coordinates.
     * @param dstPts the array into which the transformed point
     * coordinates are returned.  Each point is stored as a pair of
     * x,&nbsp;y coordinates.
     * @param srcOff the offset to the first point to be transformed
     * in the source array
     * @param dstOff the offset to the location of the first
     * transformed point that is stored in the destination array
     * @param numPts the number of point objects to be transformed
     * @since 1.2
     */
    fun transform(
        srcPts: DoubleArray, srcOff: Int,
        dstPts: FloatArray, dstOff: Int,
        numPts: Int
    ) {
        var srcOff = srcOff
        var dstOff = dstOff
        var numPts = numPts
        val M00: Double
        val M01: Double
        val M02: Double
        val M10: Double
        val M11: Double
        val M12: Double // For caching
        when (state) {
            APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE -> {
                M00 = scaleX
                M01 = shearX
                M02 = translateX
                M10 = shearY
                M11 = scaleY
                M12 = translateY
                while (--numPts >= 0) {
                    val x = srcPts[srcOff++]
                    val y = srcPts[srcOff++]
                    dstPts[dstOff++] = (M00 * x + M01 * y + M02).toFloat()
                    dstPts[dstOff++] = (M10 * x + M11 * y + M12).toFloat()
                }
                return
            }

            APPLY_SHEAR or APPLY_SCALE -> {
                M00 = scaleX
                M01 = shearX
                M10 = shearY
                M11 = scaleY
                while (--numPts >= 0) {
                    val x = srcPts[srcOff++]
                    val y = srcPts[srcOff++]
                    dstPts[dstOff++] = (M00 * x + M01 * y).toFloat()
                    dstPts[dstOff++] = (M10 * x + M11 * y).toFloat()
                }
                return
            }

            APPLY_SHEAR or APPLY_TRANSLATE -> {
                M01 = shearX
                M02 = translateX
                M10 = shearY
                M12 = translateY
                while (--numPts >= 0) {
                    val x = srcPts[srcOff++]
                    dstPts[dstOff++] = (M01 * srcPts[srcOff++] + M02).toFloat()
                    dstPts[dstOff++] = (M10 * x + M12).toFloat()
                }
                return
            }

            APPLY_SHEAR -> {
                M01 = shearX
                M10 = shearY
                while (--numPts >= 0) {
                    val x = srcPts[srcOff++]
                    dstPts[dstOff++] = (M01 * srcPts[srcOff++]).toFloat()
                    dstPts[dstOff++] = (M10 * x).toFloat()
                }
                return
            }

            APPLY_SCALE or APPLY_TRANSLATE -> {
                M00 = scaleX
                M02 = translateX
                M11 = scaleY
                M12 = translateY
                while (--numPts >= 0) {
                    dstPts[dstOff++] = (M00 * srcPts[srcOff++] + M02).toFloat()
                    dstPts[dstOff++] = (M11 * srcPts[srcOff++] + M12).toFloat()
                }
                return
            }

            APPLY_SCALE -> {
                M00 = scaleX
                M11 = scaleY
                while (--numPts >= 0) {
                    dstPts[dstOff++] = (M00 * srcPts[srcOff++]).toFloat()
                    dstPts[dstOff++] = (M11 * srcPts[srcOff++]).toFloat()
                }
                return
            }

            APPLY_TRANSLATE -> {
                M02 = translateX
                M12 = translateY
                while (--numPts >= 0) {
                    dstPts[dstOff++] = (srcPts[srcOff++] + M02).toFloat()
                    dstPts[dstOff++] = (srcPts[srcOff++] + M12).toFloat()
                }
                return
            }

            APPLY_IDENTITY -> while (--numPts >= 0) {
                dstPts[dstOff++] = srcPts[srcOff++].toFloat()
                dstPts[dstOff++] = srcPts[srcOff++].toFloat()
            }

            else -> {
                stateError()
                /* NOTREACHED */
                return
            }
        }        /* NOTREACHED */
    }

    /**
     * Inverse transforms the specified `ptSrc` and stores the
     * result in `ptDst`.
     * If `ptDst` is `null`, a new
     * `Point2D` object is allocated and then the result of the
     * transform is stored in this object.
     * In either case, `ptDst`, which contains the transformed
     * point, is returned for convenience.
     * If `ptSrc` and `ptDst` are the same
     * object, the input point is correctly overwritten with the
     * transformed point.
     *
     * @param ptSrc the point to be inverse transformed
     * @param ptDst the resulting transformed point
     * @return `ptDst`, which contains the result of the
     * inverse transform.
     * @throws NonInvertibleTransformException if the matrix cannot be
     * inverted.
     * @since 1.2
     */
    @Throws(NonInvertibleTransformException::class)
    fun inverseTransform(ptSrc: Point2D, ptDst: Point2D?): Point2D {
        var ptDst = ptDst
        if (ptDst == null) {
            ptDst = if (ptSrc is Point2DDouble) {
                Point2DDouble()
            } else {
                Point2DFloat()
            }
        }
        // Copy source coords into local variables in case src == dst
        var x = ptSrc.x
        var y = ptSrc.y
        when (state) {
            APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE -> {
                x -= translateX
                y -= translateY
                val det = scaleX * scaleY - shearX * shearY
                if (abs(det) <= Double.MIN_VALUE) {
                    throw NonInvertibleTransformException(
                        "Determinant is " +
                                det
                    )
                }
                ptDst.setLocation(
                    (x * scaleY - y * shearX) / det,
                    (y * scaleX - x * shearY) / det
                )
                return ptDst
            }

            APPLY_SHEAR or APPLY_SCALE -> {
                val det = scaleX * scaleY - shearX * shearY
                if (abs(det) <= Double.MIN_VALUE) {
                    throw NonInvertibleTransformException(
                        "Determinant is " +
                                det
                    )
                }
                ptDst.setLocation(
                    (x * scaleY - y * shearX) / det,
                    (y * scaleX - x * shearY) / det
                )
                return ptDst
            }

            APPLY_SHEAR or APPLY_TRANSLATE -> {
                x -= translateX
                y -= translateY
                if (shearX == 0.0 || shearY == 0.0) {
                    throw NonInvertibleTransformException("Determinant is 0")
                }
                ptDst.setLocation(y / shearY, x / shearX)
                return ptDst
            }

            APPLY_SHEAR -> {
                if (shearX == 0.0 || shearY == 0.0) {
                    throw NonInvertibleTransformException("Determinant is 0")
                }
                ptDst.setLocation(y / shearY, x / shearX)
                return ptDst
            }

            APPLY_SCALE or APPLY_TRANSLATE -> {
                x -= translateX
                y -= translateY
                if (scaleX == 0.0 || scaleY == 0.0) {
                    throw NonInvertibleTransformException("Determinant is 0")
                }
                ptDst.setLocation(x / scaleX, y / scaleY)
                return ptDst
            }

            APPLY_SCALE -> {
                if (scaleX == 0.0 || scaleY == 0.0) {
                    throw NonInvertibleTransformException("Determinant is 0")
                }
                ptDst.setLocation(x / scaleX, y / scaleY)
                return ptDst
            }

            APPLY_TRANSLATE -> {
                ptDst.setLocation(x - translateX, y - translateY)
                return ptDst
            }

            APPLY_IDENTITY -> {
                ptDst.setLocation(x, y)
                return ptDst
            }

            else -> {
                stateError()
                x -= translateX
                y -= translateY
                val det = scaleX * scaleY - shearX * shearY
                if (abs(det) <= Double.MIN_VALUE) {
                    throw NonInvertibleTransformException(
                        "Determinant is " +
                                det
                    )
                }
                ptDst.setLocation(
                    (x * scaleY - y * shearX) / det,
                    (y * scaleX - x * shearY) / det
                )
                return ptDst
            }
        }        /* NOTREACHED */
    }

    /**
     * Inverse transforms an array of double precision coordinates by
     * this transform.
     * The two coordinate array sections can be exactly the same or
     * can be overlapping sections of the same array without affecting the
     * validity of the results.
     * This method ensures that no source coordinates are
     * overwritten by a previous operation before they can be transformed.
     * The coordinates are stored in the arrays starting at the specified
     * offset in the order `[x0, y0, x1, y1, ..., xn, yn]`.
     *
     * @param srcPts the array containing the source point coordinates.
     * Each point is stored as a pair of x,&nbsp;y coordinates.
     * @param dstPts the array into which the transformed point
     * coordinates are returned.  Each point is stored as a pair of
     * x,&nbsp;y coordinates.
     * @param srcOff the offset to the first point to be transformed
     * in the source array
     * @param dstOff the offset to the location of the first
     * transformed point that is stored in the destination array
     * @param numPts the number of point objects to be transformed
     * @throws NonInvertibleTransformException if the matrix cannot be
     * inverted.
     * @since 1.2
     */
    @Throws(NonInvertibleTransformException::class)
    fun inverseTransform(
        srcPts: DoubleArray, srcOff: Int,
        dstPts: DoubleArray, dstOff: Int,
        numPts: Int
    ) {
        var srcOff = srcOff
        var dstOff = dstOff
        var numPts = numPts
        val M00: Double
        val M01: Double
        val M02: Double
        val M10: Double
        val M11: Double
        val M12: Double // For caching
        val det: Double
        if (dstPts == srcPts && dstOff > srcOff && dstOff < srcOff + numPts * 2) {
            // If the arrays overlap partially with the destination higher
            // than the source and we transform the coordinates normally
            // we would overwrite some of the later source coordinates
            // with results of previous transformations.
            // To get around this we use arraycopy to copy the points
            // to their final destination with correct overwrite
            // handling and then transform them in place in the new
            // safer location.
            System.arraycopy(srcPts, srcOff, dstPts, dstOff, numPts * 2)
            // srcPts = dstPts;         // They are known to be equal.
            srcOff = dstOff
        }
        when (state) {
            APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE -> {
                M00 = scaleX
                M01 = shearX
                M02 = translateX
                M10 = shearY
                M11 = scaleY
                M12 = translateY
                det = M00 * M11 - M01 * M10
                if (abs(det) <= Double.MIN_VALUE) {
                    throw NonInvertibleTransformException(
                        "Determinant is " +
                                det
                    )
                }
                while (--numPts >= 0) {
                    val x = srcPts[srcOff++] - M02
                    val y = srcPts[srcOff++] - M12
                    dstPts[dstOff++] = (x * M11 - y * M01) / det
                    dstPts[dstOff++] = (y * M00 - x * M10) / det
                }
                return
            }

            APPLY_SHEAR or APPLY_SCALE -> {
                M00 = scaleX
                M01 = shearX
                M10 = shearY
                M11 = scaleY
                det = M00 * M11 - M01 * M10
                if (abs(det) <= Double.MIN_VALUE) {
                    throw NonInvertibleTransformException(
                        "Determinant is " +
                                det
                    )
                }
                while (--numPts >= 0) {
                    val x = srcPts[srcOff++]
                    val y = srcPts[srcOff++]
                    dstPts[dstOff++] = (x * M11 - y * M01) / det
                    dstPts[dstOff++] = (y * M00 - x * M10) / det
                }
                return
            }

            APPLY_SHEAR or APPLY_TRANSLATE -> {
                M01 = shearX
                M02 = translateX
                M10 = shearY
                M12 = translateY
                if (M01 == 0.0 || M10 == 0.0) {
                    throw NonInvertibleTransformException("Determinant is 0")
                }
                while (--numPts >= 0) {
                    val x = srcPts[srcOff++] - M02
                    dstPts[dstOff++] = (srcPts[srcOff++] - M12) / M10
                    dstPts[dstOff++] = x / M01
                }
                return
            }

            APPLY_SHEAR -> {
                M01 = shearX
                M10 = shearY
                if (M01 == 0.0 || M10 == 0.0) {
                    throw NonInvertibleTransformException("Determinant is 0")
                }
                while (--numPts >= 0) {
                    val x = srcPts[srcOff++]
                    dstPts[dstOff++] = srcPts[srcOff++] / M10
                    dstPts[dstOff++] = x / M01
                }
                return
            }

            APPLY_SCALE or APPLY_TRANSLATE -> {
                M00 = scaleX
                M02 = translateX
                M11 = scaleY
                M12 = translateY
                if (M00 == 0.0 || M11 == 0.0) {
                    throw NonInvertibleTransformException("Determinant is 0")
                }
                while (--numPts >= 0) {
                    dstPts[dstOff++] = (srcPts[srcOff++] - M02) / M00
                    dstPts[dstOff++] = (srcPts[srcOff++] - M12) / M11
                }
                return
            }

            APPLY_SCALE -> {
                M00 = scaleX
                M11 = scaleY
                if (M00 == 0.0 || M11 == 0.0) {
                    throw NonInvertibleTransformException("Determinant is 0")
                }
                while (--numPts >= 0) {
                    dstPts[dstOff++] = srcPts[srcOff++] / M00
                    dstPts[dstOff++] = srcPts[srcOff++] / M11
                }
                return
            }

            APPLY_TRANSLATE -> {
                M02 = translateX
                M12 = translateY
                while (--numPts >= 0) {
                    dstPts[dstOff++] = srcPts[srcOff++] - M02
                    dstPts[dstOff++] = srcPts[srcOff++] - M12
                }
                return
            }

            APPLY_IDENTITY -> if (srcPts != dstPts || srcOff != dstOff) {
                System.arraycopy(
                    srcPts, srcOff, dstPts, dstOff,
                    numPts * 2
                )
            }

            else -> {
                stateError()
                /* NOTREACHED */
                return
            }
        }        /* NOTREACHED */
    }

    /**
     * Transforms the relative distance vector specified by
     * `ptSrc` and stores the result in `ptDst`.
     * A relative distance vector is transformed without applying the
     * translation components of the affine transformation matrix
     * using the following equations:
     * <pre>
     * [  x' ]   [  m00  m01 (m02) ] [  x  ]   [ m00x + m01y ]
     * [  y' ] = [  m10  m11 (m12) ] [  y  ] = [ m10x + m11y ]
     * [ (1) ]   [  (0)  (0) ( 1 ) ] [ (1) ]   [     (1)     ]
    </pre> *
     * If `ptDst` is `null`, a new
     * `Point2D` object is allocated and then the result of the
     * transform is stored in this object.
     * In either case, `ptDst`, which contains the
     * transformed point, is returned for convenience.
     * If `ptSrc` and `ptDst` are the same object,
     * the input point is correctly overwritten with the transformed
     * point.
     *
     * @param ptSrc the distance vector to be delta transformed
     * @param ptDst the resulting transformed distance vector
     * @return `ptDst`, which contains the result of the
     * transformation.
     * @since 1.2
     */
    fun deltaTransform(ptSrc: Point2D, ptDst: Point2D?): Point2D {
        var ptDst = ptDst
        if (ptDst == null) {
            ptDst = if (ptSrc is Point2DDouble) {
                Point2DDouble()
            } else {
                Point2DFloat()
            }
        }
        // Copy source coords into local variables in case src == dst
        val x = ptSrc.x
        val y = ptSrc.y
        when (state) {
            APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE, APPLY_SHEAR or APPLY_SCALE -> {
                ptDst.setLocation(x * scaleX + y * shearX, x * shearY + y * scaleY)
                return ptDst
            }

            APPLY_SHEAR or APPLY_TRANSLATE, APPLY_SHEAR -> {
                ptDst.setLocation(y * shearX, x * shearY)
                return ptDst
            }

            APPLY_SCALE or APPLY_TRANSLATE, APPLY_SCALE -> {
                ptDst.setLocation(x * scaleX, y * scaleY)
                return ptDst
            }

            APPLY_TRANSLATE, APPLY_IDENTITY -> {
                ptDst.setLocation(x, y)
                return ptDst
            }

            else -> {
                stateError()
                /* NOTREACHED */
                return Point2DDouble()
            }
        }        /* NOTREACHED */
    }

    /**
     * Transforms an array of relative distance vectors by this
     * transform.
     * A relative distance vector is transformed without applying the
     * translation components of the affine transformation matrix
     * using the following equations:
     * <pre>
     * [  x' ]   [  m00  m01 (m02) ] [  x  ]   [ m00x + m01y ]
     * [  y' ] = [  m10  m11 (m12) ] [  y  ] = [ m10x + m11y ]
     * [ (1) ]   [  (0)  (0) ( 1 ) ] [ (1) ]   [     (1)     ]
    </pre> *
     * The two coordinate array sections can be exactly the same or
     * can be overlapping sections of the same array without affecting the
     * validity of the results.
     * This method ensures that no source coordinates are
     * overwritten by a previous operation before they can be transformed.
     * The coordinates are stored in the arrays starting at the indicated
     * offset in the order `[x0, y0, x1, y1, ..., xn, yn]`.
     *
     * @param srcPts the array containing the source distance vectors.
     * Each vector is stored as a pair of relative x,&nbsp;y coordinates.
     * @param dstPts the array into which the transformed distance vectors
     * are returned.  Each vector is stored as a pair of relative
     * x,&nbsp;y coordinates.
     * @param srcOff the offset to the first vector to be transformed
     * in the source array
     * @param dstOff the offset to the location of the first
     * transformed vector that is stored in the destination array
     * @param numPts the number of vector coordinate pairs to be
     * transformed
     * @since 1.2
     */
    fun deltaTransform(
        srcPts: DoubleArray, srcOff: Int,
        dstPts: DoubleArray, dstOff: Int,
        numPts: Int
    ) {
        var srcOff = srcOff
        var dstOff = dstOff
        var numPts = numPts
        val M00: Double
        val M01: Double
        val M10: Double
        val M11: Double // For caching
        if (dstPts == srcPts && dstOff > srcOff && dstOff < srcOff + numPts * 2) {
            // If the arrays overlap partially with the destination higher
            // than the source and we transform the coordinates normally
            // we would overwrite some of the later source coordinates
            // with results of previous transformations.
            // To get around this we use arraycopy to copy the points
            // to their final destination with correct overwrite
            // handling and then transform them in place in the new
            // safer location.
            System.arraycopy(srcPts, srcOff, dstPts, dstOff, numPts * 2)
            // srcPts = dstPts;         // They are known to be equal.
            srcOff = dstOff
        }
        when (state) {
            APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE, APPLY_SHEAR or APPLY_SCALE -> {
                M00 = scaleX
                M01 = shearX
                M10 = shearY
                M11 = scaleY
                while (--numPts >= 0) {
                    val x = srcPts[srcOff++]
                    val y = srcPts[srcOff++]
                    dstPts[dstOff++] = x * M00 + y * M01
                    dstPts[dstOff++] = x * M10 + y * M11
                }
                return
            }

            APPLY_SHEAR or APPLY_TRANSLATE, APPLY_SHEAR -> {
                M01 = shearX
                M10 = shearY
                while (--numPts >= 0) {
                    val x = srcPts[srcOff++]
                    dstPts[dstOff++] = srcPts[srcOff++] * M01
                    dstPts[dstOff++] = x * M10
                }
                return
            }

            APPLY_SCALE or APPLY_TRANSLATE, APPLY_SCALE -> {
                M00 = scaleX
                M11 = scaleY
                while (--numPts >= 0) {
                    dstPts[dstOff++] = srcPts[srcOff++] * M00
                    dstPts[dstOff++] = srcPts[srcOff++] * M11
                }
                return
            }

            APPLY_TRANSLATE, APPLY_IDENTITY -> if (srcPts != dstPts || srcOff != dstOff) {
                System.arraycopy(
                    srcPts, srcOff, dstPts, dstOff,
                    numPts * 2
                )
            }

            else -> {
                stateError()
                /* NOTREACHED */
                return
            }
        }        /* NOTREACHED */
    }

    /**
     * Returns a new [Shape] object defined by the geometry of the
     * specified `Shape` after it has been transformed by
     * this transform.
     *
     * @param pSrc the specified `Shape` object to be
     * transformed by this transform.
     * @return a new `Shape` object that defines the geometry
     * of the transformed `Shape`, or null if `pSrc` is null.
     * @since 1.2
     */
    fun createTransformedShape(pSrc: Shape?): Shape? {
        if (pSrc == null) {
            return null
        }
        return Path2DDouble(pSrc, this)
    }

    /**
     * Returns a `String` that represents the value of this
     * [Object].
     *
     * @return a `String` representing the value of this
     * `Object`.
     * @since 1.2
     */
    override fun toString(): String {
        return ("AffineTransform[["
                + _matround(
            scaleX
        ) + ", "
                + _matround(
            shearX
        ) + ", "
                + _matround(
            translateX
        ) + "], ["
                + _matround(
            shearY
        ) + ", "
                + _matround(
            scaleY
        ) + ", "
                + _matround(
            translateY
        ) + "]]")
    }

    val isIdentity: Boolean
        /**
         * Returns `true` if this `AffineTransform` is
         * an identity transform.
         *
         * @return `true` if this `AffineTransform` is
         * an identity transform; `false` otherwise.
         * @since 1.2
         */
        get() = (state == APPLY_IDENTITY || (getType() == TYPE_IDENTITY))

    /**
     * Returns a copy of this `AffineTransform` object.
     *
     * @return an `Object` that is a copy of this
     * `AffineTransform` object.
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
     * Returns the hashcode for this transform.
     *
     * @return a hash code for this transform.
     * @since 1.2
     */
    override fun hashCode(): Int {
        var bits = java.lang.Double.doubleToLongBits(scaleX)
        bits = bits * 31 + java.lang.Double.doubleToLongBits(shearX)
        bits = bits * 31 + java.lang.Double.doubleToLongBits(translateX)
        bits = bits * 31 + java.lang.Double.doubleToLongBits(shearY)
        bits = bits * 31 + java.lang.Double.doubleToLongBits(scaleY)
        bits = bits * 31 + java.lang.Double.doubleToLongBits(translateY)
        return ((bits.toInt()) xor ((bits shr 32).toInt()))
    }

    /**
     * Returns `true` if this `AffineTransform`
     * represents the same affine coordinate transform as the specified
     * argument.
     *
     * @param obj the `Object` to test for equality with this
     * `AffineTransform`
     * @return `true` if `obj` equals this
     * `AffineTransform` object; `false` otherwise.
     * @since 1.2
     */
    override fun equals(obj: Any?): Boolean {
        if (obj !is AffineTransform) {
            return false
        }

        val a = obj

        return ((scaleX == a.scaleX) && (shearX == a.shearX) && (translateX == a.translateX) &&
                (shearY == a.shearY) && (scaleY == a.scaleY) && (translateY == a.translateY))
    }

    /**
     * Writes default serializable fields to stream.
     *
     * @param s the `ObjectOutputStream` to write
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    private fun writeObject(s: ObjectOutputStream) {
        s.defaultWriteObject()
    }

    /**
     * Reads the `ObjectInputStream`.
     *
     * @param s the `ObjectInputStream` to read
     * @throws ClassNotFoundException if the class of a serialized object could
     * not be found
     * @throws IOException            if an I/O error occurs
     */
    @Throws(ClassNotFoundException::class, IOException::class)
    private fun readObject(s: ObjectInputStream) {
        s.defaultReadObject()
        updateState()
    }

    companion object {
        /*
     * This constant is only useful for the cached type field.
     * It indicates that the type has been decached and must be recalculated.
     */
        private const val TYPE_UNKNOWN = -1

        /**
         * This constant indicates that the transform defined by this object
         * is an identity transform.
         * An identity transform is one in which the output coordinates are
         * always the same as the input coordinates.
         * If this transform is anything other than the identity transform,
         * the type will either be the constant GENERAL_TRANSFORM or a
         * combination of the appropriate flag bits for the various coordinate
         * conversions that this transform performs.
         *
         * @see .TYPE_TRANSLATION
         *
         * @see .TYPE_UNIFORM_SCALE
         *
         * @see .TYPE_GENERAL_SCALE
         *
         * @see .TYPE_FLIP
         *
         * @see .TYPE_QUADRANT_ROTATION
         *
         * @see .TYPE_GENERAL_ROTATION
         *
         * @see .TYPE_GENERAL_TRANSFORM
         *
         * @see .getType
         *
         * @since 1.2
         */
        const val TYPE_IDENTITY: Int = 0

        /**
         * This flag bit indicates that the transform defined by this object
         * performs a translation in addition to the conversions indicated
         * by other flag bits.
         * A translation moves the coordinates by a constant amount in x
         * and y without changing the length or angle of vectors.
         *
         * @see .TYPE_IDENTITY
         *
         * @see .TYPE_UNIFORM_SCALE
         *
         * @see .TYPE_GENERAL_SCALE
         *
         * @see .TYPE_FLIP
         *
         * @see .TYPE_QUADRANT_ROTATION
         *
         * @see .TYPE_GENERAL_ROTATION
         *
         * @see .TYPE_GENERAL_TRANSFORM
         *
         * @see .getType
         *
         * @since 1.2
         */
        const val TYPE_TRANSLATION: Int = 1

        /**
         * This flag bit indicates that the transform defined by this object
         * performs a uniform scale in addition to the conversions indicated
         * by other flag bits.
         * A uniform scale multiplies the length of vectors by the same amount
         * in both the x and y directions without changing the angle between
         * vectors.
         * This flag bit is mutually exclusive with the TYPE_GENERAL_SCALE flag.
         *
         * @see .TYPE_IDENTITY
         *
         * @see .TYPE_TRANSLATION
         *
         * @see .TYPE_GENERAL_SCALE
         *
         * @see .TYPE_FLIP
         *
         * @see .TYPE_QUADRANT_ROTATION
         *
         * @see .TYPE_GENERAL_ROTATION
         *
         * @see .TYPE_GENERAL_TRANSFORM
         *
         * @see .getType
         *
         * @since 1.2
         */
        const val TYPE_UNIFORM_SCALE: Int = 2

        /**
         * This flag bit indicates that the transform defined by this object
         * performs a general scale in addition to the conversions indicated
         * by other flag bits.
         * A general scale multiplies the length of vectors by different
         * amounts in the x and y directions without changing the angle
         * between perpendicular vectors.
         * This flag bit is mutually exclusive with the TYPE_UNIFORM_SCALE flag.
         *
         * @see .TYPE_IDENTITY
         *
         * @see .TYPE_TRANSLATION
         *
         * @see .TYPE_UNIFORM_SCALE
         *
         * @see .TYPE_FLIP
         *
         * @see .TYPE_QUADRANT_ROTATION
         *
         * @see .TYPE_GENERAL_ROTATION
         *
         * @see .TYPE_GENERAL_TRANSFORM
         *
         * @see .getType
         *
         * @since 1.2
         */
        const val TYPE_GENERAL_SCALE: Int = 4

        /**
         * This constant is a bit mask for any of the scale flag bits.
         *
         * @see .TYPE_UNIFORM_SCALE
         *
         * @see .TYPE_GENERAL_SCALE
         *
         * @since 1.2
         */
        const val TYPE_MASK_SCALE: Int = (TYPE_UNIFORM_SCALE or
                TYPE_GENERAL_SCALE)

        /**
         * This flag bit indicates that the transform defined by this object
         * performs a mirror image flip about some axis which changes the
         * normally right handed coordinate system into a left handed
         * system in addition to the conversions indicated by other flag bits.
         * A right handed coordinate system is one where the positive X
         * axis rotates counterclockwise to overlay the positive Y axis
         * similar to the direction that the fingers on your right hand
         * curl when you stare end on at your thumb.
         * A left handed coordinate system is one where the positive X
         * axis rotates clockwise to overlay the positive Y axis similar
         * to the direction that the fingers on your left hand curl.
         * There is no mathematical way to determine the angle of the
         * original flipping or mirroring transformation since all angles
         * of flip are identical given an appropriate adjusting rotation.
         *
         * @see .TYPE_IDENTITY
         *
         * @see .TYPE_TRANSLATION
         *
         * @see .TYPE_UNIFORM_SCALE
         *
         * @see .TYPE_GENERAL_SCALE
         *
         * @see .TYPE_QUADRANT_ROTATION
         *
         * @see .TYPE_GENERAL_ROTATION
         *
         * @see .TYPE_GENERAL_TRANSFORM
         *
         * @see .getType
         *
         * @since 1.2
         */
        const val TYPE_FLIP: Int = 64

        /* NOTE: TYPE_FLIP was added after GENERAL_TRANSFORM was in public
     * circulation and the flag bits could no longer be conveniently
     * renumbered without introducing binary incompatibility in outside
     * code.
     */
        /**
         * This flag bit indicates that the transform defined by this object
         * performs a quadrant rotation by some multiple of 90 degrees in
         * addition to the conversions indicated by other flag bits.
         * A rotation changes the angles of vectors by the same amount
         * regardless of the original direction of the vector and without
         * changing the length of the vector.
         * This flag bit is mutually exclusive with the TYPE_GENERAL_ROTATION flag.
         *
         * @see .TYPE_IDENTITY
         *
         * @see .TYPE_TRANSLATION
         *
         * @see .TYPE_UNIFORM_SCALE
         *
         * @see .TYPE_GENERAL_SCALE
         *
         * @see .TYPE_FLIP
         *
         * @see .TYPE_GENERAL_ROTATION
         *
         * @see .TYPE_GENERAL_TRANSFORM
         *
         * @see .getType
         *
         * @since 1.2
         */
        const val TYPE_QUADRANT_ROTATION: Int = 8

        /**
         * This flag bit indicates that the transform defined by this object
         * performs a rotation by an arbitrary angle in addition to the
         * conversions indicated by other flag bits.
         * A rotation changes the angles of vectors by the same amount
         * regardless of the original direction of the vector and without
         * changing the length of the vector.
         * This flag bit is mutually exclusive with the
         * TYPE_QUADRANT_ROTATION flag.
         *
         * @see .TYPE_IDENTITY
         *
         * @see .TYPE_TRANSLATION
         *
         * @see .TYPE_UNIFORM_SCALE
         *
         * @see .TYPE_GENERAL_SCALE
         *
         * @see .TYPE_FLIP
         *
         * @see .TYPE_QUADRANT_ROTATION
         *
         * @see .TYPE_GENERAL_TRANSFORM
         *
         * @see .getType
         *
         * @since 1.2
         */
        const val TYPE_GENERAL_ROTATION: Int = 16

        /**
         * This constant is a bit mask for any of the rotation flag bits.
         *
         * @see .TYPE_QUADRANT_ROTATION
         *
         * @see .TYPE_GENERAL_ROTATION
         *
         * @since 1.2
         */
        const val TYPE_MASK_ROTATION: Int = (TYPE_QUADRANT_ROTATION or
                TYPE_GENERAL_ROTATION)

        /**
         * This constant indicates that the transform defined by this object
         * performs an arbitrary conversion of the input coordinates.
         * If this transform can be classified by any of the above constants,
         * the type will either be the constant TYPE_IDENTITY or a
         * combination of the appropriate flag bits for the various coordinate
         * conversions that this transform performs.
         *
         * @see .TYPE_IDENTITY
         *
         * @see .TYPE_TRANSLATION
         *
         * @see .TYPE_UNIFORM_SCALE
         *
         * @see .TYPE_GENERAL_SCALE
         *
         * @see .TYPE_FLIP
         *
         * @see .TYPE_QUADRANT_ROTATION
         *
         * @see .TYPE_GENERAL_ROTATION
         *
         * @see .getType
         *
         * @since 1.2
         */
        const val TYPE_GENERAL_TRANSFORM: Int = 32

        /**
         * This constant is used for the internal state variable to indicate
         * that no calculations need to be performed and that the source
         * coordinates only need to be copied to their destinations to
         * complete the transformation equation of this transform.
         *
         * @see .APPLY_TRANSLATE
         *
         * @see .APPLY_SCALE
         *
         * @see .APPLY_SHEAR
         *
         * @see .state
         */
        const val APPLY_IDENTITY: Int = 0

        /**
         * This constant is used for the internal state variable to indicate
         * that the translation components of the matrix (m02 and m12) need
         * to be added to complete the transformation equation of this transform.
         *
         * @see .APPLY_IDENTITY
         *
         * @see .APPLY_SCALE
         *
         * @see .APPLY_SHEAR
         *
         * @see .state
         */
        const val APPLY_TRANSLATE: Int = 1

        /**
         * This constant is used for the internal state variable to indicate
         * that the scaling components of the matrix (m00 and m11) need
         * to be factored in to complete the transformation equation of
         * this transform.  If the APPLY_SHEAR bit is also set then it
         * indicates that the scaling components are not both 0.0.  If the
         * APPLY_SHEAR bit is not also set then it indicates that the
         * scaling components are not both 1.0.  If neither the APPLY_SHEAR
         * nor the APPLY_SCALE bits are set then the scaling components
         * are both 1.0, which means that the x and y components contribute
         * to the transformed coordinate, but they are not multiplied by
         * any scaling factor.
         *
         * @see .APPLY_IDENTITY
         *
         * @see .APPLY_TRANSLATE
         *
         * @see .APPLY_SHEAR
         *
         * @see .state
         */
        const val APPLY_SCALE: Int = 2

        /**
         * This constant is used for the internal state variable to indicate
         * that the shearing components of the matrix (m01 and m10) need
         * to be factored in to complete the transformation equation of this
         * transform.  The presence of this bit in the state variable changes
         * the interpretation of the APPLY_SCALE bit as indicated in its
         * documentation.
         *
         * @see .APPLY_IDENTITY
         *
         * @see .APPLY_TRANSLATE
         *
         * @see .APPLY_SCALE
         *
         * @see .state
         */
        const val APPLY_SHEAR: Int = 4

        /*
     * For methods which combine together the state of two separate
     * transforms and dispatch based upon the combination, these constants
     * specify how far to shift one of the states so that the two states
     * are mutually non-interfering and provide constants for testing the
     * bits of the shifted (HI) state.  The methods in this class use
     * the convention that the state of "this" transform is unshifted and
     * the state of the "other" or "argument" transform is shifted (HI).
     */
        private const val HI_SHIFT = 3
        private const val HI_IDENTITY = APPLY_IDENTITY shl HI_SHIFT
        private const val HI_TRANSLATE = APPLY_TRANSLATE shl HI_SHIFT
        private const val HI_SCALE = APPLY_SCALE shl HI_SHIFT
        private const val HI_SHEAR = APPLY_SHEAR shl HI_SHIFT

        /**
         * Returns a transform representing a translation transformation.
         * The matrix representing the returned transform is:
         * <pre>
         * [   1    0    tx  ]
         * [   0    1    ty  ]
         * [   0    0    1   ]
        </pre> *
         *
         * @param tx the distance by which coordinates are translated in the
         * X axis direction
         * @param ty the distance by which coordinates are translated in the
         * Y axis direction
         * @return an `AffineTransform` object that represents a
         * translation transformation, created with the specified vector.
         * @since 1.2
         */
        fun getTranslateInstance(tx: Double, ty: Double): AffineTransform {
            val Tx = AffineTransform()
            Tx.setToTranslation(tx, ty)
            return Tx
        }

        /**
         * Returns a transform representing a rotation transformation.
         * The matrix representing the returned transform is:
         * <pre>
         * [   cos(theta)    -sin(theta)    0   ]
         * [   sin(theta)     cos(theta)    0   ]
         * [       0              0         1   ]
        </pre> *
         * Rotating by a positive angle theta rotates points on the positive
         * X axis toward the positive Y axis.
         * Note also the discussion of
         * [Handling 90-Degree Rotations](#quadrantapproximation)
         * above.
         *
         * @param theta the angle of rotation measured in radians
         * @return an `AffineTransform` object that is a rotation
         * transformation, created with the specified angle of rotation.
         * @since 1.2
         */
        fun getRotateInstance(theta: Double): AffineTransform {
            val Tx = AffineTransform()
            Tx.setToRotation(theta)
            return Tx
        }

        /**
         * Returns a transform that rotates coordinates around an anchor point.
         * This operation is equivalent to translating the coordinates so
         * that the anchor point is at the origin (S1), then rotating them
         * about the new origin (S2), and finally translating so that the
         * intermediate origin is restored to the coordinates of the original
         * anchor point (S3).
         *
         *
         * This operation is equivalent to the following sequence of calls:
         * <pre>
         * AffineTransform Tx = new AffineTransform();
         * Tx.translate(anchorx, anchory);    // S3: final translation
         * Tx.rotate(theta);                  // S2: rotate around anchor
         * Tx.translate(-anchorx, -anchory);  // S1: translate anchor to origin
        </pre> *
         * The matrix representing the returned transform is:
         * <pre>
         * [   cos(theta)    -sin(theta)    x-x*cos+y*sin  ]
         * [   sin(theta)     cos(theta)    y-x*sin-y*cos  ]
         * [       0              0               1        ]
        </pre> *
         * Rotating by a positive angle theta rotates points on the positive
         * X axis toward the positive Y axis.
         * Note also the discussion of
         * [Handling 90-Degree Rotations](#quadrantapproximation)
         * above.
         *
         * @param theta   the angle of rotation measured in radians
         * @param anchorx the X coordinate of the rotation anchor point
         * @param anchory the Y coordinate of the rotation anchor point
         * @return an `AffineTransform` object that rotates
         * coordinates around the specified point by the specified angle of
         * rotation.
         * @since 1.2
         */
        fun getRotateInstance(
            theta: Double,
            anchorx: Double,
            anchory: Double
        ): AffineTransform {
            val Tx = AffineTransform()
            Tx.setToRotation(theta, anchorx, anchory)
            return Tx
        }

        /**
         * Returns a transform that rotates coordinates according to
         * a rotation vector.
         * All coordinates rotate about the origin by the same amount.
         * The amount of rotation is such that coordinates along the former
         * positive X axis will subsequently align with the vector pointing
         * from the origin to the specified vector coordinates.
         * If both `vecx` and `vecy` are 0.0,
         * an identity transform is returned.
         * This operation is equivalent to calling:
         * <pre>
         * AffineTransform.getRotateInstance(Math.atan2(vecy, vecx));
        </pre> *
         *
         * @param vecx the X coordinate of the rotation vector
         * @param vecy the Y coordinate of the rotation vector
         * @return an `AffineTransform` object that rotates
         * coordinates according to the specified rotation vector.
         * @since 1.6
         */
        fun getRotateInstance(vecx: Double, vecy: Double): AffineTransform {
            val Tx = AffineTransform()
            Tx.setToRotation(vecx, vecy)
            return Tx
        }

        /**
         * Returns a transform that rotates coordinates around an anchor
         * point according to a rotation vector.
         * All coordinates rotate about the specified anchor coordinates
         * by the same amount.
         * The amount of rotation is such that coordinates along the former
         * positive X axis will subsequently align with the vector pointing
         * from the origin to the specified vector coordinates.
         * If both `vecx` and `vecy` are 0.0,
         * an identity transform is returned.
         * This operation is equivalent to calling:
         * <pre>
         * AffineTransform.getRotateInstance(Math.atan2(vecy, vecx),
         * anchorx, anchory);
        </pre> *
         *
         * @param vecx    the X coordinate of the rotation vector
         * @param vecy    the Y coordinate of the rotation vector
         * @param anchorx the X coordinate of the rotation anchor point
         * @param anchory the Y coordinate of the rotation anchor point
         * @return an `AffineTransform` object that rotates
         * coordinates around the specified point according to the
         * specified rotation vector.
         * @since 1.6
         */
        fun getRotateInstance(
            vecx: Double,
            vecy: Double,
            anchorx: Double,
            anchory: Double
        ): AffineTransform {
            val Tx = AffineTransform()
            Tx.setToRotation(vecx, vecy, anchorx, anchory)
            return Tx
        }

        /**
         * Returns a transform that rotates coordinates by the specified
         * number of quadrants.
         * This operation is equivalent to calling:
         * <pre>
         * AffineTransform.getRotateInstance(numquadrants * Math.PI / 2.0);
        </pre> *
         * Rotating by a positive number of quadrants rotates points on
         * the positive X axis toward the positive Y axis.
         *
         * @param numquadrants the number of 90 degree arcs to rotate by
         * @return an `AffineTransform` object that rotates
         * coordinates by the specified number of quadrants.
         * @since 1.6
         */
        fun getQuadrantRotateInstance(numquadrants: Int): AffineTransform {
            val Tx = AffineTransform()
            Tx.setToQuadrantRotation(numquadrants)
            return Tx
        }

        /**
         * Returns a transform that rotates coordinates by the specified
         * number of quadrants around the specified anchor point.
         * This operation is equivalent to calling:
         * <pre>
         * AffineTransform.getRotateInstance(numquadrants * Math.PI / 2.0,
         * anchorx, anchory);
        </pre> *
         * Rotating by a positive number of quadrants rotates points on
         * the positive X axis toward the positive Y axis.
         *
         * @param numquadrants the number of 90 degree arcs to rotate by
         * @param anchorx      the X coordinate of the rotation anchor point
         * @param anchory      the Y coordinate of the rotation anchor point
         * @return an `AffineTransform` object that rotates
         * coordinates by the specified number of quadrants around the
         * specified anchor point.
         * @since 1.6
         */
        fun getQuadrantRotateInstance(
            numquadrants: Int,
            anchorx: Double,
            anchory: Double
        ): AffineTransform {
            val Tx = AffineTransform()
            Tx.setToQuadrantRotation(numquadrants, anchorx, anchory)
            return Tx
        }

        /**
         * Returns a transform representing a scaling transformation.
         * The matrix representing the returned transform is:
         * <pre>
         * [   sx   0    0   ]
         * [   0    sy   0   ]
         * [   0    0    1   ]
        </pre> *
         *
         * @param sx the factor by which coordinates are scaled along the
         * X axis direction
         * @param sy the factor by which coordinates are scaled along the
         * Y axis direction
         * @return an `AffineTransform` object that scales
         * coordinates by the specified factors.
         * @since 1.2
         */
        fun getScaleInstance(sx: Double, sy: Double): AffineTransform {
            val Tx = AffineTransform()
            Tx.setToScale(sx, sy)
            return Tx
        }

        /**
         * Returns a transform representing a shearing transformation.
         * The matrix representing the returned transform is:
         * <pre>
         * [   1   shx   0   ]
         * [  shy   1    0   ]
         * [   0    0    1   ]
        </pre> *
         *
         * @param shx the multiplier by which coordinates are shifted in the
         * direction of the positive X axis as a factor of their Y coordinate
         * @param shy the multiplier by which coordinates are shifted in the
         * direction of the positive Y axis as a factor of their X coordinate
         * @return an `AffineTransform` object that shears
         * coordinates by the specified multipliers.
         * @since 1.2
         */
        fun getShearInstance(shx: Double, shy: Double): AffineTransform {
            val Tx = AffineTransform()
            Tx.setToShear(shx, shy)
            return Tx
        }

        // Utility methods to optimize rotate methods.
        // These tables translate the flags during predictable quadrant
        // rotations where the shear and scale values are swapped and negated.
        private val rot90conversion = intArrayOf(
            /* IDENTITY => */APPLY_SHEAR,  /* TRANSLATE (TR) => */
            APPLY_SHEAR or APPLY_TRANSLATE,  /* SCALE (SC) => */
            APPLY_SHEAR,  /* SC | TR => */
            APPLY_SHEAR or APPLY_TRANSLATE,  /* SHEAR (SH) => */
            APPLY_SCALE,  /* SH | TR => */
            APPLY_SCALE or APPLY_TRANSLATE,  /* SH | SC => */
            APPLY_SHEAR or APPLY_SCALE,  /* SH | SC | TR => */
            APPLY_SHEAR or APPLY_SCALE or APPLY_TRANSLATE,
        )

        // Round values to sane precision for printing
        // Note that Math.sin(Math.PI) has an error of about 10^-16
        private fun _matround(matval: Double): Double {
            return round(matval * 1E15) / 1E15
        }

        /* Serialization support.  A readObject method is neccessary because
     * the state field is part of the implementation of this particular
     * AffineTransform and not part of the public specification.  The
     * state variable's value needs to be recalculated on the fly by the
     * readObject method as it is in the 6-argument matrix constructor.
     */
        /**
         * Use serialVersionUID from JDK 1.2 for interoperability.
         */
        private const val serialVersionUID = 1330973210523860834L
    }
}
