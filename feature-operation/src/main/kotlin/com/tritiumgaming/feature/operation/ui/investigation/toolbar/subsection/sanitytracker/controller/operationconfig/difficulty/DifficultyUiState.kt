package com.tritiumgaming.feature.operation.ui.investigation.toolbar.subsection.sanitytracker.controller.operationconfig.difficulty

import com.tritiumgaming.shared.operation.domain.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.shared.operation.domain.difficulty.mapper.DifficultyResources.DifficultyTitle

data class DifficultyUiState(
    internal val index: Int = 0,
    internal val name: DifficultyTitle = DifficultyTitle.AMATEUR,
    internal val modifier: Float = 0f,
    internal val time: Long = 0L,
    internal val initialSanity: Float = 0f,
    internal val responseType: DifficultyResponseType = DifficultyResponseType.KNOWN
)
