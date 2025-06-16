package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model

import android.util.Log

class ComplexWorldMapDimensions(val w: Int, val h: Int) {
    @Synchronized
    fun print() {
        Log.d("Maps", "[ W: $w H: $h ]")
    }
}
