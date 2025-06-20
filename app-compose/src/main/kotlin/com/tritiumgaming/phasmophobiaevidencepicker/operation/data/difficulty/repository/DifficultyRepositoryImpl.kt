package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.dto.toDomain
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.model.DifficultyModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.repository.DifficultyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.source.DifficultyDataSource

class DifficultyRepositoryImpl(
    val localSource: DifficultyDataSource
): DifficultyRepository {

    override fun getDifficulties(): Result<List<DifficultyModel>> {
        return localSource.fetchDifficulties().map { dto -> dto.toDomain() }
    }

}