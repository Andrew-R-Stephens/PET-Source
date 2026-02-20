package com.tritiumgaming.shared.data.map.simple.usecase

class IncrementSimpleMapIndexUseCase(
    private val simpleMapRepository: com.tritiumgaming.shared.data.map.simple.repository.SimpleMapRepository
) {

    operator fun invoke(currentIndex: Int): Result<Int> {
        val result = simpleMapRepository.getMaps()

        result.exceptionOrNull()?.printStackTrace()
        val maps = result.getOrNull()

        var newIndex = currentIndex
        maps?.let { list ->
            newIndex ++
            if (newIndex >= list.size) { newIndex = 0 }
        }
        return Result.success(newIndex)

    }
}