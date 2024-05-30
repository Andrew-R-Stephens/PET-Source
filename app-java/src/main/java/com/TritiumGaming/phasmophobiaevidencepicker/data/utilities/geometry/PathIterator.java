package com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.geometry;

import java.lang.annotation.Native;

public interface PathIterator {

    @Native int WIND_EVEN_ODD = 0;
    @Native int WIND_NON_ZERO = 1;
    @Native int SEG_MOVETO = 0;
    @Native int SEG_LINETO = 1;
    @Native int SEG_QUADTO = 2;
    @Native int SEG_CUBICTO = 3;
    @Native int SEG_CLOSE = 4;

    int getWindingRule();
    boolean isDone();
    void next();
    int currentSegment(float[] coords);
    int currentSegment(double[] coords);
}
