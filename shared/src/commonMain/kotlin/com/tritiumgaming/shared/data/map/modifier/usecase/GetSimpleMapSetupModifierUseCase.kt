package com.tritiumgaming.shared.data.map.modifier.usecase

import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSizePhaseModifier

class GetSimpleMapSetupModifierUseCase(
    private val fetchSimpleMapModifiersUseCase: FetchSimpleMapModifiersUseCase
) {
    operator fun invoke(index: Int): Result<MapSizePhaseModifier> {
        val result = fetchSimpleMapModifiersUseCase()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Failure to get map modifier", it)) }

        val modifier = result.getOrNull()?.getOrNull(index)?.setupModifier
            ?: return Result.failure(Exception("Could not get map modifier"))

        return Result.success(modifier)
    }
}