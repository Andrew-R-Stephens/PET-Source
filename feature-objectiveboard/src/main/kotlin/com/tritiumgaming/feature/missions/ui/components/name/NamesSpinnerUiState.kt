package com.tritiumgaming.feature.missions.ui.components.name

import com.tritiumgaming.shared.data.ghostname.model.GhostName

data class NamesSpinnerUiState(
    val firstNames: List<GhostName> = emptyList(),
    val surnames: List<GhostName> = emptyList(),
)