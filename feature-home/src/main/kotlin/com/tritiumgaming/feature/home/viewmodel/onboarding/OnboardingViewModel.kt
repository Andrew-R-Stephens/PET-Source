package com.tritiumgaming.feature.home.viewmodel.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.feature.home.app.container.HomeContainerProvider

/** @noinspection SameParameterValue
 */
class OnboardingViewModel(): ViewModel() {

    var showIntroduction: Boolean = true

    class OnboardingFactory : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OnboardingViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return OnboardingViewModel(
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY]
                val container = (application as HomeContainerProvider).provideHomeContainer()

                OnboardingViewModel()
            }
        }
    }
}