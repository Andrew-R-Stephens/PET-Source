package com.tritiumgaming.shared.data.difficulty.usecase

class GetDifficultyResponseTypeUseCase(
    private val difficultyRepository: com.tritiumgaming.shared.data.difficulty.repository.DifficultyRepository
) {
    operator fun invoke(index: Int): Result<com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType> {
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
