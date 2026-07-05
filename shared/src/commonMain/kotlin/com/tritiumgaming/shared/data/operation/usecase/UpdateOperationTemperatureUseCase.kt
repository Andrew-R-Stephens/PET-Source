package com.tritiumgaming.shared.data.operation.usecase

import com.tritiumgaming.shared.data.operation.OperationRepository
import com.tritiumgaming.shared.data.operation.model.TemperatureData

class UpdateOperationTemperatureUseCase(private val repository: OperationRepository) {
    operator fun invoke(temperature: TemperatureData) = repository.updateTemperature(temperature)
}
