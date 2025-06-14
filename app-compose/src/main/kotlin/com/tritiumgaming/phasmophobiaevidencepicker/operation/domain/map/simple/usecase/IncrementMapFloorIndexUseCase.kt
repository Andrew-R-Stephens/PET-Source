package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository

class IncrementMapFloorIndexUseCase(
    private val simpleMapsRepository: SimpleMapRepository
) {
    operator fun invoke(currentIndex: Int): Int {
        val result = simpleMapsRepository.getMaps()
        result.exceptionOrNull()?.printStackTrace()

        return result.getOrNull()?.let { map ->
            val floorCount = map[currentIndex].floorCount

            var newIndex: Int = currentIndex + 1
            if (newIndex >= floorCount) { newIndex = 0 }
            newIndex
        } ?: currentIndex

    }

}
