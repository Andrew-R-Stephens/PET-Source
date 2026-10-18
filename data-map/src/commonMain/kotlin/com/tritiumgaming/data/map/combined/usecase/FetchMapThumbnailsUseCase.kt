package com.tritiumgaming.data.map.combined.usecase

import com.tritiumgaming.data.map.simple.mappers.SimpleMapResources.MapThumbnail

class FetchMapThumbnailsUseCase(
    private val mapRepository: MapRepository
) {
    operator fun invoke(): Result<List<MapThumbnail>> {

        val result = simpleMapRepository.getMaps()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get simple map thumbnails", it)) }

        return result.map { it.map { map -> map.thumbnailImage } }
    }
}