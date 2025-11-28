package com.tritiumgaming.shared.data.ghost.model

import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostIdentifier
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostTitle

data class GhostType(
    val id: GhostIdentifier,
    val name: GhostTitle,
)