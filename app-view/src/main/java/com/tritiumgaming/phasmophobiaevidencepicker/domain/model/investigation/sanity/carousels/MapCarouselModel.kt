package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.carousels

import android.content.Context
import android.content.res.TypedArray
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.InvestigationViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MapCarouselModel(
    context: Context,
    val investigationViewModel: InvestigationViewModel
) {

    companion object MapConstraints {
        val MODIFIER_NORMAL = floatArrayOf(.12f, .08f, .05f)
        val MODIFIER_SETUP = floatArrayOf(.09f, .05f, .03f)
    }

    data class MapSizeData(val name: Int = 0, val size: Int)

    /* List */
    private var itemList = mutableListOf<MapSizeData>()
    private val itemCount: Int
        get() {
            return itemList.size
        }
    /* -- */

    /* Index */
    private val _currentIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    val currentIndex = _currentIndex.asStateFlow()
    private fun setIndex(index: Int) {
        _currentIndex.value = index
    }
    fun incrementIndex() {
        var i = currentIndex.value + 1
        if (i >= itemCount) { i = 0 }
        setIndex(i)
    }
    fun decrementIndex() {
        var i = currentIndex.value - 1
        if (i < 0) { i = itemCount - 1 }
        setIndex(i)
    }
    /* -- */

    val currentName: Int
        get() = itemList[currentIndex.value].name

    val currentMapSize: Int
        get() {
            return itemList[currentIndex.value].size
        }

    /** Based on current map size (Small, Medium, Large) and the stage of the investigation
     * (Setup vs Hunt)
     * Defaults if the selected index is out of range of available indexes.
     * @returns the drop rate multiplier. */
    val currentModifier: Float
        get() {
            if ((investigationViewModel.timerModel?.timeRemaining?.value ?: return 1f) <= 0L) {
                return MODIFIER_NORMAL[currentMapSize]
            }
            return MODIFIER_SETUP[currentMapSize]
        }

    private fun setList(
        names: MutableList<Int> = mutableListOf(),
        sizes: MutableList<Int> = mutableListOf()
    ) {
        if (names.size == sizes.size) {
            for (i in names.indices) {
                itemList.add(i, MapSizeData(names[i], sizes[i]))
            }
        }
    }

    init {
        val typedArray: TypedArray =
            context.resources.obtainTypedArray(R.array.maps_resources_array)
        val names = mutableListOf<Int>()
        val sizes = mutableListOf<Int>()
        for (i in 0 until typedArray.length()) {
            val mapTypedArray: TypedArray =
                context.resources.obtainTypedArray(typedArray.getResourceId(i, 0))
            names.add(i, mapTypedArray.getResourceId(0, 0))
            val sizeLayer = 6
            sizes.add(i, mapTypedArray.getInt(sizeLayer, 0))
            mapTypedArray.recycle()
        }
        typedArray.recycle()

        setList(names, sizes)
    }
}
