package com.tritiumgaming.data.map.combined.usecase

import com.tritiumgaming.data.map.combined.repository.MapRepository

class DecrementMapIndexUseCase(
    private val mapRepository: MapRepository
) {

    operator fun invoke(currentIndex: Int): Result<Int> {
        val result = mapRepository.mapFlow

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