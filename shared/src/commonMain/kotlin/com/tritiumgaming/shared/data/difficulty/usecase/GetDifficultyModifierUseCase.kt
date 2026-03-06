package com.tritiumgaming.shared.data.difficulty.usecase

import com.tritiumgaming.shared.data.difficulty.repository.DifficultyRepository
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.SanityDrainSpeed

class GetDifficultyModifierUseCase(
        private val difficultyRepository: DifficultyRepository
    ) {
        operator fun invoke(index: Int): Result<SanityDrainSpeed> {
            val result = difficultyRepository.getDifficulties()

            result.exceptionOrNull()?.let {
                return Result.failure(Exception("Could not get difficulty modifier"))
            }

            try {
                val modifier = result.getOrNull()?.let {
                    it[index].settingsModel.sanityDrainSpeed
                } ?: return Result.failure(Exception("Could not get difficulty modifier"))

                return Result.success(modifier)
            } catch (e: Exception) {
                return Result.failure(Exception("Could not acquire difficulty modifier", e))
            }
        }
    }
