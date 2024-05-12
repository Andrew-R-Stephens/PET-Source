package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels

import androidx.lifecycle.ViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.StartScreenAnimationViewData
import com.google.android.gms.ads.AdRequest

/**
 * TitleScreenViewModel class
 *
 * @author TritiumGamingStudios
 */
class MainMenuViewModel : ViewModel() {

    val animationData: StartScreenAnimationViewData = StartScreenAnimationViewData()
    var canRefreshFragment = true
    var languageSelectedOriginal: Int = -1

    var adRequest: AdRequest? = null

    fun hasAdRequest(): Boolean {
        return adRequest != null
    }
}