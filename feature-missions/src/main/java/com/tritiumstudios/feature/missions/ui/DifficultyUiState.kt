package com.tritiumstudios.feature.missions.ui

import com.tritiumgaming.shared.operation.domain.difficulty.mapper.DifficultyResources.DifficultyResponseType

data class DifficultyUiState(
    internal val responseType: DifficultyResponseType = DifficultyResponseType.KNOWN
)
