package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.mapper.GhostResources.GhostTitle

data class GhostType(
    val id: String,
    val name: GhostTitle,
)