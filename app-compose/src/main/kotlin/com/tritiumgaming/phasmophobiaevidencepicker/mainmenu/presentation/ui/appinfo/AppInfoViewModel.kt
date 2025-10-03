package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.appinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.PETApplication
import com.tritiumgaming.shared.mainmenu.domain.appinfo.model.Contributor
import com.tritiumgaming.shared.mainmenu.domain.appinfo.usecase.GetSpecialThanksUseCase

/**
 * TitleScreenViewModel class
 *
 * @author TritiumGamingStudios
 */
class AppInfoViewModel(
    private val getSpecialThanksUseCase: GetSpecialThanksUseCase
): ViewModel() {

    val specialThanksList: List<Contributor>
        get() = getSpecialThanksUseCase()

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {

                val appKeyContainer =
                    (this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY] as PETApplication).mainMenuContainer

                val getSpecialThanksUseCase: GetSpecialThanksUseCase =
                    appKeyContainer.getSpecialThanksUseCase

                AppInfoViewModel(
                    getSpecialThanksUseCase = getSpecialThanksUseCase
                )

            }
        }
    }

}