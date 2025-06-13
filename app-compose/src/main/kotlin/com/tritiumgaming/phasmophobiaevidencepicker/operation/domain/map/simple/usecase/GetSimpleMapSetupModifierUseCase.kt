package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository

class GetSimpleMapSetupModifierUseCase(
    private val simpleMapsRepository: SimpleMapRepository
) {
    operator fun invoke(index: Int): Float {
        val result = simpleMapsRepository.getModifiers()

        result.exceptionOrNull()

        return result.getOrNull()?.getOrNull(index)?.setupModifier ?: 0f
    }
}