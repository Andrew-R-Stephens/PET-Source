package com.tritiumgaming.feature.customdifficulty.app.container

import android.content.Context
import com.tritiumgaming.data.customdifficulty.usecase.GetCustomDifficultiesUseCase
import com.tritiumgaming.data.customdifficulty.usecase.UpdateCustomDifficultyUseCase

class CustomDifficultyContainer(
    val applicationContext: Context,
    val getCustomDifficultiesUseCase: GetCustomDifficultiesUseCase,
    val updateCustomDifficultyUseCase: UpdateCustomDifficultyUseCase
) {

}
