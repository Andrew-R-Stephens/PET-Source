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

package com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.geometry;

import androidx.annotation.NonNull;

/** @noinspection CanBeFinal, CanBeFinal */
final class Order0 extends Curve {
    private final double x;
    private final double y;

    public Order0(double x, double y) {
        super(INCREASING);
        this.x = x;
        this.y = y;
    }

    public int getOrder() {
        return 0;
    }

    public double getXTop() {
        return x;
    }

    public double getYTop() {
        return y;
    }

    public double getXBot() {
        return x;
    }

    public double getYBot() {
        return y;
    }

    public double getXMin() {
        return x;
    }

    public double getXMax() {
        return x;
    }

    public double getX0() {
        return x;
    }

    public double getY0() {
        return y;
    }

    public double getX1() {
        return x;
    }

    public double getY1() {
        return y;
    }

    public double XforY(double y) {
        return y;
    }

    public double TforY(double y) {
        return 0;
    }

    public double XforT(double t) {
        return x;
    }

    public double YforT(double t) {
        return y;
    }

    public double dXforT(double t, int deriv) {
        return 0;
    }

    public double dYforT(double t, int deriv) {
        return 0;
    }

    public double nextVertical(double t0, double t1) {
        return t1;
    }

    public int crossingsFor(double x, double y) {
        return 0;
    }

    public boolean accumulateCrossings(@NonNull Crossings c) {
        return (x > c.getXLo() &&
                x < c.getXHi() &&
                y > c.getYLo() &&
                y < c.getYHi());
    }

    public void enlarge(@NonNull Rectangle2D r) {
        r.add(x, y);
    }

    @NonNull
    public Curve getSubCurve(double ystart, double yend, int dir) {
        return this;
    }

    @NonNull
    public Curve getReversedCurve() {
        return this;
    }

    public int getSegment(@NonNull double[] coords) {
        coords[0] = x;
        coords[1] = y;
        return PathIterator.SEG_MOVETO;
    }
}
