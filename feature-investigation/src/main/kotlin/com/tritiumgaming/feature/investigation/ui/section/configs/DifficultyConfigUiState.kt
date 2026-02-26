package com.tritiumgaming.feature.investigation.ui.section.configs

import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyTitle

data class DifficultyConfigUiState(
    internal val name: DifficultyTitle = DifficultyTitle.AMATEUR,
    val allDifficulties: List<DifficultyTitle> = emptyList()
)