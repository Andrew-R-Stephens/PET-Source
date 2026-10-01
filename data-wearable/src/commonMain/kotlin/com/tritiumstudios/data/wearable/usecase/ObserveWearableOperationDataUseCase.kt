package com.tritiumstudios.data.wearable.usecase

import com.tritiumstudios.data.wearable.repository.WearableRepository

class ObserveWearableOperationDataUseCase(private val repository: WearableRepository) {
    operator fun invoke() = repository.observeOperationData()
}
