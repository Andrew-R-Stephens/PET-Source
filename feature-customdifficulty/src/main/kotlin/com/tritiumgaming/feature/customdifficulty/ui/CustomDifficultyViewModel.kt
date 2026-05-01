package com.tritiumgaming.feature.customdifficulty.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.feature.customdifficulty.app.container.CustomDifficultyContainerProvider

class CustomDifficultyViewModel(
): ViewModel() {

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                val container = (application as CustomDifficultyContainerProvider).provideCustomDifficultyContainer()

                CustomDifficultyViewModel(

                )
            }
        }
    }

}