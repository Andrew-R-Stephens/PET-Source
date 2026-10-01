package com.tritiumstudios.data.wearable.usecase

import com.tritiumstudios.data.wearable.repository.WearableRepository

class SendWearableSanityMessageUseCase(private val repository: WearableRepository) {
    suspend operator fun invoke(sanityLevel: Float) =
        repository.sendSanityUpdateMessage(sanityLevel)
}
