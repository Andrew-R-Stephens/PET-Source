package com.tritiumgaming.shared.data.wearable.usecase

import com.tritiumgaming.shared.data.wearable.repository.WearableRepository

class SendWearableToggleMessageUseCase(private val repository: WearableRepository) {
    suspend operator fun invoke(evidenceId: String, newState: String) = 
        repository.sendToggleMessage(evidenceId, newState)
}
