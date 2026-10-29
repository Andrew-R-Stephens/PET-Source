package com.tritiumgaming.data.challenge.dto

import com.tritiumgaming.data.challenge.mapper.ChallengeResources.ChallengeDescription
import com.tritiumgaming.data.challenge.mapper.ChallengeResources.ChallengeTitle
import com.tritiumgaming.data.challenge.model.ChallengeModel
import com.tritiumgaming.data.difficulty.mapper.DifficultyResources.GhostResponsePresentation
import com.tritiumstudios.data.difficultysetting.dto.DifficultySettingsModelDto
import com.tritiumstudios.data.difficultysetting.dto.toDomain

data class ChallengeModelDto(
    val challengeTitle: ChallengeTitle,
    val description: ChallengeDescription,
    val responseType: GhostResponsePresentation,
    val mapId: String,
    val settingsModelDto: DifficultySettingsModelDto
)

internal fun ChallengeModelDto.toDomain() = ChallengeModel(
    challengeTitle = challengeTitle,
    description = description,
    responseType = responseType,
    mapId = mapId,
    settingsModel = settingsModelDto.toDomain()
)

internal fun List<ChallengeModelDto>.toDomain() = map{ dto ->
    dto.toDomain()
}
