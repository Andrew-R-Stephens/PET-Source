package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.dto

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.mapper.DifficultyResources.DifficultyType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.model.DifficultyModel

data class DifficultyModelDto(
    val index: Int,
    val name: DifficultyTitle,
    val time: Long,
    val modifier: Float,
    val initialSanity: Float
)

fun DifficultyModelDto.toDomain() = DifficultyModel(
    type = DifficultyType.entries[index],
    name = name,
    time = time,
    modifier = modifier,
    initialSanity = initialSanity
)

fun List<DifficultyModelDto>.toDomain() = map{ dto ->
    dto.toDomain()
}
