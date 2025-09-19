package com.tritiumgaming.shared.operation.domain.map.simple.usecase

import com.tritiumgaming.shared.operation.domain.map.simple.mappers.SimpleMapResources.MapThumbnail
import com.tritiumgaming.shared.operation.domain.map.simple.repository.SimpleMapRepository

class FetchMapThumbnailsUseCase(
    private val simpleMapRepository: SimpleMapRepository
) {
    operator fun invoke(): Result<List<MapThumbnail>> {

        val result = simpleMapRepository.getMaps()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get simple map thumbnails", it)) }

        return result.map { it.map { map -> map.thumbnailImage } }
    }
}