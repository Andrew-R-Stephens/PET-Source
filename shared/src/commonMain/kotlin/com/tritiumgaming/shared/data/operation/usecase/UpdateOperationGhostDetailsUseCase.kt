package com.tritiumgaming.shared.data.operation.usecase

import com.tritiumgaming.shared.data.operation.OperationRepository
import com.tritiumgaming.shared.data.operation.model.GhostDetails

class UpdateOperationGhostDetailsUseCase(private val repository: OperationRepository) {
    operator fun invoke(ghostDetails: GhostDetails) = repository.updateGhostDetails(ghostDetails)
}
