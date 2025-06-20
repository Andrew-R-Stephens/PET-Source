package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.SimpleWorldMap
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository

class FetchSimpleMapsUseCase(
    private val simpleMapRepository: SimpleMapRepository
) {
    operator fun invoke(): List<SimpleWorldMap> {
        val result = simpleMapRepository.getMaps()

        result.exceptionOrNull()?.printStackTrace()

        return result.getOrNull() ?: emptyList()
    }
}