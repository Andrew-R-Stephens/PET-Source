package com.tritiumgaming.data.map.combined.usecase

import com.tritiumgaming.data.map.combined.repository.MapRepository

class IncrementSimpleMapIndexUseCase(
    private val mapRepository: MapRepository
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