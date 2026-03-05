package com.tritiumgaming.shared.data.map.modifier.usecase

import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSizePhaseModifier

class GetSimpleMapNormalModifierUseCase(
    private val fetchSimpleMapModifiersUseCase: FetchSimpleMapModifiersUseCase
) {
    operator fun invoke(index: Int): Result<MapSizePhaseModifier> {
        val result = fetchSimpleMapModifiersUseCase()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get map modifiers", it)) }

        val modifier = result.getOrNull()?.getOrNull(index)?.actionModifier
            ?: return Result.failure(Exception("Could not get map modifier"))

        return Result.success(modifier)
    }
}
