package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.model.DifficultyModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.repository.DifficultyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.source.DifficultyDataSource

class DifficultyRepositoryImpl(
    override val localSource: DifficultyDataSource
): DifficultyRepository {

    override fun getDifficulties(): List<DifficultyModel> {
        return localSource.fetchDifficulties()
    }

}