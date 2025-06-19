package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.model

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.mapper.DifficultyResources.DifficultyTitle

data class DifficultyModel(
    val type: DifficultyType,
    val name: DifficultyTitle,
    val time: Long,
    val modifier: Float,
    val initialSanity: Float
)
