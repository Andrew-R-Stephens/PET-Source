package com.tritiumgaming.shared.operation.domain.difficulty.usecase

import com.tritiumgaming.shared.operation.domain.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.shared.operation.domain.difficulty.repository.DifficultyRepository

class GetDifficultyResponseTypeUseCase(
    private val difficultyRepository: DifficultyRepository
) {
    operator fun invoke(index: Int): Result<DifficultyResponseType> {
        val result = difficultyRepository.getDifficulties()

        result.exceptionOrNull()?.printStackTrace()

        try {
            val sanity = result.getOrNull()?.let {
                it[index].responseType
            } ?: return Result.failure(Exception("Could not get difficulty response type"))

            return Result.success(sanity)
        } catch (e: Exception) {
            return Result.failure(Exception("Could not acquire difficulty response type", e))
        }
    }
}
