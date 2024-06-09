package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.carousels

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MapCarouselModel {

    data class MapSizeData(val name: String, val size: Int)
    private var mapSizeData: Array<MapSizeData?>? = null

    var mapCurrentIndex: Int = 0
        set(value) {
            field = value
            updateMapCarouselName()
        }

    fun incrementIndex() {
        var i = mapCurrentIndex + 1
        if (i >= mapCount) {
            i = 0
        }
        mapCurrentIndex = i
    }

    fun decrementIndex() {
        var i = mapCurrentIndex - 1
        if (i < 0) {
            i = mapCount - 1
        }
        mapCurrentIndex = i
    }

    fun setMapSizeData(allNames: Array<String>, allSizes: IntArray) {
        if (allNames.size == allSizes.size) {
            mapSizeData = arrayOfNulls(allSizes.size)
            for (i in allNames.indices) {
                mapSizeData!![i] = MapSizeData(
                    allNames[i], allSizes[i]
                )
            }
        }

        updateMapCarouselName()
    }

    private val mapCount: Int
        get() {
            return mapSizeData?.size ?: 0
        }

    /*
    val mapCurrentName: String
        get() {
            return mapSizeData?.get(mapCurrentIndex)?.name ?: "???"
        }
    */

    private val _mapCurrentName: MutableStateFlow<String> = MutableStateFlow("???")
    val mapCurrentName = _mapCurrentName.asStateFlow()
    private fun updateMapCarouselName() {
        _mapCurrentName.value = mapSizeData?.get(mapCurrentIndex)?.name ?: "???"
        Log.d("MapCarouselModel", "Map name updated to ${_mapCurrentName.value}")
    }

    val mapCurrentSize: Int
        get() {
            if (mapSizeData != null) {
                return mapSizeData!![mapCurrentIndex]!!.size
            }
            return 1
        }

    fun hasMapSizeData(): Boolean {
        return mapSizeData == null
    }
}
