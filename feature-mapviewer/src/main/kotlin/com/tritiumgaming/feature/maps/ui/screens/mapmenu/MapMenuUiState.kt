package com.tritiumgaming.feature.maps.ui.screens.mapmenu

import com.tritiumgaming.data.map.model.simple.SimpleWorldMap

data class MapMenuUiState(
    val maps: List<SimpleWorldMap> = emptyList()
)
