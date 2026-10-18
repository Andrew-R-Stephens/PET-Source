package com.tritiumgaming.data.map.combined.usecase

import com.tritiumgaming.data.map.modifier.mappers.MapModifierResources.MapSize

class GetSimpleMapSizeUseCase(
    private val mapRepository: MapRepository
) {
    operator fun invoke(index: Int): Result<MapSize> {
        val result = simpleMapRepository.getMaps()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get maps", it))
        }

        val mapSize = result.getOrNull()?.getOrNull(index)?.mapSize
            ?: return Result.failure(Exception("Could not get map size"))

        return Result.success(mapSize)
    }

    operator fun invoke(id: String): Result<MapSize> {
        val result = simpleMapRepository.getMaps()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get maps", it))
        }

        val mapSize = result.getOrNull()?.first{ map -> map.mapId == id }?.mapSize
            ?: return Result.failure(Exception("Could not get map size"))

        return Result.success(mapSize)
    }
}
