package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.repository.DifficultyRepository

class GetDifficultyResponseTypeUseCase(
    private val difficultyRepository: DifficultyRepository
) {
    operator fun invoke(index: Int): Result<DifficultyResponseType> {
        val result = difficultyRepository.getDifficulties()

        result.exceptionOrNull()?.printStackTrace()

        return try {
            val sanity = result.getOrNull()?.let {
                it[index].responseType
            } ?: DifficultyResponseType.KNOWN

            Result.success(sanity)
        } catch (e: Exception) {
            Result.failure(Exception("Could not acquire difficulty name", e))
        }
    }
}
