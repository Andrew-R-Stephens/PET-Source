package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.repsitory.MapModifiersRepository

class FetchMapModifiersUseCase(
    private val simpleMapRepository: MapModifiersRepository
) {
    operator fun invoke() = simpleMapRepository.getModifiers()
}