package com.tritiumstudios.data.operation.usecase

import com.tritiumstudios.data.operation.OperationRepository
import com.tritiumstudios.data.operation.model.TemperatureData

class UpdateOperationTemperatureUseCase(private val repository: OperationRepository) {
    operator fun invoke(temperature: TemperatureData) = repository.updateTemperature(temperature)
}
