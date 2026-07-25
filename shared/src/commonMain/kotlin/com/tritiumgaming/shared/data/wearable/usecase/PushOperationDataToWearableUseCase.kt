package com.tritiumgaming.shared.data.wearable.usecase

import com.tritiumgaming.shared.data.wearable.model.WearableOperationData
import com.tritiumgaming.shared.data.wearable.repository.WearableRepository

class PushOperationDataToWearableUseCase(private val repository: WearableRepository) {
    suspend operator fun invoke(data: WearableOperationData) = repository.pushOperationData(data)
}
