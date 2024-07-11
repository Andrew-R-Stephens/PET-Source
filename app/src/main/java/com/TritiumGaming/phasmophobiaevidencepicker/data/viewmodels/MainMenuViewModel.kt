package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.startscreen.AnimationModel
import com.google.android.gms.ads.AdRequest

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