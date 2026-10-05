package com.tritiumgaming.feature.missions.ui

import com.tritiumgaming.data.difficulty.mapper.DifficultyResources.GhostResponseType
import com.tritiumgaming.data.ghostname.model.GhostName

data class GhostDetailsUiState(
    val firstName: GhostName? = null,
    val surname: GhostName? = null,
    val responseState: GhostResponseType = GhostResponseType.ALONE
)
