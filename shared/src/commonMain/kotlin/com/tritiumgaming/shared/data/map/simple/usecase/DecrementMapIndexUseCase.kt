package com.tritiumgaming.shared.data.map.simple.usecase

class DecrementMapIndexUseCase(
    private val simpleMapRepository: com.tritiumgaming.shared.data.map.simple.repository.SimpleMapRepository
) {

    operator fun invoke(currentIndex: Int): Result<Int> {
        val result = simpleMapRepository.getMaps()

        result.exceptionOrNull()?.printStackTrace()
        val maps = result.getOrNull()

        var newIndex = currentIndex
        maps?.let { list ->
            newIndex = currentIndex - 1
            if (newIndex < 0) { newIndex = list.size - 1 }
        }
        return Result.success(newIndex)

    }
}