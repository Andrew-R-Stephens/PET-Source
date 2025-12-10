package com.tritiumgaming.feature.missions.ui

import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType

data class DifficultyUiState(
    internal val responseType: DifficultyResponseType = DifficultyResponseType.KNOWN
)
