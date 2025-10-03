package com.tritiumgaming.data.difficulty.repository

import com.tritiumgaming.data.difficulty.dto.toDomain
import com.tritiumgaming.data.difficulty.source.DifficultyDataSource
import com.tritiumgaming.shared.operation.domain.difficulty.model.DifficultyModel
import com.tritiumgaming.shared.operation.domain.difficulty.repository.DifficultyRepository

class DifficultyRepositoryImpl(
    val localSource: DifficultyDataSource
): DifficultyRepository {

    var difficulties: List<DifficultyModel> = emptyList()

    override fun getDifficulties(): Result<List<DifficultyModel>> {

        if(difficulties.isEmpty()) {
            difficulties = localSource.fetchDifficulties()
                .map { dto -> dto.toDomain() }
                .getOrDefault(emptyList())
        }

        return Result.success(difficulties)
    }

}