package com.tritiumgaming.shared.operation.domain.difficulty.usecase

import com.tritiumgaming.shared.operation.domain.difficulty.repository.DifficultyRepository

class GetDifficultyTimeUseCase(
    private val difficultyRepository: DifficultyRepository
) {
    operator fun invoke(index: Int): Result<Long> {
        val result = difficultyRepository.getDifficulties()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get difficulty time"))
        }

        try {
            val time = result.getOrNull()?.let {
                it[index].time
            } ?: return Result.failure(Exception("Could not get difficulty time"))

            return Result.success(time)
        } catch (e: Exception) {
            return Result.failure(Exception("Could not acquire difficulty name", e))
        }
    }
}
