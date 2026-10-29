package com.tritiumgaming.data.map.usecase

import com.tritiumgaming.data.map.mappers.MapModifierResources.MapSizePhaseModifier

class GetMapSetupModifierUseCase(
    private val fetchMapModifiersUseCase: FetchMapModifiersUseCase
) {
    operator fun invoke(index: Int): Result<MapSizePhaseModifier> {
        val result = fetchMapModifiersUseCase()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Failure to get map modifier", it)) }

        val modifier = result.getOrNull()?.getOrNull(index)?.setupModifier
            ?: return Result.failure(Exception("Could not get map modifier"))

        return Result.success(modifier)
    }
}