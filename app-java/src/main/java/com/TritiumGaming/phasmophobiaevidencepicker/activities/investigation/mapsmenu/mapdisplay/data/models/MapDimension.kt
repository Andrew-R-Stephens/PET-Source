package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.mapsmenu.mapdisplay.data.models

import android.util.Log

class MapDimension(val w: Int, val h: Int) {
    @Synchronized
    fun print() {
        Log.d("Maps", "[ W: $w H: $h ]")
    }
}