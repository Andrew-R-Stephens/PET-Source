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

    private var itemList = mutableListOf<MapSizeData>()
    private val itemCount: Int
        get() {
            return itemList.size
        }


    /* Index */
    private val _currentIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    val currentIndex = _currentIndex.asStateFlow()
    fun updateIndex(index: Int) {
        _currentIndex.value = index
    }
    fun incrementIndex() {
        var i = currentIndex.value + 1
        if (i >= itemCount) {
            i = 0
        }
        updateIndex(i)
    }
    fun decrementIndex() {
        var i = currentIndex.value - 1
        if (i < 0) {
            i = itemCount - 1
        }
        updateIndex(i)
    }
    /* -- */

    val currentName: String
        get() = itemList[currentIndex.value].name

    val currentMapSize: Int
        get() {
            return itemList[currentIndex.value].size
        }

    init {
        val typedArray: TypedArray =
            context.resources.obtainTypedArray(R.array.maps_resources_array)
        val names = mutableListOf<String>()
        val sizes = mutableListOf<Int>()
        for (i in 0 until typedArray.length()) {
            val mapTypedArray: TypedArray =
                context.resources.obtainTypedArray(typedArray.getResourceId(i, 0))
            names.add(i, mapTypedArray.getString(0) ?: "")
            val sizeLayer = 6
            sizes.add(i, mapTypedArray.getInt(sizeLayer, 0))
            mapTypedArray.recycle()
        }
        typedArray.recycle()

        setList(names, sizes)
    }

    private fun setList(
        allNames: MutableList<String> = mutableListOf(),
        allSizes: MutableList<Int> = mutableListOf()
    ) {
        if (allNames.size == allSizes.size) {
            for (i in allNames.indices) {
                itemList.add(i, MapSizeData(allNames[i], allSizes[i]))
            }
        }
    }
}
