package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository

class FetchMapThumbnailsUseCase(
    private val simpleMapRepository: SimpleMapRepository
) {
    operator fun invoke() = simpleMapRepository.getThumbnails()
}