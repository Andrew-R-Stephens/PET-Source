package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.repository.DifficultyRepository

class DecrementDifficultyIndexUseCase(
    private val difficultyRepository: DifficultyRepository
) {
    operator fun invoke(
        currentIndex: Int
    ): Result<Int> {

        val result = difficultyRepository.getDifficulties()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get difficulty", it))
        }

        val difficulties = result.getOrNull()
        var newIndex = currentIndex
        difficulties?.let { list ->
            newIndex --
            if (newIndex < 0) { newIndex = list.size - 1 }
        }

        return Result.success(newIndex)

    }
}
