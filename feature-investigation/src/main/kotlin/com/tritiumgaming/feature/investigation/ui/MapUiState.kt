package com.tritiumgaming.feature.investigation.ui

import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources

data class MapUiState(
    internal val index: Int = 0,
    internal val name: SimpleMapResources.MapTitle = SimpleMapResources.MapTitle.BLEASDALE_FARMHOUSE,
    internal val size: MapModifierResources.MapSize = MapModifierResources.MapSize.SMALL,
    internal val setupModifier: Float = 1f,
    internal val normalModifier: Float = 1f,
)