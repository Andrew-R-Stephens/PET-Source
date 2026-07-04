package com.tritiumgaming.feature.missions.ui

import com.tritiumgaming.feature.missions.ui.ObjectiveBoardViewModel.Companion.ALONE
import com.tritiumgaming.shared.data.ghostname.model.GhostName
import com.tritiumgaming.shared.data.operation.model.Response

data class GhostDetailsUiState(
    val firstName: GhostName? = null,
    val surname: GhostName? = null,
    val responseState: Response = ALONE
)
