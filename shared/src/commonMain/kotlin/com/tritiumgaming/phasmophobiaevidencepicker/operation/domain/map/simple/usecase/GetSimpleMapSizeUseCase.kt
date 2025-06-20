package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.mappers.MapModifierResources.MapSize
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository

class GetSimpleMapSizeUseCase(
    private val simpleMapsRepository: SimpleMapRepository
) {
    operator fun invoke(index: Int): MapSize? {
        val result = simpleMapsRepository.getMaps()

        result.exceptionOrNull()

        return result.getOrNull()?.getOrNull(index)?.mapSize
    }
}
