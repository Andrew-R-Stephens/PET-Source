package com.tritiumgaming.data.difficulty.source

import com.tritiumgaming.data.difficulty.dto.DifficultyModelDto

interface DifficultyDataSource {

    fun fetchDifficulties(): Result<List<DifficultyModelDto>>

}