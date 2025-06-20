package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.viewmodel.mainmenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.android.gms.ads.AdRequest
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.appinfo.usecase.GetSpecialThanksUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.startscreen.model.AnimationModel

/**
 * TitleScreenViewModel class
 *
 * @author TritiumGamingStudios
 */
class MainMenuViewModel(
    private val getSpecialThanksUseCase: GetSpecialThanksUseCase
): ViewModel() {

    val animationModel: AnimationModel = AnimationModel()

    var adRequest: AdRequest? = null

    val specialThanksList = getSpecialThanksUseCase()

    fun hasAdRequest(): Boolean {
        return adRequest != null
    }

    class MainMenuFactory(
        private val getSpecialThanksUseCase: GetSpecialThanksUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainMenuViewModel::class.java)) {
                val viewModel =
                    MainMenuViewModel(
                        getSpecialThanksUseCase = getSpecialThanksUseCase
                    )
                /*viewModel.init()*/
                @Suppress("UNCHECKED_CAST")
                return (viewModel)as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {

                val appKeyContainer =
                    (this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY] as PETApplication).mainMenuContainer

                val getSpecialThanksUseCase: GetSpecialThanksUseCase = appKeyContainer.getSpecialThanksUseCase

                MainMenuViewModel(
                    getSpecialThanksUseCase = getSpecialThanksUseCase
                )

            }
        }
    }

}