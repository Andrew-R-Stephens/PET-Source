package com.tritiumgaming.shared.data.operation.usecase

import com.tritiumgaming.shared.data.operation.OperationRepository
import com.tritiumgaming.shared.data.operation.model.PhaseData

class UpdateOperationPhaseUseCase(private val repository: OperationRepository) {
    operator fun invoke(phase: PhaseData) = repository.updatePhase(phase)
}
