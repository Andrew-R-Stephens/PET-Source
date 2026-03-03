package com.tritiumgaming.feature.investigation.ui.tool.configs

import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources

data class MapConfigUiState(
    internal val name: SimpleMapResources.MapTitle = SimpleMapResources.MapTitle.BLEASDALE_FARMHOUSE,
    internal val enabled: Boolean = true,
    internal val allMaps: List<SimpleMapResources.MapTitle> = emptyList()
)