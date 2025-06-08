package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.model.DifficultyModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.source.DifficultyDataSource

interface DifficultyRepository {
    val localSource: DifficultyDataSource

    fun getDifficulties(): Result<List<DifficultyModel>>

}