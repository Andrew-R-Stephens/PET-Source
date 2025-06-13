package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository

class IncrementMapIndexUseCase(
    private val simpleMapsRepository: SimpleMapRepository
) {

    operator fun invoke(currentIndex: Int): Int {
        val result = simpleMapsRepository.getMaps()

        result.exceptionOrNull()?.printStackTrace()

        var newIndex = currentIndex
        result.getOrNull()?.let { list ->
            var newIndex = currentIndex + 1
            if (newIndex >= list.size) { newIndex = 0 }
        }
        return newIndex

    }
}