package com.tritiumgaming.feature.about.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.feature.about.app.container.AboutContainerProvider
import com.tritiumgaming.shared.data.contributor.model.Contributor
import com.tritiumgaming.shared.data.contributor.usecase.ContributorsUseCase

/**
 * TitleScreenViewModel class
 *
 * @author TritiumGamingStudios
 */
class AppInfoViewModel(
    private val getContributorsUseCase: ContributorsUseCase
): ViewModel() {

    private val _contributorsList: List<Contributor>
        get() = getContributorsUseCase().getOrDefault(emptyList())
    val contributorsList = _contributorsList

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                val container = (application as AboutContainerProvider).provideAboutContainer()

                val getSpecialThanksUseCase: ContributorsUseCase =
                    container.getContributorsUseCase

                AppInfoViewModel(
                    getContributorsUseCase = getSpecialThanksUseCase
                )

            }
        }
    }

}