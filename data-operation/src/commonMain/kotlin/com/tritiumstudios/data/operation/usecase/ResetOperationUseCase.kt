package com.tritiumstudios.data.operation.usecase

import com.tritiumstudios.data.operation.OperationRepository

class ResetOperationUseCase(private val repository: OperationRepository) {
    operator fun invoke() = repository.reset()
}
