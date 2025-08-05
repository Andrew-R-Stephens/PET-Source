package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.mapper.DifficultyResources
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.repository.DifficultyRepository

class GetDifficultyTypeUseCase(
    private val difficultyRepository: DifficultyRepository
) {
    operator fun invoke(index: Int): Result<DifficultyResources.DifficultyType> {
        val result = difficultyRepository.getDifficulties()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get difficulty type"))
        }

        try {
            val type = result.getOrNull()?.let {
                it[index].type
            } ?: return Result.failure(Exception("Could not get difficulty type"))

            return Result.success(type)
        } catch (e: Exception) {
            return Result.failure(Exception("Could not acquire difficulty type", e))
        }
    }

}
