package com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.geometry;

public final class GeneralPath extends Path2D.Path2DFloat {

    public GeneralPath() {
        super(WIND_NON_ZERO, INIT_SIZE);
    }

    public GeneralPath(int rule) {
        super(rule, INIT_SIZE);
    }

    public GeneralPath(int rule, int initialCapacity) {
        super(rule, initialCapacity);
    }

    public GeneralPath(Shape s) {
        super(s, null);
    }

    GeneralPath(int windingRule,
                byte[] pointTypes,
                int numTypes,
                float[] pointCoords,
                int numCoords) {
        // used to construct from native

        setWindingRule(windingRule);
        this.pointTypes = pointTypes;
        setNumTypes(numTypes);
        setFloatCoords(pointCoords);
        setNumCoords(numCoords);
    }

    private static final long serialVersionUID = -8327096662768731142L;
}
