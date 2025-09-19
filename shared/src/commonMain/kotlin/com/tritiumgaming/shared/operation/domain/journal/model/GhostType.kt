package com.tritiumgaming.shared.operation.domain.journal.model

import com.tritiumgaming.shared.operation.domain.journal.mapper.GhostResources.GhostTitle

data class GhostType(
    val id: String,
    val name: GhostTitle,
)