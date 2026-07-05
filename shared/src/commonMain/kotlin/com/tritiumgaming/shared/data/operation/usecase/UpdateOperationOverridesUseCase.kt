package com.tritiumgaming.shared.data.operation.usecase

import com.tritiumgaming.shared.data.operation.OperationRepository
import com.tritiumgaming.shared.data.operation.model.OperationOverrideData

class UpdateOperationOverridesUseCase(
    private val repository: OperationRepository
) {
    operator fun invoke(overrides: OperationOverrideData) =
        repository.updateOverrides(overrides)
}
