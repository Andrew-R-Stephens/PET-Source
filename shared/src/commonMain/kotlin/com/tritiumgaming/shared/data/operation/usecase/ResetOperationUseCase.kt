package com.tritiumgaming.shared.data.operation.usecase

import com.tritiumgaming.shared.data.operation.OperationRepository

class ResetOperationUseCase(private val repository: OperationRepository) {
    operator fun invoke() = repository.reset()
}
