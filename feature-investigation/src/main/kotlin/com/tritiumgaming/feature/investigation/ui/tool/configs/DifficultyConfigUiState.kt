package com.tritiumgaming.feature.investigation.ui.tool.configs

import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyTitle.AMATEUR

data class DifficultyConfigUiState(
    internal val name: DifficultyTitle = AMATEUR,
    val allDifficulties: List<DifficultyTitle> = emptyList()
)