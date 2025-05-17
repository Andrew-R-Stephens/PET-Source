package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.model.DifficultyModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.source.local.DifficultyDataSource

interface DifficultyRepository {

    val localSource: DifficultyDataSource

    val difficulties: List<DifficultyModel>

    fun fetchDifficulties(context: Context): List<DifficultyModel>

}