package com.tritiumgaming.shared.operation.domain.map.modifier.usecase

class GetMapModifierUseCase(
    private val getSimpleMapNormalModifierUseCase: GetSimpleMapNormalModifierUseCase,
    private val getSimpleMapSetupModifierUseCase: GetSimpleMapSetupModifierUseCase
) {
    operator fun invoke(
        mapSize: Int,
        timeRemaining: Long = 0L
    ): Result<Float> {
        val result = if (timeRemaining <= 0L) {
            getSimpleMapNormalModifierUseCase(mapSize) }
        else { getSimpleMapSetupModifierUseCase(mapSize) }

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get map modifiers", it)) }

        val modifier = result.getOrNull()
            ?: return Result.failure(Exception("Could not get map modifier"))

        return Result.success(modifier)


    }
}

