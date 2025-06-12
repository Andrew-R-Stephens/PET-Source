package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.model.DifficultyModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.repository.DifficultyRepository

class FetchDifficultiesUseCase(
    private val difficultyRepository: DifficultyRepository
) {
    operator fun invoke(): List<DifficultyModel> {
        val result = difficultyRepository.getDifficulties()

        result.exceptionOrNull()?.printStackTrace()

        return result.getOrDefault(emptyList())
    }
}
    