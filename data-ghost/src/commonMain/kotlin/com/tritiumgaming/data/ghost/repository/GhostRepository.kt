package com.tritiumgaming.data.ghost.repository

import com.tritiumgaming.data.ghost.model.Ghost
import com.tritiumgaming.data.ghost.model.GhostType

interface GhostRepository {

    fun fetchGhostTypes(): Result<List<GhostType>>

    fun fetchGhosts(): Result<List<Ghost>>

}