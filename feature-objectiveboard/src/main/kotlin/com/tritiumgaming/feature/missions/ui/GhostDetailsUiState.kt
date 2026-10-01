package com.tritiumgaming.feature.missions.ui

import com.tritiumgaming.data.ghostname.model.GhostName
import com.tritiumgaming.feature.missions.ui.ObjectiveBoardViewModel.Companion.ALONE

typealias Response = Int

data class GhostDetailsUiState(
    val firstName: GhostName? = null,
    val surname: GhostName? = null,
    val responseState: Response = ALONE
)
