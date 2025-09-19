package com.tritiumgaming.shared.operation.domain.difficulty.repository

import com.tritiumgaming.shared.operation.domain.difficulty.model.DifficultyModel

interface DifficultyRepository {

    fun getDifficulties(): Result<List<DifficultyModel>>

}