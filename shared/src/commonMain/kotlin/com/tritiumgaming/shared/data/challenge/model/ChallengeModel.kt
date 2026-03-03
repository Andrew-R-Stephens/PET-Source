package com.tritiumgaming.shared.data.challenge.model

import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.shared.data.difficultysetting.model.DifficultySettingsModel

data class ChallengeModel(
    val name: ChallengeResources.ChallengeTitle,
    val description: ChallengeResources.ChallengeDescription,
    val responseType: DifficultyResponseType,
    val challengeResourceSettingsModel: DifficultySettingsModel
)