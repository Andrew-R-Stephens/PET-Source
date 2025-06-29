package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.mappers.SimpleMapResources.MapTitle
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository

class GetSimpleMapNameUseCase(
    private val simpleMapsRepository: SimpleMapRepository
) {
    operator fun invoke(index: Int): MapTitle? {

        val result = simpleMapsRepository.getMaps()

        result.exceptionOrNull()

        val mapResultList = result.getOrNull()

        return mapResultList?.getOrNull(index)?.mapName
    }
}
