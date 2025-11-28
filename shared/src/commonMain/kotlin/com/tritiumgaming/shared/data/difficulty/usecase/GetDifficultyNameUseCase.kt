package com.tritiumgaming.shared.data.difficulty.usecase

class GetDifficultyNameUseCase(
        private val difficultyRepository: com.tritiumgaming.shared.data.difficulty.repository.DifficultyRepository
    ) {
        operator fun invoke(index: Int): Result<com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyTitle> {
            val result = difficultyRepository.getDifficulties()

            result.exceptionOrNull()?.let {
                return Result.failure(Exception("Could not get difficulty name"))
            }

            try {
                val title = result.getOrNull()?.let {
                    it[index].name
                } ?: return Result.failure(Exception("Could not get difficulty name"))

                return Result.success(title)
            } catch (e: Exception) {
                return Result.failure(Exception("Could not acquire difficulty name", e))
            }
        }
    }
