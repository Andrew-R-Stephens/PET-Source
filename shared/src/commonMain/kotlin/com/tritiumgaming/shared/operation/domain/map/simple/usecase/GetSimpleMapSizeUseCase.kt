package com.tritiumgaming.shared.operation.domain.map.simple.usecase

import com.tritiumgaming.shared.operation.domain.map.modifier.mappers.MapModifierResources
import com.tritiumgaming.shared.operation.domain.map.simple.repository.SimpleMapRepository

class GetSimpleMapSizeUseCase(
    private val simpleMapsRepository: SimpleMapRepository
) {
    operator fun invoke(index: Int): Result<MapModifierResources.MapSize> {
        val result = simpleMapsRepository.getMaps()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get maps", it))
        }

        val mapSize = result.getOrNull()?.getOrNull(index)?.mapSize
            ?: return Result.failure(Exception("Could not get map size"))

        return Result.success(mapSize)
    }

    operator fun invoke(id: String): Result<MapModifierResources.MapSize> {
        val result = simpleMapsRepository.getMaps()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get maps", it))
        }

        val mapSize = result.getOrNull()?.first{ map -> map.mapId == id }?.mapSize
            ?: return Result.failure(Exception("Could not get map size"))

        return Result.success(mapSize)
    }
}
