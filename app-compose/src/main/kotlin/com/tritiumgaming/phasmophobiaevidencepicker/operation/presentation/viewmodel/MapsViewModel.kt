package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.mapviewer.MapInteractModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps.MapModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.repository.ComplexMapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository

/**
 * MapMenuViewModel class
 *
 * @author TritiumGamingStudios
 */
class MapsViewModel(
    val simpleMapRepository: SimpleMapRepository,
    val complexMapRepository: ComplexMapRepository
) : ViewModel() {

    var imageDisplayThread: Thread? = null

    val mapThumbnails: List<Int> = simpleMapRepository.mapThumbnails
    private var allMaps = simpleMapRepository.maps

    var currentComplexMap: MapModel? = null
    val currentSimpleMap: MapInteractModel
        get() {
            return allMaps[currentMapIndex]
        }
    var currentMapIndex: Int = 0
        set(currentMapPos) {
            if (currentMapPos < allMaps.size) {
                field = currentMapPos
            }
        }

    fun incrementFloorIndex() {
        var layerIndex: Int = currentSimpleMap.currentFloor
        if (++layerIndex >= currentSimpleMap.floorCount) {
            layerIndex = 0
        }
        currentSimpleMap.currentFloor = layerIndex
    }
    fun decrementFloorIndex() {
        var layerIndex: Int = currentSimpleMap.currentFloor
        if (--layerIndex < 0) {
            layerIndex = currentSimpleMap.floorCount -1
        }
        currentSimpleMap.currentFloor = layerIndex
    }

    class MapsFactory(
        private val simpleMapRepository: SimpleMapRepository,
        private val complexMapRepository: ComplexMapRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MapsViewModel(
                    simpleMapRepository,
                    complexMapRepository
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val appKeyContainer = (this[APPLICATION_KEY] as PETApplication).operationsContainer

                val simpleMapRepository: SimpleMapRepository = appKeyContainer.simpleMapRepository
                val complexMapRepository: ComplexMapRepository = appKeyContainer.complexMapRepository

                MapsViewModel(
                    simpleMapRepository = simpleMapRepository,
                    complexMapRepository = complexMapRepository
                )
            }
        }
    }
}
