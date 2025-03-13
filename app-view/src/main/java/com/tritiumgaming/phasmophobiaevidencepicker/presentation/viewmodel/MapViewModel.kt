package com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.app.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.ComplexMapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.SimpleMapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.maps.map.MapModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.maps.mapviewer.MapViewerModel

/**
 * MapMenuViewModel class
 *
 * @author TritiumGamingStudios
 */
class MapViewModel(
    val simpleMapRepository: SimpleMapRepository,
    val complexMapRepository: ComplexMapRepository
) : ViewModel() {

    private var allMaps = simpleMapRepository.mapsData

    var currentMapModel: MapModel? = null

    val mapThumbnails: MutableList<Int> = simpleMapRepository.mapThumbnails

    var imageDisplayThread: Thread? = null

    var currentMapIndex: Int = 0
        set(currentMapPos) {
            if (currentMapPos < allMaps.size) {
                field = currentMapPos
            }
        }
    val currentMapViewerModel: MapViewerModel
        get() {
            return allMaps[currentMapIndex]
        }
    fun incrementFloorIndex() {
        var layerIndex: Int = currentMapViewerModel.currentFloor
        if (++layerIndex >= currentMapViewerModel.floorCount) {
            layerIndex = 0
        }
        currentMapViewerModel.currentFloor = layerIndex
    }
    fun decrementFloorIndex() {
        var layerIndex: Int = currentMapViewerModel.currentFloor
        if (--layerIndex < 0) {
            layerIndex = currentMapViewerModel.floorCount -1
        }
        currentMapViewerModel.currentFloor = layerIndex
    }

    fun hasCurrentMapData(): Boolean {
        return currentMapIndex < allMaps.size
    }

    class MapMenuFactory(
        private val simpleMapRepository: SimpleMapRepository,
        private val complexMapRepository: ComplexMapRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MapViewModel(
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
                val appKeyContainer = (this[APPLICATION_KEY] as PETApplication).container

                val simpleMapRepository: SimpleMapRepository = appKeyContainer.simpleMapRepository
                val complexMapRepository: ComplexMapRepository = appKeyContainer.complexMapRepository

                MapViewModel(
                    simpleMapRepository = simpleMapRepository,
                    complexMapRepository = complexMapRepository
                )
            }
        }
    }
}
