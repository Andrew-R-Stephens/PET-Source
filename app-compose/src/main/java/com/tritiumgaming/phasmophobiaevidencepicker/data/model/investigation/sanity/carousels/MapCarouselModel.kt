package com.tritiumgaming.phasmophobiaevidencepicker.data.model.investigation.sanity.carousels

import com.tritiumgaming.phasmophobiaevidencepicker.data.model.investigation.sanity.timer.PhaseTimerModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.datastore.repository.MapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.datastore.repository.MapRepository.MapConstraints.MODIFIER_NORMAL
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.datastore.repository.MapRepository.MapConstraints.MODIFIER_SETUP
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MapCarouselModel(
    private val mapRepository: MapRepository,
    private val timerModel: PhaseTimerModel?
) {

    /* Index */
    private val _currentIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    val currentIndex = _currentIndex.asStateFlow()
    private fun setIndex(index: Int) {
        _currentIndex.value = index
    }
    fun incrementIndex() {
        var i = currentIndex.value + 1
        if (i >= mapRepository.itemCount) { i = 0 }
        setIndex(i)
    }
    fun decrementIndex() {
        var i = currentIndex.value - 1
        if (i < 0) { i = mapRepository.itemCount - 1 }
        setIndex(i)
    }
    /* -- */

    val currentName: Int
        get() = mapRepository.mapsData[currentIndex.value].mapName

    val currentMapSize: Int
        get() = mapRepository.mapsData[currentIndex.value].mapSize

    /** Based on current map size (Small, Medium, Large) and the stage of the investigation
     * (Setup vs Hunt)
     * Defaults if the selected index is out of range of available indexes.
     * @returns the drop rate multiplier. */
    val currentModifier: Float
        get() {
            if ((timerModel?.timeRemaining?.value ?: return 1f) <= 0L) {
                return MODIFIER_NORMAL[currentMapSize]
            }
            return MODIFIER_SETUP[currentMapSize]
        }

}
