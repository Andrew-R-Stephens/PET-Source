package com.tritiumgaming.shared.data.challenge.model

import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.shared.data.difficultysetting.model.DifficultySettingsModel
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources

data class ChallengeModel(
    val challengeTitle: ChallengeResources.ChallengeTitle,
    val description: ChallengeResources.ChallengeDescription,
    val responseType: DifficultyResponseType,
    val map: SimpleMapResources.MapTitle,
    val settingsModel: DifficultySettingsModel,
)