package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.repository.DifficultyRepository

class GetDifficultyModifierUseCase(
        private val difficultyRepository: DifficultyRepository
    ) {
        operator fun invoke(index: Int): Result<Float> {
            val result = difficultyRepository.getDifficulties()

            result.exceptionOrNull()?.let {
                return Result.failure(Exception("Could not get difficulty modifier"))
            }

            try {
                val modifier = result.getOrNull()?.let {
                    it[index].modifier
                } ?: return Result.failure(Exception("Could not get difficulty modifier"))

                return Result.success(modifier)
            } catch (e: Exception) {
                return Result.failure(Exception("Could not acquire difficulty modifier", e))
            }
        }
    }
