package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghost.source

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.GhostType

interface GhostDataSource {

    fun fetchGhosts(): ArrayList<GhostType>

}