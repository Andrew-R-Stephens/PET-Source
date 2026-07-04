package com.tritiumgaming.feature.missions.ui.components.response

import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType

data class GhostResponseUiState(
    internal val responseType: DifficultyResponseType = DifficultyResponseType.KNOWN
)
