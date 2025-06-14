package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.mapviewer.LocalWorldMap
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository

class FetchSimpleMapsUseCase(
    private val simpleMapRepository: SimpleMapRepository
) {
    operator fun invoke(): List<LocalWorldMap> {
        val result = simpleMapRepository.getMaps()

        result.exceptionOrNull()?.printStackTrace()

        return result.getOrNull() ?: emptyList()
    }
}