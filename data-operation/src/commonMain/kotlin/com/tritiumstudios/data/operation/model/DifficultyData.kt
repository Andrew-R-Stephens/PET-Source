package com.tritiumstudios.data.operation.model

import com.tritiumgaming.data.challenge.mapper.ChallengeResources
import com.tritiumgaming.data.difficulty.mapper.DifficultyResources.GhostResponsePresentation
import com.tritiumgaming.data.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.data.difficulty.mapper.DifficultyResources.DifficultyType
import com.tritiumstudios.data.difficultysetting.model.DifficultySettingsModel

data class DifficultyData(
    val index: Int = 0,
    val type: DifficultyType = DifficultyType.AMATEUR,
    val title: DifficultyTitle = DifficultyTitle.AMATEUR,
    val responseType: GhostResponsePresentation = GhostResponsePresentation.KNOWN,
    val challengeTitle: ChallengeResources.ChallengeTitle? = null,
    val settings: DifficultySettingsModel = DifficultySettingsModel(),
    val customDifficultyIndex: Int? = null
)
