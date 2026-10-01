package com.tritiumgaming.data.customdifficulty.usecase

import com.tritiumgaming.data.customdifficulty.model.CustomDifficultyModel
import com.tritiumgaming.data.customdifficulty.repository.CustomDifficultyRepository

class UpdateCustomDifficultyUseCase(
    private val repository: CustomDifficultyRepository
) {
    suspend operator fun invoke(difficulty: CustomDifficultyModel): Result<Unit> = repository.update(difficulty)
}
