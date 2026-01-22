package com.tritiumgaming.feature.missions.ui

import com.tritiumgaming.feature.missions.ui.ObjectivesViewModel.Companion.UNKNOWN
import com.tritiumgaming.shared.data.ghostname.model.GhostName

typealias Response = Int

data class GhostDetailsUiState(
    val firstName: GhostName? = null,
    val surname: GhostName? = null,
    val responseState: Response = UNKNOWN
)
