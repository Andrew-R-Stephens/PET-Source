package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository

class DecrementMapFloorIndexUseCase(
    private val simpleMapsRepository: SimpleMapRepository
) {
    operator fun invoke(currentIndex: Int): Int {
        val result = simpleMapsRepository.getMaps()
        result.exceptionOrNull()?.printStackTrace()

        return result.getOrNull()?.let { map ->
            val floorCount = map[currentIndex].floorCount

            var newIndex: Int = currentIndex - 1
            if (newIndex < 0) { newIndex = floorCount - 1 }
            newIndex
        } ?: currentIndex

    }

}