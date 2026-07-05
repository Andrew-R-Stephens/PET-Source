package com.tritiumgaming.shared.data.operation.usecase

import com.tritiumgaming.shared.data.operation.OperationRepository
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather

class UpdateOperationWeatherUseCase(private val repository: OperationRepository) {
    operator fun invoke(weather: Weather) = repository.updateWeather(weather)
}
