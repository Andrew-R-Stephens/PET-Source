package com.tritiumgaming.shared.data.difficulty.usecase

import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources

class GetDifficultyModifierUseCase(
        private val difficultyRepository: com.tritiumgaming.shared.data.difficulty.repository.DifficultyRepository
    ) {
        operator fun invoke(index: Int): Result<DifficultySettingResources.SanityDrainSpeed> {
            val result = difficultyRepository.getDifficulties()

            result.exceptionOrNull()?.let {
                return Result.failure(Exception("Could not get difficulty modifier"))
            }

            try {
                val modifier = result.getOrNull()?.let {
                    it[index].difficultySettingsModel.sanityDrainSpeed
                } ?: return Result.failure(Exception("Could not get difficulty modifier"))

                return Result.success(modifier)
            } catch (e: Exception) {
                return Result.failure(Exception("Could not acquire difficulty modifier", e))
            }
        }
    }
