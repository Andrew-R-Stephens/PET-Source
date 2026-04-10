package com.tritiumgaming.shared.data.investigation.model

import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather

data class OperationConditionsData(
    val weatherOverride: Weather = Weather.RANDOM
)
