package com.tritiumgaming.data.map.simple.usecase

import com.tritiumgaming.data.map.simple.model.SimpleWorldMap
import com.tritiumgaming.data.map.simple.repository.SimpleMapRepository

class FetchSimpleMapsUseCase(
    private val simpleMapRepository: SimpleMapRepository
) {
    operator fun invoke(): Result<List<SimpleWorldMap>> {
        val result = simpleMapRepository.getMaps()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get simple maps", it)) }

        return result
    }
}