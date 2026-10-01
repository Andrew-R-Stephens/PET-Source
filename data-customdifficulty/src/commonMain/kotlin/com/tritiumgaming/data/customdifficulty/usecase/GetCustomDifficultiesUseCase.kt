package com.tritiumgaming.data.customdifficulty.usecase

import com.tritiumgaming.data.customdifficulty.model.CustomDifficultyModel
import com.tritiumgaming.data.customdifficulty.repository.CustomDifficultyRepository
import kotlinx.coroutines.flow.Flow

class GetCustomDifficultiesUseCase(
    private val repository: CustomDifficultyRepository
) {
    operator fun invoke(): Flow<Result<List<CustomDifficultyModel>>> = repository.allCustomDifficulties
}
