package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository

class IncrementMapIndexUseCase(
    private val simpleMapsRepository: SimpleMapRepository
) {

    operator fun invoke(currentIndex: Int): Int {
        val result = simpleMapsRepository.getMaps()

        result.exceptionOrNull()?.printStackTrace()
        val maps = result.getOrNull()

        var newIndex = currentIndex
        maps?.let { list ->
            newIndex ++
            if (newIndex >= list.size) { newIndex = 0 }
        }
        return newIndex

    }
}