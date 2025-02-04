package com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.android.gms.ads.AdRequest
import com.tritiumgaming.phasmophobiaevidencepicker.app.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.AppInfoRepository
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.startscreen.AnimationModel

/**
 * TitleScreenViewModel class
 *
 * @author TritiumGamingStudios
 */
class MainMenuViewModel(
    val appInfoRepository: AppInfoRepository
): ViewModel() {

    val animationModel: AnimationModel = AnimationModel()

    @Deprecated("Unused in Composables")
    var canRefreshFragment = true
    @Deprecated("Unused in Composables")
    var languageSelectedOriginal: Int = -1

    var adRequest: AdRequest? = null

    fun hasAdRequest(): Boolean {
        return adRequest != null
    }

    class MainMenuFactory(
        private val appInfoRepository: AppInfoRepository
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
                //val savedStateHandle = createSavedStateHandle()
                val appInfoRepository = (this[APPLICATION_KEY] as PETApplication).container.appInfoRepository
                MainMenuViewModel(
                    appInfoRepository = appInfoRepository
                )
            }
        }
    }

}