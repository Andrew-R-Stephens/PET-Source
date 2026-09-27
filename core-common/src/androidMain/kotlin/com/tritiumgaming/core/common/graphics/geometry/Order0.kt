/*
 * Copyright (c) 1998, 2018, Oracle and/or its affiliates. All rights reserved.
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
package com.tritiumgaming.core.common.graphics.geometry

/**
 * @noinspection CanBeFinal, CanBeFinal
 */
internal class Order0(
    override val x1: Double,
    override val y1: Double
) : Curve(INCREASING) {
    override val order: Int
        get() = 0

    override val xTop: Double = 0.0
    override val yTop: Double = 0.0
    override val xBot: Double = 0.0
    override val yBot: Double = 0.0
    override val xMin: Double = 0.0
    override val xMax: Double = 0.0
    override val x0: Double = 0.0
    override val y0: Double = 0.0

    override val reversedCurve: Curve
        get() = this

    override fun XforY(y: Double): Double { return y }

    override fun TforY(y: Double): Double { return 0.0 }

    override fun XforT(t: Double): Double { return x1 }

    override fun YforT(t: Double): Double { return y1 }

    override fun dXforT(t: Double, deriv: Int): Double { return 0.0 }

    override fun dYforT(t: Double, deriv: Int): Double { return 0.0 }

    override fun nextVertical(t0: Double, t1: Double): Double { return t1 }

    override fun crossingsFor(x: Double, y: Double): Int { return 0 }

    override fun accumulateCrossings(c: Crossings): Boolean {
        return (x1 > c.xLo && x1 < c.xHi && y1 > c.yLo && y1 < c.yHi)
    }

    override fun enlarge(r: Rectangle2D?) {
        r?.add(x1, y1)
    }

    override fun getSubCurve(ystart: Double, yend: Double, dir: Int): Curve { return this }

    override fun getSegment(coords: DoubleArray?): Int {
        coords?.set(0, x1)
        coords?.set(1, y1)
        return PathIterator.SEG_MOVETO
    }
}
