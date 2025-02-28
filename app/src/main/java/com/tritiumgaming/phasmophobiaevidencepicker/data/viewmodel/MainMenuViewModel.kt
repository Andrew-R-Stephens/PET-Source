package com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.AdRequest
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.startscreen.AnimationModel

/**
 * TitleScreenViewModel class
 *
 * @author TritiumGamingStudios
 */
class MainMenuViewModel: ViewModel() {

    val animationModel: AnimationModel = AnimationModel()
    var canRefreshFragment = true
    var languageSelectedOriginal: Int = -1

    var adRequest: AdRequest? = null

    fun hasAdRequest(): Boolean {
        return adRequest != null
    }

    class MainMenuFactory : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainMenuViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainMenuViewModel(

                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}