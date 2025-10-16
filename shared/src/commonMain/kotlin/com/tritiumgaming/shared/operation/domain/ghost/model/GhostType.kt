package com.tritiumgaming.shared.operation.domain.ghost.model

import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostIdentifier
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostTitle

data class GhostType(
    val id: GhostIdentifier,
    val name: GhostTitle,
)