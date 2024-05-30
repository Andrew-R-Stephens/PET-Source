package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.carousels

class MapCarouselModel {
    data class MapSizeData(val name: String, val size: Int)

    private var mapSizeData: Array<MapSizeData?>? = null

    var mapCurrentIndex: Int = 0

    fun setMapSizeData(allNames: Array<String>, allSizes: IntArray) {
        if (allNames.size == allSizes.size) {
            mapSizeData = arrayOfNulls(allSizes.size)
            for (i in allNames.indices) {
                mapSizeData!![i] = MapSizeData(
                    allNames[i], allSizes[i]
                )
            }
        }
    }

    val mapCount: Int
        get() {
            if (mapSizeData == null) {
                return 0
            }
            return mapSizeData!!.size
        }

    val mapCurrentName: String
        get() {
            if (mapSizeData != null) {
                return mapSizeData!![mapCurrentIndex]!!.name
            }
            return "???"
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
