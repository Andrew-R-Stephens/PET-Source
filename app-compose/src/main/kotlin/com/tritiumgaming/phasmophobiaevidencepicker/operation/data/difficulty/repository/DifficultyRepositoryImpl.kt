package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.dto.toDomain
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.source.DifficultyDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.model.DifficultyModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.repository.DifficultyRepository

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