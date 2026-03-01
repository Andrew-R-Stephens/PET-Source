package com.tritiumgaming.shared.data.difficultysetting.repository

import com.tritiumgaming.shared.data.difficulty.model.DifficultyModel

interface DifficultySettingRepository {

    fun getDifficulties(): Result<List<DifficultyModel>>

}