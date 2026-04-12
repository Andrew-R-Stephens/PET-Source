package com.tritiumgaming.shared.data.investigation.model

import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.FuseBoxAtStartOfContract
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather

data class DifficultyOverridesData(
    val weather: Weather = Weather.RANDOM,
    val fuseBox: FuseBoxAtStartOfContract = FuseBoxAtStartOfContract.ON
)
