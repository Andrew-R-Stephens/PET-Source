package com.tritiumgaming.feature.investigation.ui

import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources

data class DifficultyUiState(
    internal val index: Int = 0,
    internal val name: DifficultyResources.DifficultyTitle = DifficultyResources.DifficultyTitle.AMATEUR,
    internal val modifier: Float = 0f,
    internal val time: Long = 0L,
    internal val initialSanity: Float = 0f,
    val responseType: DifficultyResources.DifficultyResponseType = DifficultyResources.DifficultyResponseType.KNOWN
)