package com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.maps.map.MapModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.maps.mapviewer.MapViewerModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.MapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.pet.application.PETApplication

/**
 * MapMenuViewModel class
 *
 * @author TritiumGamingStudios
 */
class MapMenuViewModel(
    mapRepository: MapRepository
) : ViewModel() {

    private var allMaps = mapRepository.mapsData

    var currentMapModel: MapModel? = null

    val mapThumbnails: MutableList<Int> = mapRepository.mapThumbnails

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
        private val mapRepository: MapRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MapMenuViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MapMenuViewModel(
                    mapRepository
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val appKeyContainer = (this[APPLICATION_KEY] as PETApplication).container

                val mapRepository: MapRepository = appKeyContainer.mapRepository

                MapMenuViewModel(
                    mapRepository = mapRepository
                )
            }
        }
    }
}
