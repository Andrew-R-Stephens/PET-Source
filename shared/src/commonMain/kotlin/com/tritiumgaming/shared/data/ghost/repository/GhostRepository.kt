package com.tritiumgaming.shared.data.ghost.repository

import com.tritiumgaming.shared.data.ghost.model.Ghost
import com.tritiumgaming.shared.data.ghost.model.GhostType

interface GhostRepository {

    fun fetchGhostTypes(): Result<List<GhostType>>

    fun fetchGhosts(): Result<List<Ghost>>

}