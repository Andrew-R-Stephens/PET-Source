package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.repository.ComplexMapRepository

class FetchComplexMapsUseCase(
    private val complexMapRepository: ComplexMapRepository
) {
    suspend operator fun invoke(): Result<ComplexWorldMaps> {
        return complexMapRepository.fetchMaps()
    }
}
    