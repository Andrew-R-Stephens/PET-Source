package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.source

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.model.DifficultyModel

interface DifficultyDataSource {

    fun fetchDifficulties(): Result<List<DifficultyModel>>

}