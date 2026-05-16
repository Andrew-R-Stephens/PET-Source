package com.tritiumgaming.shared.data.investigation.model

import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather

data class DifficultyOverridesData(
    val weather: Weather = Weather.RANDOM,
    val fuseBox: FuseBoxFlag = FuseBoxFlag.FUSEBOX_ENABLED
) {

    companion object {
        enum class FuseBoxFlag {
            FUSEBOX_ENABLED,
            FUSEBOX_DISABLED
        }
    }

}
