package com.tritiumgaming.shared.data.map.modifier.usecase

import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSizePhaseModifier
import com.tritiumgaming.shared.data.phase.model.PhaseResources.PhaseIdentifier

class GetSimpleMapModifierUseCase(
    private val getSimpleMapNormalModifierUseCase: GetSimpleMapNormalModifierUseCase,
    private val getSimpleMapSetupModifierUseCase: GetSimpleMapSetupModifierUseCase
) {
    operator fun invoke(
        mapSize: Int,
        phaseIdentifier: PhaseIdentifier
    ): Result<MapSizePhaseModifier> {
        val result = when(phaseIdentifier) {
            PhaseIdentifier.ACTION, PhaseIdentifier.HUNT -> getSimpleMapNormalModifierUseCase(mapSize)
            PhaseIdentifier.SETUP -> getSimpleMapSetupModifierUseCase(mapSize)
        }

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get map modifiers", it)) }

        val modifier = result.getOrNull()
            ?: return Result.failure(Exception("Could not get map modifier"))

        return Result.success(modifier)
    }

}

