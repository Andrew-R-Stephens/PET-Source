package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase

class GetMapModifierUseCase(
    private val getSimpleMapNormalModifierUseCase: GetSimpleMapNormalModifierUseCase,
    private val getSimpleMapSetupModifierUseCase: GetSimpleMapSetupModifierUseCase
) {
    operator fun invoke(
        mapSize: Int,
        timeRemaining: Long = 0L
    ): Float {
        if (timeRemaining <= 0L) {
            return getSimpleMapNormalModifierUseCase(mapSize)
        }
        return getSimpleMapSetupModifierUseCase(mapSize)
    }
}