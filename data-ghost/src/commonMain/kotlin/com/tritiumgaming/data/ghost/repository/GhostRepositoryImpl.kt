package com.tritiumgaming.data.ghost.repository

import com.tritiumgaming.data.ghost.dto.toDomain
import com.tritiumgaming.data.ghost.dto.toGhostType
import com.tritiumgaming.data.ghost.model.Ghost
import com.tritiumgaming.data.ghost.model.GhostType
import com.tritiumgaming.data.ghost.source.GhostDataSource

class GhostRepositoryImpl(
    val ghostLocalDataSource: GhostDataSource
): GhostRepository {

    override fun fetchGhostTypes(): Result<List<GhostType>> {
        val result = ghostLocalDataSource.get()

        result.exceptionOrNull()?.printStackTrace()

        return result.map { it.toGhostType() }
    }

    override fun fetchGhosts(): Result<List<Ghost>> {
        val result = ghostLocalDataSource.get()

        result.exceptionOrNull()?.printStackTrace()

        return result.map { it.toDomain() }
    }

}