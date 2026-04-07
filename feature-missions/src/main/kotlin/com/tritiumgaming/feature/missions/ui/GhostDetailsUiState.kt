package com.tritiumgaming.feature.missions.ui

import com.tritiumgaming.feature.missions.ui.ObjectivesViewModel.Companion.ALONE
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType.UNKNOWN
import com.tritiumgaming.shared.data.ghostname.model.GhostName

typealias Response = Int

data class GhostDetailsUiState(
    val firstName: GhostName? = null,
    val surname: GhostName? = null,
    val responseState: Response = ALONE
)
