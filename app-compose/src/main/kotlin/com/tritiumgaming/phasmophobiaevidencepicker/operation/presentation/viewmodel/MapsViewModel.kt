package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.viewmodel

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.FetchMapModifiersUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.FetchMapThumbnailsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.FetchSimpleMapsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.mapviewer.LocalWorldMap
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.worldmaps.WorldMap
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.usecase.FetchComplexMapsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.DecrementMapFloorIndexUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.IncrementMapFloorIndexUseCase
import kotlinx.coroutines.launch

/**
 * MapMenuViewModel class
 *
 * @author TritiumGamingStudios
 */
class MapsViewModel(
    private val fetchSimpleMapsUseCase: FetchSimpleMapsUseCase,
    private val incrementMapFloorIndexUseCase: IncrementMapFloorIndexUseCase,
    private val decrementMapFloorIndexUseCase: DecrementMapFloorIndexUseCase,
    private val fetchMapModifiersUseCase: FetchMapModifiersUseCase,
    private val fetchMapThumbnailsUseCase: FetchMapThumbnailsUseCase,
    private val fetchComplexMapsUseCase: FetchComplexMapsUseCase
) : ViewModel() {

    var imageDisplayThread: Thread? = null

    @DrawableRes val mapThumbnails: List<Int> = fetchMapThumbnailsUseCase()
    private val allMaps = fetchSimpleMapsUseCase()

    var currentComplexMap: WorldMap? = null
    val currentSimpleMap: LocalWorldMap
        get() = allMaps[currentMapIndex]

    var currentMapIndex: Int = 0
        set(currentMapPos) {
            if (currentMapPos < allMaps.size) {
                field = currentMapPos
            }
        }

    fun incrementFloorIndex() {
        currentSimpleMap.currentFloor =
            incrementMapFloorIndexUseCase(currentSimpleMap.currentFloor)
    }


    fun decrementFloorIndex() {
        currentSimpleMap.currentFloor =
            decrementMapFloorIndexUseCase(currentSimpleMap.currentFloor)
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
        private val incrementMapFloorIndexUseCase: IncrementMapFloorIndexUseCase,
        private val decrementMapFloorIndexUseCase: DecrementMapFloorIndexUseCase,
        private val fetchMapModifiersUseCase: FetchMapModifiersUseCase,
        private val fetchMapThumbnailsUseCase: FetchMapThumbnailsUseCase,
        private val fetchComplexMapsUseCase: FetchComplexMapsUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MapsViewModel(
                    fetchSimpleMapsUseCase = fetchSimpleMapsUseCase,
                    incrementMapFloorIndexUseCase = incrementMapFloorIndexUseCase,
                    decrementMapFloorIndexUseCase = decrementMapFloorIndexUseCase,
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
                val container = (this[APPLICATION_KEY] as PETApplication).operationsContainer

                val fetchSimpleMapsUseCase = container.fetchSimpleMapsUseCase
                val incrementMapFloorIndexUseCase = container.incrementMapFloorIndexUseCase
                val decrementMapFloorIndexUseCase = container.decrementMapFloorIndexUseCase
                val fetchMapModifiersUseCase = container.fetchMapModifiersUseCase
                val fetchMapThumbnailsUseCase = container.fetchMapThumbnailsUseCase
                val fetchComplexMapsUseCase = container.fetchComplexMapsUseCase

                MapsViewModel(
                    fetchSimpleMapsUseCase = fetchSimpleMapsUseCase,
                    incrementMapFloorIndexUseCase = incrementMapFloorIndexUseCase,
                    decrementMapFloorIndexUseCase = decrementMapFloorIndexUseCase,
                    fetchMapModifiersUseCase = fetchMapModifiersUseCase,
                    fetchMapThumbnailsUseCase = fetchMapThumbnailsUseCase,
                    fetchComplexMapsUseCase = fetchComplexMapsUseCase
                )
            }
        }
    }
}
