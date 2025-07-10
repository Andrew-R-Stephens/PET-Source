package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.mappers.MapModifierResources.MapSize
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository

class GetSimpleMapSizeUseCase(
    private val simpleMapsRepository: SimpleMapRepository
) {
    operator fun invoke(index: Int): Result<MapSize> {
        val result = simpleMapsRepository.getMaps()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get map size", it))
        }

        val mapSize = result.getOrNull()?.getOrNull(index)?.mapSize
            ?: return Result.failure(Exception("Could not get map size"))

        return Result.success(mapSize)
    }
}
