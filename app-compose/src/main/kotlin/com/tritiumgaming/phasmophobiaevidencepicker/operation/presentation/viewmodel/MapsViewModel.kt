package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.viewmodel

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.usecase.FetchMapModifiersUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.usecase.FetchMapThumbnailsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.usecase.FetchSimpleMapsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.mapviewer.MapInteractModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps.WorldMap
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.usecase.FetchComplexMapsUseCase
import kotlinx.coroutines.launch

/**
 * MapMenuViewModel class
 *
 * @author TritiumGamingStudios
 */
class MapsViewModel(
    val fetchSimpleMapsUseCase: FetchSimpleMapsUseCase,
    val fetchMapModifiersUseCase: FetchMapModifiersUseCase,
    val fetchMapThumbnailsUseCase: FetchMapThumbnailsUseCase,
    val fetchComplexMapsUseCase: FetchComplexMapsUseCase
) : ViewModel() {

    var imageDisplayThread: Thread? = null

    @DrawableRes val mapThumbnails: List<Int> = fetchMapThumbnailsUseCase()
    private val allMaps = fetchSimpleMapsUseCase()

    var currentComplexMap: WorldMap? = null
    val currentSimpleMap: MapInteractModel
        get() = allMaps[currentMapIndex]

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
            layerIndex = currentSimpleMap.floorCount - 1
        }
        currentSimpleMap.currentFloor = layerIndex
    }

    fun fetchComplexMapsUseCase() {
        viewModelScope.launch {
            fetchComplexMapsUseCase()
        }
    }

    init {
        fetchComplexMapsUseCase()
    }

    class MapsFactory(
        private val fetchSimpleMapsUseCase: FetchSimpleMapsUseCase,
        private val fetchMapModifiersUseCase: FetchMapModifiersUseCase,
        private val fetchMapThumbnailsUseCase: FetchMapThumbnailsUseCase,
        private val fetchComplexMapsUseCase: FetchComplexMapsUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MapsViewModel(
                    fetchSimpleMapsUseCase = fetchSimpleMapsUseCase,
                    fetchMapModifiersUseCase = fetchMapModifiersUseCase,
                    fetchMapThumbnailsUseCase = fetchMapThumbnailsUseCase,
                    fetchComplexMapsUseCase = fetchComplexMapsUseCase
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val appKeyContainer = (this[APPLICATION_KEY] as PETApplication).operationsContainer

                val fetchSimpleMapsUseCase = appKeyContainer.fetchSimpleMapsUseCase
                val fetchMapModifiersUseCase = appKeyContainer.fetchMapModifiersUseCase
                val fetchMapThumbnailsUseCase = appKeyContainer.fetchMapThumbnailsUseCase
                val fetchComplexMapsUseCase = appKeyContainer.fetchComplexMapsUseCase

                MapsViewModel(
                    fetchSimpleMapsUseCase = fetchSimpleMapsUseCase,
                    fetchMapModifiersUseCase = fetchMapModifiersUseCase,
                    fetchMapThumbnailsUseCase = fetchMapThumbnailsUseCase,
                    fetchComplexMapsUseCase = fetchComplexMapsUseCase
                )
            }
        }
    }
}
