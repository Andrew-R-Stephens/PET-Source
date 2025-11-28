package com.tritiumgaming.shared.data.map.simple.usecase

class GetSimpleMapSizeUseCase(
    private val simpleMapRepository: com.tritiumgaming.shared.data.map.simple.repository.SimpleMapRepository
) {
    operator fun invoke(index: Int): Result<com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSize> {
        val result = simpleMapRepository.getMaps()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get maps", it))
        }

        val mapSize = result.getOrNull()?.getOrNull(index)?.mapSize
            ?: return Result.failure(Exception("Could not get map size"))

        return Result.success(mapSize)
    }

    operator fun invoke(id: String): Result<com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSize> {
        val result = simpleMapRepository.getMaps()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get maps", it))
        }

        val mapSize = result.getOrNull()?.first{ map -> map.mapId == id }?.mapSize
            ?: return Result.failure(Exception("Could not get map size"))

        return Result.success(mapSize)
    }
}
