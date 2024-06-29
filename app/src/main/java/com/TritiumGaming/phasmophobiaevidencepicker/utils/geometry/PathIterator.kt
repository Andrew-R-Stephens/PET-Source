package com.TritiumGaming.phasmophobiaevidencepicker.utils.geometry

interface PathIterator {
    val windingRule: Int
    val isDone: Boolean
    fun next()
    fun currentSegment(coords: FloatArray): Int
    fun currentSegment(coords: DoubleArray): Int

    companion object {
        const val WIND_EVEN_ODD: Int = 0
        const val WIND_NON_ZERO: Int = 1
        const val SEG_MOVETO: Int = 0
        const val SEG_LINETO: Int = 1
        const val SEG_QUADTO: Int = 2
        const val SEG_CUBICTO: Int = 3
        const val SEG_CLOSE: Int = 4
    }
}
