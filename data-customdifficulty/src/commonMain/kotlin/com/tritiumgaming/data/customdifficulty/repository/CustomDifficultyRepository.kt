package com.tritiumgaming.data.customdifficulty.repository

import com.tritiumgaming.data.customdifficulty.model.CustomDifficultyModel
import kotlinx.coroutines.flow.Flow

interface CustomDifficultyRepository {
    val allCustomDifficulties: Flow<Result<List<CustomDifficultyModel>>>
    suspend fun update(difficulty: CustomDifficultyModel): Result<Unit>
}
