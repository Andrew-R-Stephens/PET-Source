package com.tritiumgaming.data.challenge.model

import com.tritiumgaming.data.challenge.mapper.ChallengeResources
import com.tritiumgaming.data.difficulty.mapper.DifficultyResources
import com.tritiumgaming.data.map.simple.mappers.SimpleMapResources.MapTitle
import com.tritiumstudios.data.difficultysetting.model.DifficultySettingsModel

data class ChallengeModel(
    val challengeTitle: ChallengeResources.ChallengeTitle,
    val description: ChallengeResources.ChallengeDescription,
    val responseType: DifficultyResources.DifficultyResponseType,
    val map: MapTitle,
    val settingsModel: DifficultySettingsModel,
)