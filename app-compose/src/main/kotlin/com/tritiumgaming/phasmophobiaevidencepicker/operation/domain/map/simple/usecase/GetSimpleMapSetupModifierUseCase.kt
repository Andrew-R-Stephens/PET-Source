package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository

class GetSimpleMapSetupModifierUseCase(
    private val fetchMapModifiersUseCase: FetchMapModifiersUseCase
) {
    operator fun invoke(index: Int): Float {
        val result = fetchMapModifiersUseCase()

        result.exceptionOrNull()

        return result.getOrNull()?.getOrNull(index)?.setupModifier ?: 0f
    }
}