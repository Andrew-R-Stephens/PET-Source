package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghost.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghost.repository.GhostRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghost.source.GhostDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.GhostType

class GhostRepositoryImpl(
    context: Context,
    override val localSource: GhostDataSource
): GhostRepository {

    override val ghosts = fetchGhosts(context)

    override fun fetchGhosts(context: Context): List<GhostType> {
        return localSource.fetchGhosts(context)
    }

    override fun getById(id: String): GhostType? {
        return ghosts.find { it.id == id }
    }

}
