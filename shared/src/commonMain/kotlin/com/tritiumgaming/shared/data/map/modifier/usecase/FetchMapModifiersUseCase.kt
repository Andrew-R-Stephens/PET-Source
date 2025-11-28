package com.tritiumgaming.shared.data.map.modifier.usecase

class FetchMapModifiersUseCase(
    private val repository: com.tritiumgaming.shared.data.map.modifier.repsitory.MapModifiersRepository
) {

    operator fun invoke(): Result<List<com.tritiumgaming.shared.data.map.modifier.model.WorldMapModifier>> {
        val result = repository.getModifiers()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("", it)) }

        val modifiers = result.getOrNull()
            ?: return Result.failure(Exception("Could not get map modifiers"))

        return Result.success(modifiers)
    }

    operator fun invoke(mapSize: com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSize): Result<com.tritiumgaming.shared.data.map.modifier.model.WorldMapModifier> {
        val result = this()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("", it)) }

        val modifiers = result.getOrNull()?.getOrNull(mapSize.ordinal)
            ?: return Result.failure(Exception("Could not get map modifier"))

        return Result.success(modifiers)
    }

}