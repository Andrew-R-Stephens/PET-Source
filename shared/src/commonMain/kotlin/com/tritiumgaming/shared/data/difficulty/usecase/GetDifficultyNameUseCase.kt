package com.tritiumgaming.shared.data.difficulty.usecase

import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.shared.data.difficulty.repository.DifficultyRepository

class GetDifficultyNameUseCase(
        private val difficultyRepository: DifficultyRepository
    ) {
        operator fun invoke(index: Int): Result<DifficultyTitle> {
            val result = difficultyRepository.getDifficulties()

            result.exceptionOrNull()?.let {
                return Result.failure(Exception("Could not get difficulty name"))
            }

            try {
                val title = result.getOrNull()?.let {
                    it[index].difficultyTitle
                } ?: return Result.failure(Exception("Could not get difficulty name"))

                return Result.success(title)
            } catch (e: Exception) {
                return Result.failure(Exception("Could not acquire difficulty name", e))
            }
        }
    }
