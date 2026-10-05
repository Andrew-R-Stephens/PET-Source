package com.tritiumgaming.data.difficulty.model

import com.tritiumgaming.data.difficulty.mapper.DifficultyResources.GhostResponsePresentation
import com.tritiumgaming.data.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.data.difficulty.mapper.DifficultyResources.DifficultyType
import com.tritiumstudios.data.difficultysetting.model.DifficultySettingsModel

data class DifficultyModel(
    val type: DifficultyType,
    val difficultyTitle: DifficultyTitle,
    val responseType: GhostResponsePresentation,
    val settingsModel: DifficultySettingsModel
)
