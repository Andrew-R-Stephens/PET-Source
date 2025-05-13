package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.sanity.carousels

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.repository.SimpleMapRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MapCarouselHandler(
    private val simpleMapRepository: SimpleMapRepository
) {

    /* Index */
    private val _currentIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    val currentIndex = _currentIndex.asStateFlow()
    private fun setIndex(index: Int) {
        _currentIndex.value = index

        setCurrentName()
        setCurrentSize()
    }
    fun incrementIndex() {
        var i = currentIndex.value + 1
        if (i >= simpleMapRepository.maps.size) { i = 0 }
        setIndex(i)
    }
    fun decrementIndex() {
        var i = currentIndex.value - 1
        if (i < 0) { i = simpleMapRepository.maps.size - 1 }
        setIndex(i)
    }
    /* -- */

    private val _currentName = MutableStateFlow<Int>(
        simpleMapRepository.maps[currentIndex.value].mapName
    )
    val currentName = _currentName.asStateFlow()
    private fun setCurrentName() {
        _currentName.update { simpleMapRepository.maps[currentIndex.value].mapName }
    }

    private val _currentSize = MutableStateFlow<Int>(
        simpleMapRepository.maps[currentIndex.value].mapSize)
    private val currentSize = _currentSize.asStateFlow()
    private fun setCurrentSize() {
        _currentSize.update { simpleMapRepository.maps[currentIndex.value].mapSize }
    }

    /** Based on current map size (Small, Medium, Large) and the stage of the investigation
     * (Setup vs Hunt)
     * Defaults if the selected index is out of range of available indexes.
     * @returns the drop rate multiplier. */
    fun getCurrentModifier(timeRemaining: Long = 0L): Float {
        if (timeRemaining <= 0L) {
            return simpleMapRepository.modifiers[currentSize.value].normalModifier
        }
        return simpleMapRepository.modifiers[currentSize.value].setupModifier
    }
}
