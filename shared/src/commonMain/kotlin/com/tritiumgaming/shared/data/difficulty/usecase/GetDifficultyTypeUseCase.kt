package com.tritiumgaming.shared.data.difficulty.usecase

class GetDifficultyTypeUseCase(
    private val difficultyRepository: com.tritiumgaming.shared.data.difficulty.repository.DifficultyRepository
) {
    operator fun invoke(index: Int): Result<com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyType> {
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
