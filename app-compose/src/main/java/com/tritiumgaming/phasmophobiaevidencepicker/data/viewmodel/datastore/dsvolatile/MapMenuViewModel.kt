package com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.datastore.dsvolatile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.maps.map.MapModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.maps.mapviewer.MapViewerModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.MapRepository

/**
 * MapMenuViewModel class
 *
 * @author TritiumGamingStudios
 */
class MapMenuViewModel(
    localMapRepository: MapRepository
) : ViewModel() {

    private var allMaps = localMapRepository.mapsData

    var currentMapModel: MapModel? = null

    val mapThumbnails: MutableList<Int> = localMapRepository.mapThumbnails

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
}
