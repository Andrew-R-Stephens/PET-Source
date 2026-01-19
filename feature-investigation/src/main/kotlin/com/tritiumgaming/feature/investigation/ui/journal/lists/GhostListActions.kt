package com.tritiumgaming.feature.investigation.ui.journal.lists

import com.tritiumgaming.shared.data.ghost.mapper.GhostResources
import com.tritiumgaming.shared.data.ghost.model.GhostType

class GhostListActions(
    val onFindGhostById: (id: GhostResources.GhostIdentifier) -> GhostType?,
    val onNameClick: (GhostType) -> Unit
)