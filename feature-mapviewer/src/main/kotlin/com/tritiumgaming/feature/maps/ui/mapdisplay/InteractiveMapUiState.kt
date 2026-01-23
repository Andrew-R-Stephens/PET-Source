package com.tritiumgaming.feature.maps.ui.mapdisplay

import com.tritiumgaming.shared.data.map.complex.model.ComplexWorldRoom
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources

data class InteractiveMapUiState(
    val mapId: String = "",
    val mapName: SimpleMapResources.MapTitle = SimpleMapResources.MapTitle.SUNNY_MEADOWS,
    val floorIndex: Int = 0,
    val floorTitle: SimpleMapResources.MapFloorTitle = SimpleMapResources.MapFloorTitle.FIRST_FLOOR,
    val floorCount: Int = 0,
    val roomId: Int = 0,
    val roomName: String = "",
    val roomDropdownList: List<ComplexWorldRoom> = emptyList()
)
