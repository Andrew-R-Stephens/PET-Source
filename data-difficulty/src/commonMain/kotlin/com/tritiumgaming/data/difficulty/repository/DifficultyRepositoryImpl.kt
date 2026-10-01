package com.tritiumgaming.data.difficulty.repository

import com.tritiumgaming.data.difficulty.dto.toDomain
import com.tritiumgaming.data.difficulty.model.DifficultyModel
import com.tritiumgaming.data.difficulty.source.DifficultyDataSource

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