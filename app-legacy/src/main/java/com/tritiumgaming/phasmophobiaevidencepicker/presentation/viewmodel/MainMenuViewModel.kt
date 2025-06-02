package com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.ads.AdRequest
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.startscreen.AnimationModel

/**
 * TitleScreenViewModel class
 *
 * @author TritiumGamingStudios
 */
class MainMenuViewModel(application: Application): AndroidViewModel(application) {

    val animationModel: AnimationModel = AnimationModel()
    var canRefreshFragment = true
    var languageSelectedOriginal: Int = -1

    var adRequest: AdRequest? = null

    fun hasAdRequest(): Boolean {
        return adRequest != null
    }
}