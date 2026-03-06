package com.tritiumgaming.shared.data.map.simple.usecase

import com.tritiumgaming.shared.data.map.simple.repository.SimpleMapRepository

class IncrementSimpleMapIndexUseCase(
    private val simpleMapRepository: SimpleMapRepository
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