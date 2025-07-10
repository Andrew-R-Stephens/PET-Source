package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.mappers.SimpleMapResources.MapTitle
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository

class GetSimpleMapNameUseCase(
    private val simpleMapsRepository: SimpleMapRepository
) {
    operator fun invoke(index: Int): Result<MapTitle> {

        val result = simpleMapsRepository.getMaps()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get map names", it)) }

        val mapResultList = result.getOrNull()

        val name = mapResultList?.getOrNull(index)?.mapName

        if(name == null) return Result.failure(Exception("Could not get map name"))

        return Result.success(name)
    }
}
