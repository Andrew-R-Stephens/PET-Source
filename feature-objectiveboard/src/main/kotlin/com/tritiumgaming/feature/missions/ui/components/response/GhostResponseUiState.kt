package com.tritiumgaming.feature.missions.ui.components.response

import com.tritiumgaming.data.difficulty.mapper.DifficultyResources.GhostResponsePresentation

data class GhostResponseUiState(
    internal val responseType: GhostResponsePresentation = GhostResponsePresentation.KNOWN
)
