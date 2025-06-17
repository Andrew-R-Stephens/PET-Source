package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.dto

import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.mapper.DifficultyResources
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.model.DifficultyModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.model.DifficultyType

data class DifficultyModelDto(
    val index: Int,
    val name: DifficultyResources.DifficultyTitle,
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
