package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.source.local.DifficultyLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.source.local.DifficultyLocalDataSource.DifficultyModel

class DifficultyRepository(
    context: Context,
    val localSource: DifficultyLocalDataSource
) {
    val difficulties = localSource.fetchDifficulties(context)

    fun fetchDifficulties(context: Context): List<DifficultyModel> {
        return localSource.fetchDifficulties(context)
    }

}