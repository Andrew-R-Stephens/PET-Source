package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.model.DifficultyModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.repository.DifficultyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.source.local.DifficultyDataSource

class DifficultyRepositoryImpl(
    context: Context,
    override val localSource: DifficultyDataSource
): DifficultyRepository {

    override val difficulties = localSource.fetchDifficulties(context)

    override fun fetchDifficulties(context: Context): List<DifficultyModel> {
        return localSource.fetchDifficulties(context)
    }

}