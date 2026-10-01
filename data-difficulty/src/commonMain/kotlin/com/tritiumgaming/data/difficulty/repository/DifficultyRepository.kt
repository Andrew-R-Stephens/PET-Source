package com.tritiumgaming.data.difficulty.repository

import com.tritiumgaming.data.difficulty.model.DifficultyModel

interface DifficultyRepository {

    fun getDifficulties(): Result<List<DifficultyModel>>

}