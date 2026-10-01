package com.tritiumstudios.data.operation.usecase

import com.tritiumstudios.data.operation.OperationRepository
import com.tritiumstudios.data.operation.model.PhaseData

class UpdateOperationPhaseUseCase(private val repository: OperationRepository) {
    operator fun invoke(phase: PhaseData) = repository.updatePhase(phase)
}
