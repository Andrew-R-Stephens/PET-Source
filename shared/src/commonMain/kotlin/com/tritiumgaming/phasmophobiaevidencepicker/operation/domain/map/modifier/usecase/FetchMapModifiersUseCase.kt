package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.model.WorldMapModifier
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.repsitory.MapModifiersRepository

class FetchMapModifiersUseCase(
    private val simpleMapRepository: MapModifiersRepository
) {
    operator fun invoke(): Result<List<WorldMapModifier>> {
        val result = simpleMapRepository.getModifiers()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("", it)) }

        val modifiers = result.getOrNull()
            ?: return Result.failure(Exception("Could not get map modifiers"))

        return Result.success(modifiers)
    }

    operator fun invoke(index: Int): Result<WorldMapModifier> {
        val result = this()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("", it)) }

        val modifiers = result.getOrNull()?.getOrNull(index)
            ?: return Result.failure(Exception("Could not get map modifier"))

        return Result.success(modifiers)
    }
}