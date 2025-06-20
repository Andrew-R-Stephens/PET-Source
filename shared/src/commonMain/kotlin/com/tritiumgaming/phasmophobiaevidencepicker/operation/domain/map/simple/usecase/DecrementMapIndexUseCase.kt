package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository

class DecrementMapIndexUseCase(
        private val simpleMapsRepository: SimpleMapRepository
    ) {

        operator fun invoke(currentIndex: Int): Int {
            val result = simpleMapsRepository.getMaps()

            result.exceptionOrNull()?.printStackTrace()

            var newIndex = currentIndex
            result.getOrNull()?.let { list ->
                var newIndex = currentIndex - 1
                if (newIndex < 0) { newIndex = list.size - 1 }
            }
            return newIndex

        }
    }