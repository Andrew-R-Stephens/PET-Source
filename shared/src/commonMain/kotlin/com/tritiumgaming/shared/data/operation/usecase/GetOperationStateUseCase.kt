package com.tritiumgaming.shared.data.operation.usecase

import com.tritiumgaming.shared.data.operation.OperationRepository
import com.tritiumgaming.shared.data.operation.model.OperationData
import kotlinx.coroutines.flow.StateFlow

class GetOperationStateUseCase(
    private val repository: OperationRepository
) {
    operator fun invoke(): StateFlow<OperationData> = repository.state
}