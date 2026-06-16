package com.tritiumgaming.shared.data.operation.usecase

import com.tritiumgaming.shared.data.operation.OperationRepository
import com.tritiumgaming.shared.data.operation.model.DifficultyData

class UpdateOperationDifficultyUseCase(
    private val repository: OperationRepository
) {
    operator fun invoke(difficulty: DifficultyData) =
        repository.updateDifficulty(difficulty)
}
