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

    @JvmField
    val animationData: StartScreenAnimationViewData = StartScreenAnimationViewData()

    @JvmField
    var adRequest: AdRequest? = null

    @JvmField
    var canRefreshFragment = true

    @JvmField
    var languageSelectedOriginal: Int = -1

    fun hasAdRequest(): Boolean {
        return adRequest != null
    }
}