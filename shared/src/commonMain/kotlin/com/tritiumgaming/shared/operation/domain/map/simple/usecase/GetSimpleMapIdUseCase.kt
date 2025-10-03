package com.tritiumgaming.shared.operation.domain.map.simple.usecase

import com.tritiumgaming.shared.operation.domain.map.simple.repository.SimpleMapRepository

class GetSimpleMapIdUseCase(
    private val simpleMapRepository: SimpleMapRepository
) {
    operator fun invoke(index: Int): Result<String> {

        val result = simpleMapRepository.getMaps()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get maps", it)) }

        val mapResultList = result.getOrNull()

        val id = mapResultList?.getOrNull(index)?.mapId

        if(id == null) return Result.failure(Exception("Could not get map id"))

        return Result.success(id)
    }

}
