package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.viewmodel.mainmenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.android.gms.ads.AdRequest
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.appinfo.repository.AppInfoRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.startscreen.AnimationModel

/**
 * TitleScreenViewModel class
 *
 * @author TritiumGamingStudios
 */
class MainMenuViewModel(
    val appInfoRepository: AppInfoRepositoryImpl
): ViewModel() {

    val animationModel: AnimationModel = AnimationModel()

    var adRequest: AdRequest? = null

    val specialThanksList = appInfoRepository.getSpecialThanks()

    @Deprecated("Unused in Composables")
    var canRefreshFragment = true
    @Deprecated("Unused in Composables")
    var languageSelectedOriginal: Int = -1

    fun hasAdRequest(): Boolean {
        return adRequest != null
    }

    class MainMenuFactory(
        private val appInfoRepository: AppInfoRepositoryImpl
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainMenuViewModel::class.java)) {
                val viewModel =
                    MainMenuViewModel(
                        appInfoRepository
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

                val appInfoRepository: AppInfoRepositoryImpl = appKeyContainer.appInfoRepository

                MainMenuViewModel(
                    appInfoRepository = appInfoRepository
                )

            }
        }
    }

}