package com.tritiumgaming.shared.data.wearable.usecase

import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.operation.model.EvidenceValidationType
import com.tritiumgaming.shared.data.wearable.repository.WearableRepository

class SendWearableToggleMessageUseCase(private val repository: WearableRepository) {
    suspend operator fun invoke(evidenceId: EvidenceType, newState: EvidenceValidationType) =
        repository.sendToggleMessage(evidenceId, newState)
}
