package com.tritiumstudios.data.operation.usecase

import com.tritiumstudios.data.operation.OperationRepository
import com.tritiumstudios.data.operation.model.OperationOverrideData

class UpdateOperationOverridesUseCase(
    private val repository: OperationRepository
) {
    operator fun invoke(overrides: OperationOverrideData) =
        repository.updateOverrides(overrides)
}
