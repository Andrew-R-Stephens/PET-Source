package com.tritiumgaming.feature.investigation.ui.components.tool.configs

import com.tritiumgaming.data.challenge.mapper.ChallengeResources.ChallengeTitle
import com.tritiumgaming.data.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.data.difficulty.mapper.DifficultyResources.DifficultyType

internal data class DifficultyConfigUiState(
    internal val type: DifficultyType = DifficultyType.AMATEUR,
    internal val name: DifficultyTitle = DifficultyTitle.AMATEUR,
    internal val challengeTitle: ChallengeTitle? = null,
    val allDifficulties: List<DifficultyTitle> = emptyList()
)