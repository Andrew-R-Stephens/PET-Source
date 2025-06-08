package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghost.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghost.source.GhostDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.GhostType

interface GhostRepository {
    val localSource: GhostDataSource

    fun fetchGhosts(): Result<List<GhostType>>

}
