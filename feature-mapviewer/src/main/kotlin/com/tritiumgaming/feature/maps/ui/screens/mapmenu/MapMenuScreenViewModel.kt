package com.tritiumgaming.feature.maps.ui.screens.mapmenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.data.map.model.worldmap.WorldMaps
import com.tritiumgaming.data.map.usecase.GetMapsFlowUseCase
import com.tritiumgaming.feature.maps.app.container.MapViewerContainerProvider
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * MapMenuViewModel class
 *
 * @author TritiumGamingStudios
 */
class MapMenuScreenViewModel(
    private val getMapsFlowUseCase: GetMapsFlowUseCase
) : ViewModel() {

    private val _worldMaps = getMapsFlowUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = WorldMaps()
        )
    private val _simpleMaps = _worldMaps.map { worldMaps ->
        worldMaps.worldMaps.values.map { it.simpleMap } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    val simpleMaps = _simpleMaps

    companion object {

        const val TAG = "MapMenuScreenViewModel"

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                val container = (application as MapViewerContainerProvider).provideMapViewerContainer()

                val getMapsFlowUseCase = container.fetchMapsFlowUseCase

                MapMenuScreenViewModel(
                    getMapsFlowUseCase = getMapsFlowUseCase
                )
            }
        }
    }
}