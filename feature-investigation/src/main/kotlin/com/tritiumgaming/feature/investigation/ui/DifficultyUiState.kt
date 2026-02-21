package com.tritiumgaming.feature.investigation.ui

import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyType

data class DifficultyUiState(
    internal val index: Int = 0,
    internal val name: DifficultyTitle = DifficultyTitle.AMATEUR,
    internal val modifier: Float = 0f,
    internal val time: Long = 0L,
    internal val initialSanity: Float = 0f,
    val responseType: DifficultyResponseType = DifficultyResponseType.KNOWN,
    val allDifficulties: List<DifficultyTitle> = emptyList()
) {
    internal val type: DifficultyType = DifficultyType.entries[index]
}