package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase

import androidx.annotation.DrawableRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toDrawableResource

class FetchMapThumbnailsUseCase(
    private val simpleMapRepository: SimpleMapRepository
) {
    @DrawableRes operator fun invoke(): List<Int> {

        val result = simpleMapRepository.getMaps()

        result.exceptionOrNull()?.printStackTrace()

        val thumbnails = result.getOrNull()?.map { map ->
            map.thumbnailImage.toDrawableResource() }

        return thumbnails ?: emptyList()

    }
}