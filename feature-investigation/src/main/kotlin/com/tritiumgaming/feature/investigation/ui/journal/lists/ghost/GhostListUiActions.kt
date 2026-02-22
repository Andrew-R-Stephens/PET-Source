package com.tritiumgaming.feature.investigation.ui.journal.lists.ghost

import com.tritiumgaming.shared.data.ghost.mapper.GhostResources
import com.tritiumgaming.shared.data.ghost.model.GhostType

data class GhostListUiActions(
    val onFindGhostById: (id: GhostResources.GhostIdentifier) -> GhostType? = {null},
    val onNameClick: (GhostType) -> Unit = {}
)