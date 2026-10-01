package com.tritiumgaming.data.ghost.model

import com.tritiumgaming.data.ghost.mapper.GhostResources.GhostIdentifier
import com.tritiumgaming.data.ghost.mapper.GhostResources.GhostTitle

data class GhostType(
    val id: GhostIdentifier,
    val name: GhostTitle,
)