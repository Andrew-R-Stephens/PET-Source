package com.tritiumstudios.data.operation.usecase

import com.tritiumstudios.data.operation.OperationRepository
import com.tritiumstudios.data.operation.model.GhostDetails

class UpdateOperationGhostDetailsUseCase(private val repository: OperationRepository) {
    operator fun invoke(ghostDetails: GhostDetails) = repository.updateGhostDetails(ghostDetails)
}
