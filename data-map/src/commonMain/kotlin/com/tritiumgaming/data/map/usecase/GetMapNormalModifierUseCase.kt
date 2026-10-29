package com.tritiumgaming.data.map.usecase

import com.tritiumgaming.data.map.mappers.MapModifierResources.MapSizePhaseModifier

class GetMapNormalModifierUseCase(
    private val fetchMapModifiersUseCase: FetchMapModifiersUseCase
) {
    operator fun invoke(index: Int): Result<MapSizePhaseModifier> {
        val result = fetchMapModifiersUseCase()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get map modifiers", it)) }

        val modifier = result.getOrNull()?.getOrNull(index)?.actionModifier
            ?: return Result.failure(Exception("Could not get map modifier"))

        return Result.success(modifier)
    }
}
