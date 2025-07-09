package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.repository.DifficultyRepository

class GetDifficultyNameUseCase(
        private val difficultyRepository: DifficultyRepository
    ) {
        operator fun invoke(index: Int): Result<DifficultyTitle> {
            val result = difficultyRepository.getDifficulties()

            result.exceptionOrNull()?.printStackTrace()

            return try {
                val title = result.getOrNull()?.let {
                    it[index].name
                } ?: DifficultyTitle.AMATEUR

                Result.success(title)
            } catch (e: Exception) {
                Result.failure(Exception("Could not acquire difficulty name", e))
            }
        }
    }
