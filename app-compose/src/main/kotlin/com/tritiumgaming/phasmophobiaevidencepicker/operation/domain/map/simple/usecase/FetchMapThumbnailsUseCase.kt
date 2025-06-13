package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase

import androidx.annotation.DrawableRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository

class FetchMapThumbnailsUseCase(
    private val simpleMapRepository: SimpleMapRepository
) {
    @DrawableRes operator fun invoke(): List<Int> {
        val result = simpleMapRepository.getThumbnails()

        result.exceptionOrNull()?.printStackTrace()

        return result.getOrNull() ?: emptyList()

    }
}