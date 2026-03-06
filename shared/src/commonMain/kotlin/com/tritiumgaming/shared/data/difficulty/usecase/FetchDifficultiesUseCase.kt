package com.tritiumgaming.shared.data.difficulty.usecase

import com.tritiumgaming.shared.data.difficulty.model.DifficultyModel
import com.tritiumgaming.shared.data.difficulty.repository.DifficultyRepository

class FetchDifficultiesUseCase(
    private val difficultyRepository: DifficultyRepository
) {
    operator fun invoke(): Result<List<DifficultyModel>> {
        val result = difficultyRepository.getDifficulties()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get difficulties", it))
        }

        return result
    }
}