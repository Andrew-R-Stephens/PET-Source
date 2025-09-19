package com.tritiumgaming.shared.operation.domain.map.complex.usecase

import com.tritiumgaming.shared.operation.domain.map.complex.model.ComplexWorldMaps
import com.tritiumgaming.shared.operation.domain.map.complex.repository.ComplexMapRepository

class FetchComplexMapsUseCase(
    private val complexMapRepository: ComplexMapRepository
) {
    suspend operator fun invoke(): Result<ComplexWorldMaps> {
        val result = complexMapRepository.fetchMaps()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get complex maps", it)) }

        return result
    }
}
    