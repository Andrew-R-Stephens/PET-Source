package com.tritiumgaming.data.customdifficulty.model

import com.tritiumstudios.data.difficultysetting.model.DifficultySettingsModel

data class CustomDifficultyModel(
    val id: Int = 0,
    val name: String?,
    val settings: DifficultySettingsModel
)
