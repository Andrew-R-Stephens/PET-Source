package com.tritiumgaming.shared.data.operation.usecase

import com.tritiumgaming.shared.data.operation.OperationRepository

class ResetInvestigationUseCase(private val repository: OperationRepository) {
    operator fun invoke() = repository.reset()
}
