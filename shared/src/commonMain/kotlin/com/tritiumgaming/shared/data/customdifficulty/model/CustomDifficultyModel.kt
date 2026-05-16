package com.tritiumgaming.shared.data.customdifficulty.model

import com.tritiumgaming.shared.data.difficultysetting.model.DifficultySettingsModel

data class CustomDifficultyModel(
    val id: Int = 0,
    val name: String?,
    val settings: DifficultySettingsModel
)
