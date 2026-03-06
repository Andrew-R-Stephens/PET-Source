package com.tritiumgaming.shared.data.difficulty.usecase

import com.tritiumgaming.shared.data.difficulty.repository.DifficultyRepository
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.StartingSanity

class GetDifficultyInitialSanityUseCase(
    private val difficultyRepository: DifficultyRepository
) {
    operator fun invoke(index: Int): Result<StartingSanity> {
        val result = difficultyRepository.getDifficulties()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get difficulty initial sanity", it))
        }

        try {
            val sanity = result.getOrNull()?.let {
                it[index].settingsModel.startingSanity
            } ?: return Result.failure(Exception("Could not get difficulty initial sanity"))

            return Result.success(sanity)
        } catch (e: Exception) {
            return Result.failure(Exception("Could not acquire difficulty initial sanity", e))
        }
    }
}
