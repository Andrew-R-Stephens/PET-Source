package com.tritiumgaming.feature.operation.ui.mapsmenu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.core.common.graphics.geometry.Point2D
import com.tritiumgaming.core.common.graphics.geometry.Polygon
import com.tritiumgaming.feature.operation.app.container.OperationContainerProvider
import com.tritiumgaming.feature.operation.ui.mapsmenu.mapdisplay.InteractiveMapUiState
import com.tritiumgaming.shared.operation.domain.map.complex.model.ComplexWorldMap
import com.tritiumgaming.shared.operation.domain.map.complex.model.ComplexWorldMapFloor
import com.tritiumgaming.shared.operation.domain.map.complex.model.ComplexWorldMaps
import com.tritiumgaming.shared.operation.domain.map.complex.model.ComplexWorldRoom
import com.tritiumgaming.shared.operation.domain.map.complex.usecase.FetchComplexMapsUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.mappers.SimpleMapResources
import com.tritiumgaming.shared.operation.domain.map.simple.model.SimpleWorldMap
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.DecrementMapFloorIndexUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.FetchSimpleMapsUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.IncrementMapFloorIndexUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * MapMenuViewModel class
 *
 * @author TritiumGamingStudios
 */
class MapsViewModel(
    private val fetchSimpleMapsUseCase: FetchSimpleMapsUseCase,
    private val fetchComplexMapsUseCase: FetchComplexMapsUseCase,
    private val incrementMapFloorIndexUseCase: IncrementMapFloorIndexUseCase,
    private val decrementMapFloorIndexUseCase: DecrementMapFloorIndexUseCase
) : ViewModel() {

    private val _interactiveMapUiState = MutableStateFlow(InteractiveMapUiState())
    val interactiveMapUiState = _interactiveMapUiState.asStateFlow()

    private var complexMaps: ComplexWorldMaps? = null

    val simpleMaps
        get() =
            try { fetchSimpleMapsUseCase().getOrThrow() }
            catch (e: Exception) { e.printStackTrace(); emptyList() }

    val currentComplexMap: ComplexWorldMap?
        get() = complexMaps?.getMapById(interactiveMapUiState.value.mapId)

    fun getSimpleMap(): SimpleWorldMap = simpleMaps
        .first { map -> map.mapId == interactiveMapUiState.value.mapId }

    fun getFloorByIndex(index: Int): ComplexWorldMapFloor? =
        currentComplexMap?.mapFloors[interactiveMapUiState.value.floorIndex]

    fun getFloorImage(): SimpleMapResources.MapFloorImage = getSimpleMap()
        .getFloorImage(interactiveMapUiState.value.floorIndex)

    fun getSelectedRoom(): ComplexWorldRoom? {
        return currentComplexMap?.let { map: ComplexWorldMap ->
            map.getFloor(interactiveMapUiState.value.floorIndex).rooms[0]
        }
    }

    private fun getRooms(): List<ComplexWorldRoom> =
        currentComplexMap?.mapFloors[interactiveMapUiState.value.floorIndex]
            ?.rooms?.map { room -> room
        } ?: emptyList()

    private fun getRoomNameById(id: Int): String? =
        currentComplexMap?.mapFloors[interactiveMapUiState.value.floorIndex]
            ?.rooms?.first{ it.id == id }?.name

    fun getRoomById(id: Int): ComplexWorldRoom? {
        return currentComplexMap?.let { map: ComplexWorldMap ->
            map.getFloor(interactiveMapUiState.value.floorIndex)
                .rooms.firstOrNull { room ->
                room.id == id }
        }
    }

    fun setSelectedRoomAtPoint(
        point: Point2D.Point2DFloat,
        scaleX: Float,
        scaleY: Float,
        translateX: Float,
        translateY: Float
    ) {

        viewModelScope.launch { withContext(Dispatchers.IO) {

            currentComplexMap?.let { map: ComplexWorldMap ->
                map.getFloor(interactiveMapUiState.value.floorIndex)
                    .rooms.forEach { room ->
                    val roomShape = Polygon()
                    for (p in room.roomArea.points) {
                        val x = ((p.x * scaleX) + (translateX)).toInt()
                        val y = ((p.y * scaleY) + (translateY)).toInt()
                        roomShape.addPoint(x, y)
                    }

                    if (roomShape.contains(point)) {
                        setCurrentRoom(
                            if (room.id != interactiveMapUiState.value.roomId) room.id
                            else 0
                        )
                        return@forEach
                    }
                }
            }
        } }

    }

    fun setCurrentMap(id: String) {
        _interactiveMapUiState.update {

            val simpleMap = simpleMaps.first{ map -> map.mapId == id }
            val complexMapFloors = complexMaps?.getMapById(id)?.mapFloors[simpleMap.defaultFloor]

            it.copy(
                mapId = id,
                floorIndex = simpleMap.defaultFloor,
                roomId = 0,
                roomName = complexMapFloors
                    ?.floorRoomNames[0] ?: "",
                roomDropdownList = complexMapFloors
                    ?.rooms?.filter {
                        room -> room.id != 0 } ?: emptyList()
            )

        }
    }

    fun setCurrentRoom(id: Int) {
        _interactiveMapUiState.update {
            val complexMapFloors = currentComplexMap?.mapFloors[it.floorIndex]

            it.copy(
                roomId = id,
                roomName = complexMapFloors?.rooms?.find { room ->
                    room.id == id }?.name ?: "",
                roomDropdownList = complexMapFloors?.rooms?.filter { room ->
                    room.id != id } ?: emptyList()
            )
        }
    }

    fun incrementFloor() {
        _interactiveMapUiState.update {
            try {
                val newIndex = incrementMapFloorIndexUseCase(
                    it.mapId,
                    it.floorIndex
                ).getOrThrow()

                val complexMapFloors = currentComplexMap?.mapFloors[newIndex]

                it.copy(
                    floorIndex = newIndex,
                    roomId = 0,
                    roomName = complexMapFloors?.rooms?.find { room ->
                        room.id == 0 }?.name ?: "",
                    roomDropdownList = complexMapFloors?.rooms?.filter { room ->
                        room.id != 0 } ?: emptyList()
                )

            } catch (e: Exception) {
                e.printStackTrace()
                it
            }

        }
    }

    fun decrementFloor() {
        _interactiveMapUiState.update {
            try {
                val newIndex = decrementMapFloorIndexUseCase(
                    it.mapId,
                    it.floorIndex
                ).getOrThrow()

                val complexMapFloors = currentComplexMap?.mapFloors[newIndex]

                it.copy(
                    floorIndex = newIndex,
                    roomId = 0,
                    roomName = complexMapFloors?.rooms?.find { room ->
                        room.id == 0 }?.name ?: "",
                    roomDropdownList = complexMapFloors?.rooms?.filter { room ->
                        room.id != 0 } ?: emptyList()
                )

            } catch (e: Exception) {
                e.printStackTrace()
                it
            }
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
                val application = this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY]
                val container = (application as OperationContainerProvider).provideOperationContainer()

                val fetchSimpleMapsUseCase = container.fetchSimpleMapsUseCase
                val fetchComplexMapsUseCase = container.fetchComplexMapsUseCase
                val incrementMapFloorIndexUseCase = container.incrementMapFloorIndexUseCase
                val decrementMapFloorIndexUseCase = container.decrementMapFloorIndexUseCase

                MapsViewModel(
                    fetchSimpleMapsUseCase = fetchSimpleMapsUseCase,
                    fetchComplexMapsUseCase = fetchComplexMapsUseCase,
                    incrementMapFloorIndexUseCase = incrementMapFloorIndexUseCase,
                    decrementMapFloorIndexUseCase = decrementMapFloorIndexUseCase
                )
            }
        }
    }
}