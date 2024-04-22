
package com.tritiumstudios.phasmophobiaevidencetool.data.utilities.geometry;

import androidx.annotation.NonNull;

import java.util.NoSuchElementException;

public class FlatteningPathIterator implements PathIterator {
    static final int GROW_SIZE = 24;    // Multiple of cubic & quad curve size

    PathIterator src;                   // The source iterator

    double squareflat;                  // Square of the flatness parameter
                                        // for testing against squared lengths

    int limit;                          // Maximum number of recursion levels

    @NonNull
    double[] hold = new double[14];     // The cache of interpolated coords
                                        // Note that this must be long enough
                                        // to store a full cubic segment and
                                        // a relative cubic segment to avoid
                                        // aliasing when copying the coords
                                        // of a curve to the end of the array.
                                        // This is also serendipitously equal
                                        // to the size of a full quad segment
                                        // and 2 relative quad segments.

    double curx, cury;                  // The ending x,y of the last segment

    double movx, movy;                  // The x,y of the last move segment

    int holdType;                       // The type of the curve being held
                                        // for interpolation

    int holdEnd;                        // The index of the last curve segment
                                        // being held for interpolation

    int holdIndex;                      // The index of the curve segment
                                        // that was last interpolated.  This
                                        // is the curve segment ready to be
                                        // returned in the next call to
                                        // currentSegment().

    int[] levels;                       // The recursion level at which
                                        // each curve being held in storage
                                        // was generated.

    int levelIndex;                     // The index of the entry in the
                                        // levels array of the curve segment
                                        // at the holdIndex

    boolean done;                       // True when iteration is done

    public FlatteningPathIterator(PathIterator src, double flatness) {
        this(src, flatness, 10);
    }

    public FlatteningPathIterator(PathIterator src, double flatness,
                                  int limit) {
        if (flatness < 0.0) {
            throw new IllegalArgumentException("flatness must be >= 0");
        }
        if (limit < 0) {
            throw new IllegalArgumentException("limit must be >= 0");
        }
        this.src = src;
        this.squareflat = flatness * flatness;
        this.limit = limit;
        this.levels = new int[limit + 1];
        // prime the first path segment
        next(false);
    }

    public double getFlatness() {
        return Math.sqrt(squareflat);
    }

    public int getRecursionLimit() {
        return limit;
    }

    public int getWindingRule() {
        return src.getWindingRule();
    }

    public boolean isDone() {
        return done;
    }

    /*
     * Ensures that the hold array can hold up to (want) more values.
     * It is currently holding (hold.length - holdIndex) values.
     */
    void ensureHoldCapacity(int want) {
        if (holdIndex - want < 0) {
            int have = hold.length - holdIndex;
            int newsize = hold.length + GROW_SIZE;
            double[] newhold = new double[newsize];
            System.arraycopy(hold, holdIndex,
                             newhold, holdIndex + GROW_SIZE,
                             have);
            hold = newhold;
            holdIndex += GROW_SIZE;
            holdEnd += GROW_SIZE;
        }
    }

    public void next() {
        next(true);
    }

    private void next(boolean doNext) {
        int level;

        if (holdIndex >= holdEnd) {
            if (doNext) {
                src.next();
            }
            if (src.isDone()) {
                done = true;
                return;
            }
            holdType = src.currentSegment(hold);
            levelIndex = 0;
            levels[0] = 0;
        }

        switch (holdType) {
        case SEG_MOVETO:
        case SEG_LINETO:
            curx = hold[0];
            cury = hold[1];
            if (holdType == SEG_MOVETO) {
                movx = curx;
                movy = cury;
            }
            holdIndex = 0;
            holdEnd = 0;
            break;
        case SEG_CLOSE:
            curx = movx;
            cury = movy;
            holdIndex = 0;
            holdEnd = 0;
            break;
        case SEG_QUADTO:
            if (holdIndex >= holdEnd) {
                // Move the coordinates to the end of the array.
                holdIndex = hold.length - 6;
                holdEnd = hold.length - 2;
                hold[holdIndex] = curx;
                hold[holdIndex + 1] = cury;
                hold[holdIndex + 2] = hold[0];
                hold[holdIndex + 3] = hold[1];
                hold[holdIndex + 4] = curx = hold[2];
                hold[holdIndex + 5] = cury = hold[3];
            }

            level = levels[levelIndex];
            while (level < limit) {
                if (QuadCurve2D.getFlatnessSq(hold, holdIndex) < squareflat) {
                    break;
                }

                ensureHoldCapacity(4);
                QuadCurve2D.subdivide(hold, holdIndex,
                                      hold, holdIndex - 4,
                                      hold, holdIndex);
                holdIndex -= 4;

                // Now that we have subdivided, we have constructed
                // two curves of one depth lower than the original
                // curve.  One of those curves is in the place of
                // the former curve and one of them is in the next
                // set of held coordinate slots.  We now set both
                // curves level values to the next higher level.
                level++;
                levels[levelIndex] = level;
                levelIndex++;
                levels[levelIndex] = level;
            }

            // This curve segment is flat enough, or it is too deep
            // in recursion levels to try to flatten any more.  The
            // two coordinates at holdIndex+4 and holdIndex+5 now
            // contain the endpoint of the curve which can be the
            // endpoint of an approximating line segment.
            holdIndex += 4;
            levelIndex--;
            break;
        case SEG_CUBICTO:
            if (holdIndex >= holdEnd) {
                // Move the coordinates to the end of the array.
                holdIndex = hold.length - 8;
                holdEnd = hold.length - 2;
                hold[holdIndex] = curx;
                hold[holdIndex + 1] = cury;
                hold[holdIndex + 2] = hold[0];
                hold[holdIndex + 3] = hold[1];
                hold[holdIndex + 4] = hold[2];
                hold[holdIndex + 5] = hold[3];
                hold[holdIndex + 6] = curx = hold[4];
                hold[holdIndex + 7] = cury = hold[5];
            }

            level = levels[levelIndex];
            while (level < limit) {
                if (CubicCurve2D.getFlatnessSq(hold, holdIndex) < squareflat) {
                    break;
                }

                ensureHoldCapacity(6);
                CubicCurve2D.subdivide(hold, holdIndex,
                                       hold, holdIndex - 6,
                                       hold, holdIndex);
                holdIndex -= 6;

                // Now that we have subdivided, we have constructed
                // two curves of one depth lower than the original
                // curve.  One of those curves is in the place of
                // the former curve and one of them is in the next
                // set of held coordinate slots.  We now set both
                // curves level values to the next higher level.
                level++;
                levels[levelIndex] = level;
                levelIndex++;
                levels[levelIndex] = level;
            }

            // This curve segment is flat enough, or it is too deep
            // in recursion levels to try to flatten any more.  The
            // two coordinates at holdIndex+6 and holdIndex+7 now
            // contain the endpoint of the curve which can be the
            // endpoint of an approximating line segment.
            holdIndex += 6;
            levelIndex--;
            break;
        }
    }

    public int currentSegment(float[] coords) {
        if (isDone()) {
            throw new NoSuchElementException("flattening iterator out of bounds");
        }
        int type = holdType;
        if (type != SEG_CLOSE) {
            coords[0] = (float) hold[holdIndex];
            coords[1] = (float) hold[holdIndex + 1];
            if (type != SEG_MOVETO) {
                type = SEG_LINETO;
            }
        }
        return type;
    }

    public int currentSegment(double[] coords) {
        if (isDone()) {
            throw new NoSuchElementException("flattening iterator out of bounds");
        }
        int type = holdType;
        if (type != SEG_CLOSE) {
            coords[0] = hold[holdIndex];
            coords[1] = hold[holdIndex + 1];
            if (type != SEG_MOVETO) {
                type = SEG_LINETO;
            }
        }
        return type;
    }
}
