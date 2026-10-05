package com.tritiumgaming.feature.missions.ui.components.response

import com.tritiumgaming.data.difficulty.mapper.DifficultyResources.GhostResponseType

data class GhostResponseUiActions(
    val onSelectResponse: (response: GhostResponseType) -> Unit
)
