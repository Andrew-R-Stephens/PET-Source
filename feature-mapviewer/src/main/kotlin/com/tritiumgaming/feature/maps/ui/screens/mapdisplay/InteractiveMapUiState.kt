package com.tritiumgaming.feature.maps.ui.screens.mapdisplay

import com.tritiumgaming.data.map.mappers.SimpleMapResources
import com.tritiumgaming.data.map.model.complex.ComplexWorldMapFloor
import com.tritiumgaming.data.map.model.complex.ComplexWorldMapFloorLayerType
import com.tritiumgaming.data.map.model.complex.ComplexWorldRoom

data class InteractiveMapUiState(
    val mapId: String = "",
    val mapTitle: SimpleMapResources.MapTitle = SimpleMapResources.MapTitle.BLEASDALE_FARMHOUSE,
    val floorCount: Int = 0,
    val floorId: String = "",
    val selectedFloor: ComplexWorldMapFloor = ComplexWorldMapFloor(
        floorId = 0,
        floorName = "",
        floorImage = "",
        floorLayer = ComplexWorldMapFloorLayerType.FIRST_FLOOR,
        floorRooms = emptyList(),
        floorPOIs = emptyList()
    ),
    val floorIndex: Int = 0,
    val floorTitle: SimpleMapResources.MapFloorTitle = SimpleMapResources.MapFloorTitle.FIRST_FLOOR,
    val floorImage: SimpleMapResources.MapFloorImage = SimpleMapResources.MapFloorImage.BLEASDALE_FARMHOUSE_FIRST_FLOOR,
    val roomId: String = "",
    val roomName: String = "",
    val selectedRoom: ComplexWorldRoom? = null,
    val roomList: List<ComplexWorldRoom> = emptyList(),
)
