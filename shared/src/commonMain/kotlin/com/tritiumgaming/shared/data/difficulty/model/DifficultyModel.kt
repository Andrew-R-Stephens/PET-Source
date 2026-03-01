package com.tritiumgaming.shared.data.difficulty.model

import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyType
import com.tritiumgaming.shared.data.difficultysetting.model.DifficultySettingsModel

data class DifficultyModel(
    val type: DifficultyType,
    val name: DifficultyTitle,
    val responseType: DifficultyResponseType,
    val difficultySettingsModel: DifficultySettingsModel
)
