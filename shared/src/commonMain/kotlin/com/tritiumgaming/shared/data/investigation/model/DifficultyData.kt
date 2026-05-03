package com.tritiumgaming.shared.data.investigation.model

import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyType
import com.tritiumgaming.shared.data.difficultysetting.model.DifficultySettingsModel

data class DifficultyData(
    val index: Int = 0,
    val type: DifficultyType = DifficultyType.AMATEUR,
    val title: DifficultyTitle = DifficultyTitle.AMATEUR,
    val responseType: DifficultyResponseType = DifficultyResponseType.KNOWN,
    val challengeTitle: ChallengeResources.ChallengeTitle? = null,
    val settings: DifficultySettingsModel = DifficultySettingsModel(),
    val customIndex: Int? = null
)
