package com.tritiumgaming.data.challenges.dto

import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources
import com.tritiumgaming.shared.data.challenge.model.ChallengeModel
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.shared.data.difficultysetting.dto.DifficultySettingsModelDto
import com.tritiumgaming.shared.data.difficultysetting.dto.toDomain
import com.tritiumgaming.shared.data.difficultysetting.model.DifficultySettingsModel
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources

data class ChallengeModelDto(
    val challengeTitle: ChallengeResources.ChallengeTitle,
    val description: ChallengeResources.ChallengeDescription,
    val responseType: DifficultyResponseType,
    val map: SimpleMapResources.MapTitle,
    val settingsModelDto: DifficultySettingsModelDto
)

internal fun ChallengeModelDto.toDomain() = ChallengeModel(
    challengeTitle = challengeTitle,
    description = description,
    responseType = responseType,
    map = map,
    settingsModel = settingsModelDto.toDomain()
)

internal fun List<ChallengeModelDto>.toDomain() = map{ dto ->
    dto.toDomain()
}
