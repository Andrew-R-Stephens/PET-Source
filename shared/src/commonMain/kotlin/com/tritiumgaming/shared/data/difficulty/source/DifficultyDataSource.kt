package com.tritiumgaming.shared.data.difficulty.source

import com.tritiumgaming.shared.data.difficulty.dto.DifficultyModelDto

interface DifficultyDataSource {

    fun fetchDifficulties(): Result<List<DifficultyModelDto>>

}
