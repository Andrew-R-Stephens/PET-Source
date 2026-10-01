package com.tritiumstudios.data.wearable.usecase

import com.tritiumgaming.data.evidence.model.EvidenceType
import com.tritiumgaming.data.journal.model.EvidenceValidationType
import com.tritiumstudios.data.wearable.repository.WearableRepository

class SendWearableToggleMessageUseCase(
    private val repository: WearableRepository
) {
    suspend operator fun invoke(
        evidenceType: EvidenceType,
        newState: EvidenceValidationType
    ) = repository.sendToggleMessage(evidenceType.id, newState)
}
