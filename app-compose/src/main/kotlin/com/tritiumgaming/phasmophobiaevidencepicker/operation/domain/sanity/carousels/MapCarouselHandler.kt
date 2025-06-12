package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.sanity.carousels

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MapCarouselHandler(
    private val simpleMapRepository: SimpleMapRepository
) {

    /* Index */
    /*private val _currentMapIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    val currentMapIndex = _currentMapIndex.asStateFlow()
    private fun setIndex(index: Int) {
        _currentMapIndex.value = index

        setCurrentMapName()
        setCurrentMapSize()
    }
    fun incrementMapIndex() {
        var i = currentMapIndex.value + 1
        if (i >= simpleMapRepository.maps.size) { i = 0 }
        setIndex(i)
    }
    fun decrementMapIndex() {
        var i = currentMapIndex.value - 1
        if (i < 0) { i = simpleMapRepository.maps.size - 1 }
        setIndex(i)
    }*/
    /* -- */

    /*private val _currentMapName = MutableStateFlow<Int>(
        simpleMapRepository.maps[currentMapIndex.value].mapName
    )
    val currentMapName = _currentMapName.asStateFlow()
    private fun setCurrentMapName() {
        _currentMapName.update { simpleMapRepository.maps[currentMapIndex.value].mapName }
    }

    private val _currentMapSize = MutableStateFlow<Int>(
        simpleMapRepository.maps[currentMapIndex.value].mapSize)
    private val currentMapSize = _currentMapSize.asStateFlow()
    private fun setCurrentMapSize() {
        _currentMapSize.update { simpleMapRepository.maps[currentMapIndex.value].mapSize }
    }

    *//** Based on current map size (Small, Medium, Large) and the stage of the investigation
     * (Setup vs Hunt)
     * Defaults if the selected index is out of range of available indexes.
     * @returns the drop rate multiplier. *//*
    fun getCurrentMapModifier(timeRemaining: Long = 0L): Float {
        if (timeRemaining <= 0L) {
            return simpleMapRepository.modifiers[currentMapSize.value].normalModifier
        }
        return simpleMapRepository.modifiers[currentMapSize.value].setupModifier
    }*/
}
