package com.tritiumgaming.shared.data.map.complex.usecase

class FetchComplexMapsUseCase(
    private val complexMapRepository: com.tritiumgaming.shared.data.map.complex.repository.ComplexMapRepository
) {
    suspend operator fun invoke(): Result<com.tritiumgaming.shared.data.map.complex.model.ComplexWorldMaps> {
        val result = complexMapRepository.fetchMaps()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get complex maps", it)) }

        return result
    }
}
    