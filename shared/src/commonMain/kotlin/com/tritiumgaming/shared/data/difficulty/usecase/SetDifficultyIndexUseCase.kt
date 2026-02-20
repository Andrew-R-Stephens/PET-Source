package com.tritiumgaming.shared.data.difficulty.usecase

import com.tritiumgaming.shared.data.difficulty.repository.DifficultyRepository

class SetDifficultyIndexUseCase(
    private val difficultyRepository: DifficultyRepository
) {
    operator fun invoke(
        selectedIndex: Int
    ): Result<Int> {

        val result = difficultyRepository.getDifficulties()

        val difficulties = result.getOrThrow()

        return if(selectedIndex >= 0 && selectedIndex < difficulties.size)
            Result.success(selectedIndex)
        else Result.failure(Exception("Invalid Index"))
    }
}
