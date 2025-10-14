package com.tritiumgaming.shared.operation.domain.map.modifier.usecase

import com.tritiumgaming.shared.operation.domain.map.modifier.mappers.MapModifierResources
import com.tritiumgaming.shared.operation.domain.map.modifier.model.WorldMapModifier
import com.tritiumgaming.shared.operation.domain.map.modifier.repsitory.MapModifiersRepository

class FetchMapModifiersUseCase(
    private val repository: MapModifiersRepository
) {

    operator fun invoke(): Result<List<WorldMapModifier>> {
        val result = repository.getModifiers()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("", it)) }

        val modifiers = result.getOrNull()
            ?: return Result.failure(Exception("Could not get map modifiers"))

        return Result.success(modifiers)
    }

    operator fun invoke(mapSize: MapModifierResources.MapSize): Result<WorldMapModifier> {
        val result = this()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("", it)) }

        val modifiers = result.getOrNull()?.getOrNull(mapSize.ordinal)
            ?: return Result.failure(Exception("Could not get map modifier"))

        return Result.success(modifiers)
    }

}