package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.repository.DifficultyRepository

class GetDifficultyModifierUseCase(
        private val difficultyRepository: DifficultyRepository
    ) {
        operator fun invoke(index: Int): Result<Float> {
            val result = difficultyRepository.getDifficulties()

            result.exceptionOrNull()?.printStackTrace()

            return try {
                val modifier = result.getOrNull()?.let {
                    it[index].modifier
                } ?: 0f

                Result.success(modifier)
            } catch (e: Exception) {
                Result.failure(Exception("Could not acquire difficulty name", e))
            }
        }
    }
