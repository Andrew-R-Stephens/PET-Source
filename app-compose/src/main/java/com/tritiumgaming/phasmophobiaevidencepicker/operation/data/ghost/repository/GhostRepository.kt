package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghost.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghost.source.local.GhostLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.journal.type.ghost.GhostType

class GhostRepository(
    context: Context,
    private val localSource: GhostLocalDataSource
) {

    val ghosts = fetchGhosts(context)

    private fun fetchGhosts(context: Context): List<GhostType> {
        return localSource.fetchGhosts(context)
    }

    fun getById(id: String): GhostType? {
        return ghosts.find { it.id == id }
    }

}
