package com.tritiumgaming.shared.data.operation.model

import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather

data class OperationOverrideData(
    val weather: Weather = Weather.RANDOM,
    val fuseBox: FuseBoxFlag = FuseBoxFlag.FUSEBOX_ENABLED,
    val cursedInvestigation: Boolean = false
) {

    companion object {
        enum class FuseBoxFlag {
            FUSEBOX_ENABLED,
            FUSEBOX_DISABLED,
            FUSEBOX_BROKEN
        }
    }

}
