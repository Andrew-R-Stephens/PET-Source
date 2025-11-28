package com.tritiumgaming.shared.data.map.simple.usecase

class FetchMapThumbnailsUseCase(
    private val simpleMapRepository: com.tritiumgaming.shared.data.map.simple.repository.SimpleMapRepository
) {
    operator fun invoke(): Result<List<com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources.MapThumbnail>> {

        val result = simpleMapRepository.getMaps()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get simple map thumbnails", it)) }

        return result.map { it.map { map -> map.thumbnailImage } }
    }
}