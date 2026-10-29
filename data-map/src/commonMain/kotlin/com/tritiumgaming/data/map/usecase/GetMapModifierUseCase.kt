package com.tritiumgaming.data.map.usecase

import com.tritiumgaming.data.map.mappers.MapModifierResources.MapSizePhaseModifier
import com.tritiumstudios.data.phase.mappers.PhaseResources.PhaseIdentifier

class GetMapModifierUseCase(
    private val getMapNormalModifierUseCase: GetMapNormalModifierUseCase,
    private val getMapSetupModifierUseCase: GetMapSetupModifierUseCase
) {
    operator fun invoke(
        mapSize: Int,
        phaseIdentifier: PhaseIdentifier
    ): Result<MapSizePhaseModifier> {
        val result = when(phaseIdentifier) {
            PhaseIdentifier.ACTION, PhaseIdentifier.HUNT -> getMapNormalModifierUseCase(mapSize)
            PhaseIdentifier.SETUP -> getMapSetupModifierUseCase(mapSize)
        }

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get map modifiers", it)) }

        val modifier = result.getOrNull()
            ?: return Result.failure(Exception("Could not get map modifier"))

        return Result.success(modifier)
    }

}

