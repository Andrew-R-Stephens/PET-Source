package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.model.DifficultyModel

interface DifficultyRepository {

    fun getDifficulties(): Result<List<DifficultyModel>>

}