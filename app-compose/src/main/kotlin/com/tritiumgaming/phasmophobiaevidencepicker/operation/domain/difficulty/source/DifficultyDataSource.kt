package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.source

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.dto.DifficultyModelDto

interface DifficultyDataSource {

    fun fetchDifficulties(): Result<List<DifficultyModelDto>>

}