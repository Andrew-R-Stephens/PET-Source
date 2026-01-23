package com.tritiumgaming.feature.maps.ui

import com.tritiumgaming.shared.data.map.simple.model.SimpleWorldMap

data class MapMenuUiState(
    val maps: List<SimpleWorldMap> = emptyList()
)
