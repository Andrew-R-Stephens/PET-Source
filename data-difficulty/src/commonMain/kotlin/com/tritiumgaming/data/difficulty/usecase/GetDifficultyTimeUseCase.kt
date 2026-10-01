package com.tritiumgaming.data.difficulty.usecase

import com.tritiumgaming.data.difficulty.repository.DifficultyRepository
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.SetupTime

class GetDifficultyTimeUseCase(
    private val difficultyRepository: DifficultyRepository
) {
    operator fun invoke(index: Int): Result<SetupTime> {
        val result = difficultyRepository.getDifficulties()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get difficulty time"))
        }

        try {
            val time = result.getOrNull()?.let {
                it[index].settingsModel.setupTime
            } ?: return Result.failure(Exception("Could not get difficulty time"))

            return Result.success(time)
        } catch (e: Exception) {
            return Result.failure(Exception("Could not acquire difficulty name", e))
        }
    }
}
