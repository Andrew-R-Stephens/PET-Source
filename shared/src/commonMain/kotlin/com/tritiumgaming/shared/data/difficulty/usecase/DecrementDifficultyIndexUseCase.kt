package com.tritiumgaming.shared.data.difficulty.usecase

class DecrementDifficultyIndexUseCase(
    private val difficultyRepository: com.tritiumgaming.shared.data.difficulty.repository.DifficultyRepository
) {
    operator fun invoke(
        currentIndex: Int
    ): Result<Int> {

        val result = difficultyRepository.getDifficulties()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get difficulty", it))
        }

        val difficulties = result.getOrNull()
        var newIndex = currentIndex
        difficulties?.let { list ->
            newIndex --
            if (newIndex < 0) { newIndex = list.size - 1 }
        }

        return Result.success(newIndex)

    }
}
