package com.tritiumgaming.feature.home.ui.appinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.feature.home.app.container.HomeContainerProvider
import com.tritiumgaming.shared.home.domain.appinfo.model.Contributor
import com.tritiumgaming.shared.home.domain.appinfo.usecase.ContributorsUseCase

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
                val application = this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY]
                val container = (application as HomeContainerProvider).provideHomeContainer()

                val getSpecialThanksUseCase: ContributorsUseCase =
                    container.getContributorsUseCase

                AppInfoViewModel(
                    getContributorsUseCase = getSpecialThanksUseCase
                )

            }
        }
    }

}