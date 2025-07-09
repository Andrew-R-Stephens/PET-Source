package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.repository.DifficultyRepository

class GetDifficultyTimeUseCase(
    private val difficultyRepository: DifficultyRepository
) {
    operator fun invoke(index: Int): Result<Long> {
        val result = difficultyRepository.getDifficulties()

        result.exceptionOrNull()?.printStackTrace()

        return try {
            val time = result.getOrNull()?.let {
                it[index].time
            } ?: 0L

            Result.success(time)
        } catch (e: Exception) {
            Result.failure(Exception("Could not acquire difficulty name", e))
        }
    }
}
