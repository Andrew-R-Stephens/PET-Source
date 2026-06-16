package com.tritiumgaming.shared.data.operation.usecase

import com.tritiumgaming.shared.data.operation.OperationRepository

class UpdateOperationHuntWarningUseCase(private val repository: OperationRepository) {
    operator fun invoke(warning: Boolean) = repository.updateHuntWarning(warning)
}
