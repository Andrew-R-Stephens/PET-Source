package com.tritiumstudios.data.operation.usecase

import com.tritiumstudios.data.operation.OperationRepository
import com.tritiumstudios.data.operation.model.DifficultyData

class UpdateOperationDifficultyUseCase(
    private val repository: OperationRepository
) {
    operator fun invoke(difficulty: DifficultyData) =
        repository.updateDifficulty(difficulty)
}
