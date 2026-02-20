package com.tritiumgaming.shared.data.map.modifier.usecase

class GetSimpleMapNormalModifierUseCase(
    private val fetchSimpleMapModifiersUseCase: FetchSimpleMapModifiersUseCase
) {
    operator fun invoke(index: Int): Result<Float> {
        val result = fetchSimpleMapModifiersUseCase()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("Could not get map modifiers", it)) }

        val modifier = result.getOrNull()?.getOrNull(index)?.normalModifier
            ?: return Result.failure(Exception("Could not get map modifier"))

        return Result.success(modifier)
    }
}
