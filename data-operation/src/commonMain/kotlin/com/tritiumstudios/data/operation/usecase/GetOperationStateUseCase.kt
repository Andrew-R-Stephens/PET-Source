package com.tritiumstudios.data.operation.usecase

import com.tritiumstudios.data.operation.OperationRepository
import com.tritiumstudios.data.operation.model.OperationData
import kotlinx.coroutines.flow.StateFlow

class GetOperationStateUseCase(
    private val repository: OperationRepository
) {
    operator fun invoke(): StateFlow<OperationData> = repository.state
}