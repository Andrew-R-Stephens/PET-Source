package com.tritiumgaming.feature.missions.ui.components.response

import com.tritiumgaming.feature.missions.ui.Response

data class GhostResponseUiActions(
    val onSelectResponse: (response: Response) -> Unit
)
