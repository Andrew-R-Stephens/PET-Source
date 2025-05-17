package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.source.local

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.model.DifficultyModel

interface DifficultyDataSource {

    fun fetchDifficulties(context: Context): List<DifficultyModel>

}