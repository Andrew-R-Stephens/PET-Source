package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.usecase

class GetSimpleMapSetupModifierUseCase(
    private val fetchMapModifiersUseCase: FetchMapModifiersUseCase
) {
    operator fun invoke(index: Int): Float {
        val result = fetchMapModifiersUseCase()

        result.exceptionOrNull()

        return result.getOrNull()?.getOrNull(index)?.setupModifier ?: 0f
    }
}