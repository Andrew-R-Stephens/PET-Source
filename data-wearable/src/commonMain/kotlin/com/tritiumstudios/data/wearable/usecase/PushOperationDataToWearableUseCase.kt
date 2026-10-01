package com.tritiumstudios.data.wearable.usecase

import com.tritiumstudios.data.wearable.model.WearableOperationData
import com.tritiumstudios.data.wearable.repository.WearableRepository

class PushOperationDataToWearableUseCase(private val repository: WearableRepository) {
    suspend operator fun invoke(data: WearableOperationData) = repository.pushOperationData(data)
}
