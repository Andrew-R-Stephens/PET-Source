package com.tritiumgaming.data.map.combined.usecase

import com.tritiumgaming.data.map.simple.model.SimpleWorldMap

class FetchSimpleMapsUseCase(
    private val mapRepository: MapRepository
) {
    operator fun invoke(): Result<List<SimpleWorldMap>> {
        val result = simpleMapRepository.getMaps()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get simple maps", it)) }

        return result
    }
}