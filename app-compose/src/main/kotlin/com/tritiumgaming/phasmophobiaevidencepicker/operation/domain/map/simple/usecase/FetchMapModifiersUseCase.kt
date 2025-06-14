package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifiers.repsitory.MapModifiersRepository

class FetchMapModifiersUseCase(
    private val simpleMapRepository: MapModifiersRepository
) {
    operator fun invoke() = simpleMapRepository.getModifiers()
}