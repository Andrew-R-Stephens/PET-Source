package com.tritiumgaming.feature.missions.ui.components.response

import com.tritiumgaming.shared.data.operation.model.Response

data class GhostResponseUiActions(
    val onSelectResponse: (response: Response) -> Unit
)
