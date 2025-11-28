package com.tritiumgaming.shared.data.difficulty.usecase

class FetchDifficultiesUseCase(
    private val difficultyRepository: com.tritiumgaming.shared.data.difficulty.repository.DifficultyRepository
) {
    operator fun invoke(): Result<List<com.tritiumgaming.shared.data.difficulty.model.DifficultyModel>> {
        val result = difficultyRepository.getDifficulties()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get difficulties", it))
        }

        return result
    }
}