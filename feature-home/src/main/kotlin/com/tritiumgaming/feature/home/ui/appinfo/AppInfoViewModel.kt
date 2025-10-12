package com.tritiumgaming.feature.home.ui.appinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.feature.home.app.container.HomeContainerProvider
import com.tritiumgaming.shared.home.domain.appinfo.model.Contributor
import com.tritiumgaming.shared.home.domain.appinfo.usecase.ContributorUseCase

/**
 * TitleScreenViewModel class
 *
 * @author TritiumGamingStudios
 */
class AppInfoViewModel(
    private val getSpecialThanksUseCase: ContributorUseCase
): ViewModel() {

    val specialThanksList: List<Contributor>
        get() = getSpecialThanksUseCase()

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY]
                val container = (application as HomeContainerProvider).provideHomeContainer()

                val getSpecialThanksUseCase: ContributorUseCase =
                    container.getContributorsUseCase

                AppInfoViewModel(
                    getSpecialThanksUseCase = getSpecialThanksUseCase
                )

            }
        }
    }

}