package com.tritiumgaming.shared.data.wearable.usecase

import com.tritiumgaming.shared.data.wearable.repository.WearableRepository

class ObserveWearableOperationDataUseCase(private val repository: WearableRepository) {
    operator fun invoke() = repository.observeOperationData()
}
