package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghost.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghost.repository.GhostRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghost.source.GhostDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.GhostType

class GhostRepositoryImpl(
    override val localSource: GhostDataSource
): GhostRepository {

    override fun fetchGhosts(): List<GhostType> {
        return localSource.fetchGhosts()
    }

}
