package com.tritiumgaming.shared.data.map.modifier.usecase

import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSizePhaseModifier
import com.tritiumgaming.shared.data.phase.model.Phase

class GetSimpleMapModifierUseCase(
    private val getSimpleMapNormalModifierUseCase: GetSimpleMapNormalModifierUseCase,
    private val getSimpleMapSetupModifierUseCase: GetSimpleMapSetupModifierUseCase
) {
    operator fun invoke(
        mapSize: Int,
        phase: Phase
    ): Result<MapSizePhaseModifier> {
        val result = when(phase) {
            Phase.ACTION, Phase.HUNT -> getSimpleMapNormalModifierUseCase(mapSize)
            Phase.SETUP -> getSimpleMapSetupModifierUseCase(mapSize)
        }

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get map modifiers", it)) }

        val modifier = result.getOrNull()
            ?: return Result.failure(Exception("Could not get map modifier"))

        return Result.success(modifier)
    }

}

