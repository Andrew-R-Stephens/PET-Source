package com.tritiumgaming.data.map.combined.usecase

import com.tritiumgaming.data.map.simple.mappers.SimpleMapResources.MapTitle

class GetSimpleMapNameUseCase(
    private val mapRepository: MapRepository
) {
    operator fun invoke(index: Int): Result<MapTitle> {

        val result = simpleMapRepository.getMaps()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get maps", it)) }

        val mapResultList = result.getOrNull()

        val name = mapResultList?.getOrNull(index)?.mapName
            ?: return Result.failure(Exception("Could not get map name"))

        return Result.success(name)
    }

    operator fun invoke(id: String): Result<MapTitle> {

        val result = simpleMapRepository.getMaps()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get maps", it)) }

        val mapResultList = result.getOrNull()
        val name = mapResultList?.let {
            it.firstOrNull()?.mapName
        }

        if(name == null) return Result.failure(Exception("Could not get map name"))

        return Result.success(name)
    }

}
