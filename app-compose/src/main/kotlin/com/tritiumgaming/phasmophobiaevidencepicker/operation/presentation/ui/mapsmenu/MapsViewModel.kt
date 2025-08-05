package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.ComplexWorldMap
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.ComplexWorldMaps
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.usecase.FetchComplexMapsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.usecase.FetchMapModifiersUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.mappers.SimpleMapResources
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.SimpleWorldMap
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.DecrementMapFloorIndexUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.FetchMapThumbnailsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.FetchSimpleMapsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.IncrementMapFloorIndexUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu.mapdisplay.MapDisplayUiState
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu.mapdisplay.model.InteractiveWorldMap
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * MapMenuViewModel class
 *
 * @author TritiumGamingStudios
 */
class MapsViewModel(
    private val fetchSimpleMapsUseCase: FetchSimpleMapsUseCase,
    private val fetchComplexMapsUseCase: FetchComplexMapsUseCase,
    private val incrementMapFloorIndexUseCase: IncrementMapFloorIndexUseCase,
    private val decrementMapFloorIndexUseCase: DecrementMapFloorIndexUseCase,
    private val fetchMapModifiersUseCase: FetchMapModifiersUseCase,
    private val fetchMapThumbnailsUseCase: FetchMapThumbnailsUseCase
) : ViewModel() {

    private val _mapDisplayUiState = MutableStateFlow(MapDisplayUiState())
    val mapDisplayUiState = _mapDisplayUiState.asStateFlow()

    var displayJob: Job? = null

    var complexMaps: ComplexWorldMaps? = null

    val simpleMaps
        get() =
            try { fetchSimpleMapsUseCase().getOrThrow() }
            catch (e: Exception) { e.printStackTrace(); emptyList() }

    val mapThumbnails: List<SimpleMapResources.MapThumbnail>
        get() = simpleMaps.map { map -> map.thumbnailImage }

    val currentSimpleMap: SimpleWorldMap
        get() = simpleMaps.first { it.mapId == mapDisplayUiState.value.currentId }
    val currentComplexMap: ComplexWorldMap?
        get() = complexMaps?.getMapById(mapDisplayUiState.value.currentId)

    val interactiveWorldMap: InteractiveWorldMap?
        get() = currentComplexMap?.let { complexMap ->
            InteractiveWorldMap(currentSimpleMap, complexMap)
        } ?: throw Exception("ComplexWorldMap is null")

    fun setCurrentMapId(id: String) {
        _mapDisplayUiState.update {
            it.copy(
                currentId = id,
                currentFloor = simpleMaps.first { map -> map.mapId == id }.defaultFloor
            )
        }
    }

    fun incrementFloorIndex() {
        _mapDisplayUiState.update {
            it.copy(
                currentFloor = incrementMapFloorIndexUseCase(it.currentFloor)
            )
        }
    }

    fun decrementFloorIndex() {
        _mapDisplayUiState.update {
            it.copy(
                currentFloor = decrementMapFloorIndexUseCase(it.currentFloor)
            )
        }
    }

    init {
        Log.d("MapsViewModel", "initializing")
        viewModelScope.launch {
            fetchSimpleMapsUseCase()
        }

        viewModelScope.launch {
            try {
                complexMaps = fetchComplexMapsUseCase().getOrThrow()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val container =
                    (this[APPLICATION_KEY] as PETApplication).operationsContainer

                val fetchSimpleMapsUseCase = container.fetchSimpleMapsUseCase
                val fetchComplexMapsUseCase = container.fetchComplexMapsUseCase
                val incrementMapFloorIndexUseCase = container.incrementMapFloorIndexUseCase
                val decrementMapFloorIndexUseCase = container.decrementMapFloorIndexUseCase
                val fetchMapModifiersUseCase = container.fetchMapModifiersUseCase
                val fetchMapThumbnailsUseCase = container.fetchMapThumbnailsUseCase

                MapsViewModel(
                    fetchSimpleMapsUseCase = fetchSimpleMapsUseCase,
                    fetchComplexMapsUseCase = fetchComplexMapsUseCase,
                    incrementMapFloorIndexUseCase = incrementMapFloorIndexUseCase,
                    decrementMapFloorIndexUseCase = decrementMapFloorIndexUseCase,
                    fetchMapModifiersUseCase = fetchMapModifiersUseCase,
                    fetchMapThumbnailsUseCase = fetchMapThumbnailsUseCase
                )
            }
        }
    }
}