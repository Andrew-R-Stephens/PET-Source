package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.mappers.SimpleMapResources.MapThumbnail
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository

class FetchMapThumbnailsUseCase(
    private val simpleMapRepository: SimpleMapRepository
) {
    operator fun invoke(): List<MapThumbnail> {

        val result = simpleMapRepository.getMaps()

        result.exceptionOrNull()?.printStackTrace()

        return result.getOrDefault(emptyList()).map { map -> map.thumbnailImage }

    }
}