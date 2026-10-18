package com.tritiumgaming.data.map.combined.usecase

import com.tritiumgaming.data.map.complex.model.ComplexWorldMaps

class FetchComplexMapsUseCase(
    private val mapRepository: MapRepository
) {
    suspend operator fun invoke(): Result<ComplexWorldMaps> {
        val result = complexMapRepository.fetchMaps()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get complex maps", it)) }

        return result
    }
}
    