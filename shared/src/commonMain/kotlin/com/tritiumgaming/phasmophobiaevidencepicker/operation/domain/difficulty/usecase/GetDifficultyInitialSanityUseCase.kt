package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.repository.DifficultyRepository

class GetDifficultyInitialSanityUseCase(
    private val difficultyRepository: DifficultyRepository
) {
    operator fun invoke(index: Int): Result<Float> {
        val result = difficultyRepository.getDifficulties()

        result.exceptionOrNull()?.printStackTrace()

        return try {
            val sanity = result.getOrNull()?.let {
                it[index].initialSanity
            } ?: 0f

            Result.success(sanity)
        } catch (e: Exception) {
            Result.failure(Exception("Could not acquire difficulty name", e))
        }
    }
}
