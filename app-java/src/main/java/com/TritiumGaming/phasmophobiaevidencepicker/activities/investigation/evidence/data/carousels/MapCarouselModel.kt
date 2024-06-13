package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.carousels

import android.content.Context
import android.content.res.TypedArray
import android.util.Log
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MapCarouselModel(
    context: Context,
    val evidenceViewModel: EvidenceViewModel
) {

    data class MapSizeData(val name: String = "NA", val size: Int)

    private var mapSizeData = mutableListOf<MapSizeData>()

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

    private fun setMapSizeData(allNames: MutableList<String>, allSizes: IntArray) {
        if (allNames.size == allSizes.size) {
            for (i in allNames.indices) {
                mapSizeData.add(i, MapSizeData(allNames[i], allSizes[i]))
            }
        }

        updateMapCarouselName()
    }

    private val mapCount: Int
        get() {
            return mapSizeData.size
        }

    private val _mapCurrentName: MutableStateFlow<String> = MutableStateFlow("???")
    val mapCurrentName = _mapCurrentName.asStateFlow()
    private fun updateMapCarouselName() {
        _mapCurrentName.value = mapSizeData.get(mapCurrentIndex).name
        Log.d("MapCarouselModel", "Map name updated to ${_mapCurrentName.value}")
    }

    val mapCurrentSize: Int
        get() {
            return mapSizeData[mapCurrentIndex].size
        }

    init {
        val typedArray: TypedArray =
            context.resources.obtainTypedArray(R.array.maps_resources_array)
        val names = mutableListOf<String>()
        val sizes = IntArray(typedArray.length())
        for (i in 0 until typedArray.length()) {
            val mapTypedArray: TypedArray =
                context.resources.obtainTypedArray(typedArray.getResourceId(i, 0))
            names.add(i, mapTypedArray.getString(0) ?: "")
            val sizeLayer = 6
            sizes[i] = mapTypedArray.getInt(sizeLayer, 0)
            mapTypedArray.recycle()
        }
        typedArray.recycle()
        setMapSizeData(names, sizes)
    }
}
