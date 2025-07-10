package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.usecase

class GetSimpleMapNormalModifierUseCase(
    private val fetchMapModifiersUseCase: FetchMapModifiersUseCase
) {
    operator fun invoke(index: Int): Result<Float> {
        val result = fetchMapModifiersUseCase()

        result.exceptionOrNull()?.let {
            return Result.failure(Exception("", it)) }

        val modifier = result.getOrNull()?.getOrNull(index)?.normalModifier
            ?: return Result.failure(Exception("Could not get map modifier"))

        return Result.success(modifier)
    }
}
