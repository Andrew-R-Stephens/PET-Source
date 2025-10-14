package com.tritiumgaming.shared.operation.domain.map.simple.usecase

import com.tritiumgaming.shared.operation.domain.map.simple.repository.SimpleMapRepository

class DecrementMapIndexUseCase(
    private val simpleMapRepository: SimpleMapRepository
) {

    operator fun invoke(currentIndex: Int): Result<Int> {
        val result = simpleMapRepository.getMaps()

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