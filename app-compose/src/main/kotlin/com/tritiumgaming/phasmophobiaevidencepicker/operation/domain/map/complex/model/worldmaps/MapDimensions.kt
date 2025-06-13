package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps

import android.util.Log

class MapDimensions(val w: Int, val h: Int) {
    @Synchronized
    fun print() {
        Log.d("Maps", "[ W: $w H: $h ]")
    }
}
