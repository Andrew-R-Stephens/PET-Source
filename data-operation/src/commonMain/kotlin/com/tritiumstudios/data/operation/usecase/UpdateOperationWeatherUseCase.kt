package com.tritiumstudios.data.operation.usecase

import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources
import com.tritiumstudios.data.operation.OperationRepository

class UpdateOperationWeatherUseCase(private val repository: OperationRepository) {
    operator fun invoke(weather: DifficultySettingResources.Weather) = repository.updateWeather(weather)
}
