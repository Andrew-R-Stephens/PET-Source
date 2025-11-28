package com.tritiumgaming.shared.data.map.simple.usecase

class GetSimpleMapNameUseCase(
    private val simpleMapRepository: com.tritiumgaming.shared.data.map.simple.repository.SimpleMapRepository
) {
    operator fun invoke(index: Int): Result<com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources.MapTitle> {

        val result = simpleMapRepository.getMaps()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get maps", it)) }

        val mapResultList = result.getOrNull()

        val name = mapResultList?.getOrNull(index)?.mapName

        if(name == null) return Result.failure(Exception("Could not get map name"))

        return Result.success(name)
    }

    operator fun invoke(id: String): Result<com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources.MapTitle> {

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
