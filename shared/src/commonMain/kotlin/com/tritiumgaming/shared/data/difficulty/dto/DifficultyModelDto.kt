package com.tritiumgaming.shared.data.difficulty.dto

import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyType
import com.tritiumgaming.shared.data.difficulty.model.DifficultyModel
import com.tritiumgaming.shared.data.difficultysetting.dto.DifficultySettingsModelDto
import com.tritiumgaming.shared.data.difficultysetting.dto.toDomain

data class DifficultyModelDto(
    val type: DifficultyType,
    val difficultyTitle: DifficultyTitle,
    val responseType: DifficultyResponseType,
    val settingsModelDto: DifficultySettingsModelDto
)

fun DifficultyModelDto.toDomain() = DifficultyModel(
    type = type,
    difficultyTitle = difficultyTitle,
    responseType = responseType,
    settingsModel = settingsModelDto.toDomain()
)

fun List<DifficultyModelDto>.toDomain() = map { dto ->
    dto.toDomain()
}
