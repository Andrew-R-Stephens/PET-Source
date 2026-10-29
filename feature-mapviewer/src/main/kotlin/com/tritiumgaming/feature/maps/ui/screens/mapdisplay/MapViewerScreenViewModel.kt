package com.tritiumgaming.feature.maps.ui.screens.mapdisplay

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.core.common.graphics.geometry.Point2D
import com.tritiumgaming.core.common.graphics.geometry.Polygon
import com.tritiumgaming.data.map.model.complex.ComplexWorldMap
import com.tritiumgaming.data.map.model.worldmap.WorldMaps
import com.tritiumgaming.data.map.usecase.GetMapsFlowUseCase
import com.tritiumgaming.feature.maps.app.container.MapViewerContainerProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * MapMenuViewModel class
 *
 * @author TritiumGamingStudios
 */
class MapViewerScreenViewModel(
    private val getMapsFlowUseCase: GetMapsFlowUseCase
) : ViewModel() {

    private val _worldMaps = getMapsFlowUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = WorldMaps()
        )

    private val _interactiveMapState = MutableStateFlow(InteractiveMapState())
    fun setCurrentMap(mapId: String) {
        val map = _worldMaps.value.getMap(mapId)
        if(map == null) {
            Log.e(TAG, "[InteractiveMapState: SetCurrentMap] Map not found yet: $mapId")
        } else {
            Log.d(TAG, "[InteractiveMapState: SetCurrentMap] Map found. $mapId: ${map.complexMap.mapName} / ${map.defaultFloorIndex}\ndefault floor / ${map.complexMap.mapFloors[map.defaultFloorIndex].rooms.size} rooms")
        }

        val defaultFloor = map?.defaultFloorIndex ?: 0
        val mapFloors = map?.complexMap?.mapFloors ?: emptyList()
        val floor = mapFloors.getOrNull(defaultFloor)
        val floorId = floor?.floorId?.toString() ?: ""
        val roomId = floor?.rooms?.getOrNull(0)?.id?.toString() ?: ""

        Log.d(TAG, "[InteractiveMapState: SetCurrentMap] Setting mapId: $mapId, floorId: $floorId, roomId: $roomId")

        _interactiveMapState.update {
            InteractiveMapState(
                mapId = map?.id ?: mapId,
                floorId = floorId,
                roomId = roomId
            )
        }
    }
    fun incrementFloor() {
        val worldMap = _worldMaps.value.getMap(_interactiveMapState.value.mapId)
        val floorCount = worldMap?.simpleMap?.floorCount ?: 0
        if (floorCount <= 0) return
        val floors = worldMap?.complexMap?.mapFloors ?: emptyList()
        if (floors.isEmpty()) return
        val currentFloorIndex = floors.indexOfFirst {
            it.floorId.toString() == _interactiveMapState.value.floorId }
        val safeCurrentIndex = if (currentFloorIndex < 0) 0 else currentFloorIndex

        val newFloorIndex = (safeCurrentIndex + 1) % floorCount
        val newFloorId = floors.getOrNull(newFloorIndex)?.floorId?.toString() ?: return

        _interactiveMapState.update {
            it.copy(
                floorId = newFloorId
            )
        }
    }
    fun decrementFloor() {
        val worldMap = _worldMaps.value.getMap(_interactiveMapState.value.mapId)
        val floorCount = worldMap?.simpleMap?.floorCount ?: 0
        if (floorCount <= 0) return
        val floors = worldMap?.complexMap?.mapFloors ?: emptyList()
        if (floors.isEmpty()) return
        val currentFloorIndex = floors.indexOfFirst {
            it.floorId.toString() == _interactiveMapState.value.floorId }
        val safeCurrentIndex = if (currentFloorIndex < 0) 0 else currentFloorIndex

        val newFloorIndex = (safeCurrentIndex - 1 + floorCount) % floorCount
        val newFloorId = floors.getOrNull(newFloorIndex)?.floorId?.toString() ?: return

        _interactiveMapState.update {
            it.copy(
                floorId = newFloorId
            )
        }
    }
    fun setCurrentRoomAtPoint(
        point: Point2D.Point2DFloat,
        scaleX: Float,
        scaleY: Float,
        translateX: Float,
        translateY: Float
    ) {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                val worldMap = _worldMaps.value.getMap(_interactiveMapState.value.mapId)
                val complexWorldMap = worldMap?.complexMap

                complexWorldMap?.let { map: ComplexWorldMap ->
                    val floor = map.mapFloors.firstOrNull { it.floorId.toString() == _interactiveMapState.value.floorId }
                    floor?.rooms?.forEach { room ->
                        val roomShape = Polygon()
                        for ((rx, ry) in room.roomArea.points) {
                            val x = ((rx * scaleX) + (translateX)).toInt()
                            val y = ((ry * scaleY) + (translateY)).toInt()
                            roomShape.addPoint(x, y)
                        }

                        if (roomShape.contains(point)) {
                            _interactiveMapState.update {
                                it.copy(
                                    roomId = room.id.toString()
                                )
                            }
                            return@forEach
                        }
                    }
                }
            }
        }
    }
    fun setCurrentRoom(id: String) {
        _interactiveMapState.update {
            it.copy(
                roomId = id
            )
        }
    }

    private val _interactiveMapUiState = combine(
        _worldMaps,
        _interactiveMapState
    ){ worldMaps, state ->
        Log.d(TAG, "[InteractiveMapUiState] Checking map: ${state.mapId}")
        val worldMap = worldMaps.getMap(state.mapId)

        if(worldMap == null) {
            Log.d(TAG, "[InteractiveMapUiState] Map not found: ${state.mapId}")
            return@combine InteractiveMapUiState(mapId = state.mapId)
        }

        val simpleMap = worldMap.simpleMap
        val complexMap = worldMap.complexMap

        val mapId = state.mapId
        val mapTitle = simpleMap.mapName

        val defaultFloorIndex = worldMap.defaultFloorIndex
        val floors = complexMap.mapFloors
        val floorCount = floors.size

        val selectedFloorIndex = if (state.floorId.isNotEmpty()) {
            val idx = complexMap.getFloorIndex(state.floorId)
            if (idx >= 0) idx else defaultFloorIndex
        } else {
            defaultFloorIndex
        }

        val selectedFloor = floors.getOrNull(selectedFloorIndex) ?: floors.getOrNull(0)
        val floorId = selectedFloor?.floorId?.toString() ?: state.floorId
        val floorTitle = simpleMap.mapFloors.getOrNull(selectedFloorIndex)?.layerName ?: com.tritiumgaming.data.map.mappers.SimpleMapResources.MapFloorTitle.FIRST_FLOOR
        val floorImage = simpleMap.getFloorImage(selectedFloorIndex)

        val roomList = selectedFloor?.rooms ?: emptyList()
        val selectedRoom = if (state.roomId.isNotEmpty()) {
            selectedFloor?.getRoomById(state.roomId) ?: roomList.getOrNull(0)
        } else {
            roomList.getOrNull(0)
        }
        val roomId = selectedRoom?.id?.toString() ?: state.roomId
        val roomName = selectedRoom?.name ?: ""

        InteractiveMapUiState(
            mapId = mapId,
            mapTitle = mapTitle,
            floorCount = floorCount,
            floorId = floorId,
            selectedFloor = selectedFloor ?: com.tritiumgaming.data.map.model.complex.ComplexWorldMapFloor(0, "", "", com.tritiumgaming.data.map.model.complex.ComplexWorldMapFloorLayerType.FIRST_FLOOR, emptyList(), emptyList()),
            floorIndex = selectedFloorIndex,
            floorTitle = floorTitle,
            floorImage = floorImage,
            roomId = roomId,
            roomName = roomName,
            roomList = roomList,
            selectedRoom = selectedRoom
        )

    }
    .distinctUntilChanged()
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = InteractiveMapUiState()
    )
    val interactiveMapUiState = _interactiveMapUiState

    companion object {

        const val TAG = "MapViewerScreenViewModel"

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                val container = (application as MapViewerContainerProvider).provideMapViewerContainer()

                val getMapsFlowUseCase = container.fetchMapsFlowUseCase

                MapViewerScreenViewModel(
                    getMapsFlowUseCase = getMapsFlowUseCase
                )
            }
        }
    }

    data class InteractiveMapState(
        val mapId: String = "",
        val floorId: String = "",
        val roomId: String = "",
    )


}