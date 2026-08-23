package com.tritiumgaming.shared.data.wearable.usecase

import com.tritiumgaming.shared.data.wearable.repository.WearableRepository

class SendWearableSanityMessageUseCase(private val repository: WearableRepository) {
    suspend operator fun invoke(sanityLevel: Float) =
        repository.sendSanityUpdateMessage(sanityLevel)
}
