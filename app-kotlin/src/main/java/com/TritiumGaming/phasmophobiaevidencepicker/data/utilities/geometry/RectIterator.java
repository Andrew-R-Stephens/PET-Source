
package com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.geometry;

import androidx.annotation.NonNull;

import java.util.NoSuchElementException;

class RectIterator implements PathIterator {
    double x, y, w, h;
    AffineTransform affine;
    int index;

    RectIterator(@NonNull Rectangle2D r, AffineTransform at) {
        this.x = r.getX();
        this.y = r.getY();
        this.w = r.getWidth();
        this.h = r.getHeight();
        this.affine = at;
        if (w < 0 || h < 0) {
            index = 6;
        }
    }

    public int getWindingRule() {
        return WIND_NON_ZERO;
    }

    public boolean isDone() {
        return index > 5;
    }

    public void next() {
        index++;
    }

    public int currentSegment(float[] coords) {
        if (isDone()) {
            throw new NoSuchElementException("rect iterator out of bounds");
        }
        if (index == 5) {
            return SEG_CLOSE;
        }
        coords[0] = (float) x;
        coords[1] = (float) y;
        if (index == 1 || index == 2) {
            coords[0] += (float) w;
        }
        if (index == 2 || index == 3) {
            coords[1] += (float) h;
        }
        if (affine != null) {
            affine.transform(coords, 0, coords, 0, 1);
        }
        return (index == 0 ? SEG_MOVETO : SEG_LINETO);
    }

    public int currentSegment(double[] coords) {
        if (isDone()) {
            throw new NoSuchElementException("rect iterator out of bounds");
        }
        if (index == 5) {
            return SEG_CLOSE;
        }
        coords[0] = x;
        coords[1] = y;
        if (index == 1 || index == 2) {
            coords[0] += w;
        }
        if (index == 2 || index == 3) {
            coords[1] += h;
        }
        if (affine != null) {
            affine.transform(coords, 0, coords, 0, 1);
        }
        return (index == 0 ? SEG_MOVETO : SEG_LINETO);
    }
}
