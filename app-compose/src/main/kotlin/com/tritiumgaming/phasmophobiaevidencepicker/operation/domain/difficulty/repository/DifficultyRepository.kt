package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.model.DifficultyModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.source.DifficultyDataSource

interface DifficultyRepository {

    fun getDifficulties(): Result<List<DifficultyModel>>

}