package com.tritiumgaming.shared.operation.domain.ghost.model

import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources

data class GhostType(
    val id: String,
    val name: GhostResources.GhostTitle,
)