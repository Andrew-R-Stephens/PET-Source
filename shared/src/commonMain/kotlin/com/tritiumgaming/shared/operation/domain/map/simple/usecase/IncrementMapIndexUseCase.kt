package com.tritiumgaming.shared.operation.domain.map.simple.usecase

import com.tritiumgaming.shared.operation.domain.map.simple.repository.SimpleMapRepository

class IncrementMapIndexUseCase(
    private val simpleMapRepository: SimpleMapRepository
) {

    operator fun invoke(currentIndex: Int): Int {
        val result = simpleMapRepository.getMaps()

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