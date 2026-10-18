package com.tritiumgaming.data.map.combined.usecase

class GetSimpleMapIdUseCase(
    private val mapRepository: MapRepository
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
