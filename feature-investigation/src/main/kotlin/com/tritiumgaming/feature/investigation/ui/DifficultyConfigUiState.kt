package com.tritiumgaming.feature.investigation.ui

import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyType

data class DifficultyConfigUiState(
    internal val name: DifficultyTitle = DifficultyTitle.AMATEUR,
    val allDifficulties: List<DifficultyTitle> = emptyList()
)