package com.tritiumgaming.data.ghost.repository

import com.tritiumgaming.data.ghost.dto.toDomain
import com.tritiumgaming.data.ghost.dto.toGhostType
import com.tritiumgaming.data.ghost.source.GhostDataSource
import com.tritiumgaming.shared.operation.domain.ghost.model.Ghost
import com.tritiumgaming.shared.operation.domain.ghost.model.GhostType
import com.tritiumgaming.shared.operation.domain.ghost.repository.GhostRepository

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