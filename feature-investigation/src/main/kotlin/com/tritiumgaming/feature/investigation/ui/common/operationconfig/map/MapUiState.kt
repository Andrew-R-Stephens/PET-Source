package com.tritiumgaming.feature.investigation.ui.common.operationconfig.map

import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSize
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources.MapTitle

data class MapUiState(
    internal val index: Int = 0,
    internal val name: MapTitle = MapTitle.BLEASDALE_FARMHOUSE,
    internal val size: MapSize = MapSize.SMALL,
    internal val setupModifier: Float = 1f,
    internal val normalModifier: Float = 1f,
)
