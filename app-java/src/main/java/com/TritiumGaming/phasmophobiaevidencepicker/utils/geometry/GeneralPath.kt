package com.TritiumGaming.phasmophobiaevidencepicker.utils.geometry

import com.TritiumGaming.phasmophobiaevidencepicker.utils.geometry.Path2D.Path2DFloat
import java.io.Serial

class GeneralPath : Path2DFloat {

    constructor() : super(WIND_NON_ZERO, INIT_SIZE)
    constructor(rule: Int) : super(rule, INIT_SIZE)
    constructor(rule: Int, initialCapacity: Int) : super(rule, initialCapacity)

    constructor(s: Shape?) : super(
        s!!, null
    )

    internal constructor(
        windingRule: Int,
        pointTypes: ByteArray?,
        numTypes: Int,
        pointCoords: FloatArray,
        numCoords: Int
    ) : super(WIND_NON_ZERO, INIT_SIZE) {
        // used to construct from native
        super.windingRule = windingRule
        this.pointTypes = pointTypes!!
        super.numTypes = numTypes
        super.floatCoords = pointCoords
        super.numCoords = numCoords
    }

    companion object {
        @Serial
        private val serialVersionUID = -8327096662768731142L
    }
}
