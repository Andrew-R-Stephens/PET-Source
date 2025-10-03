package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu.mapdisplay

import com.tritiumgaming.shared.operation.domain.map.complex.model.ComplexWorldRoom

data class InteractiveMapUiState(
    val mapId: String = "",
    val floorIndex: Int = 0,
    val roomId: Int = 0,
    val roomName: String = "",
    val roomDropdownList: List<ComplexWorldRoom> = emptyList()
)
