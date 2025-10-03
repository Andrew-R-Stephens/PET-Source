package com.tritiumgaming.data.difficulty.source

interface DifficultyDataSource {

    fun fetchDifficulties(): Result<List<com.tritiumgaming.data.difficulty.dto.DifficultyModelDto>>

}