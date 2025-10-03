package com.tritiumgaming.shared.operation.domain.ghost.model

import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostTitle

data class Ghost(
    val id: String,
    val name: GhostTitle,
    val normalEvidence: List<String>,
    val strictEvidence: List<String>,
)