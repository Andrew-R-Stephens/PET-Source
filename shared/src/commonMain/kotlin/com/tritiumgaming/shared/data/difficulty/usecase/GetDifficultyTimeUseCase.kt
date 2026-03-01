package com.tritiumgaming.shared.data.difficulty.usecase

import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources

class GetDifficultyTimeUseCase(
    private val difficultyRepository: com.tritiumgaming.shared.data.difficulty.repository.DifficultyRepository
) {
    operator fun invoke(index: Int): Result<DifficultySettingResources.SetupTime> {
        val result = difficultyRepository.getDifficulties()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get difficulty time"))
        }

        try {
            val time = result.getOrNull()?.let {
                it[index].difficultySettingsModel.setupTime
            } ?: return Result.failure(Exception("Could not get difficulty time"))

            return Result.success(time)
        } catch (e: Exception) {
            return Result.failure(Exception("Could not acquire difficulty name", e))
        }
    }
}
