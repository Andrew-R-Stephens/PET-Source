package com.tritiumgaming.shared.data.difficulty.repository

import com.tritiumgaming.shared.data.difficulty.model.DifficultyModel

interface DifficultyRepository {

    fun getDifficulties(): Result<List<DifficultyModel>>

}