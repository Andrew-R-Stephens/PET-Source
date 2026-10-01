package com.tritiumstudios.data.operation.usecase

import com.tritiumstudios.data.operation.OperationRepository

class UpdateOperationHuntWarningUseCase(private val repository: OperationRepository) {
    operator fun invoke(warning: Boolean) = repository.updateHuntWarning(warning)
}
