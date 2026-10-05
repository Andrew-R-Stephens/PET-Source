package com.tritiumgaming.data.difficulty.dto

import com.tritiumgaming.data.difficulty.mapper.DifficultyResources.GhostResponsePresentation
import com.tritiumgaming.data.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.data.difficulty.mapper.DifficultyResources.DifficultyType
import com.tritiumgaming.data.difficulty.model.DifficultyModel
import com.tritiumstudios.data.difficultysetting.dto.DifficultySettingsModelDto
import com.tritiumstudios.data.difficultysetting.dto.toDomain

data class DifficultyModelDto(
    val type: DifficultyType,
    val difficultyTitle: DifficultyTitle,
    val responseType: GhostResponsePresentation,
    val settingsModelDto: DifficultySettingsModelDto
)

internal fun DifficultyModelDto.toDomain() = DifficultyModel(
    type = type,
    difficultyTitle = difficultyTitle,
    responseType = responseType,
    settingsModel = settingsModelDto.toDomain()
)

internal fun List<DifficultyModelDto>.toDomain() = map{ dto ->
    dto.toDomain()
}
