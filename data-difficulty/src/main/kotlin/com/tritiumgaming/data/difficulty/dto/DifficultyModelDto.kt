package com.tritiumgaming.data.difficulty.dto

import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyType
import com.tritiumgaming.shared.data.difficulty.model.DifficultyModel

data class DifficultyModelDto(
    val type: DifficultyType,
    val name: DifficultyTitle,
    val responseType: DifficultyResponseType,
    val difficultySettingsModelDto: DifficultySettingsModelDto
)

internal fun DifficultyModelDto.toDomain() = DifficultyModel(
    type = type,
    name = name,
    responseType = responseType,
    difficultySettingsModel = difficultySettingsModelDto.toDomain()
)

internal fun List<DifficultyModelDto>.toDomain() = map{ dto ->
    dto.toDomain()
}
