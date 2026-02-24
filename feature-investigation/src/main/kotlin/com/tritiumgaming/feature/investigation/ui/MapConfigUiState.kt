package com.tritiumgaming.feature.investigation.ui

import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources

data class MapConfigUiState(
    internal val name: SimpleMapResources.MapTitle = SimpleMapResources.MapTitle.BLEASDALE_FARMHOUSE,
    internal val allMaps: List<SimpleMapResources.MapTitle> = emptyList()
)