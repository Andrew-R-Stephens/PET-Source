package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.source

interface DifficultyDataSource {

    fun fetchDifficulties(): Result<List<com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.dto.DifficultyModelDto>>

}