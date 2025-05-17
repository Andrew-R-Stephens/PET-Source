package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghost.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghost.source.GhostDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.GhostType

interface GhostRepository {

    val localSource: GhostDataSource
    val ghosts: List<GhostType>

    fun fetchGhosts(context: Context): List<GhostType>
    fun getById(id: String): GhostType?

}
