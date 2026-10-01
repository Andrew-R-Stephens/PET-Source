package com.tritiumgaming.feature.maps.ui.mapdisplay

import com.tritiumgaming.data.map.complex.model.ComplexWorldRoom
import com.tritiumgaming.data.map.simple.mappers.SimpleMapResources.MapFloorTitle
import com.tritiumgaming.data.map.simple.mappers.SimpleMapResources.MapTitle

data class InteractiveMapUiState(
    val mapId: String = "",
    val mapName: MapTitle = MapTitle.SUNNY_MEADOWS,
    val floorIndex: Int = 0,
    val floorTitle: MapFloorTitle = MapFloorTitle.FIRST_FLOOR,
    val floorCount: Int = 0,
    val roomId: Int = 0,
    val roomName: String = "",
    val roomDropdownList: List<ComplexWorldRoom> = emptyList()
)
