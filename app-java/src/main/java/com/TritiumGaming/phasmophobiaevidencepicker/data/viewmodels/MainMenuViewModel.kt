package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels

import androidx.lifecycle.ViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.data.animations.graphicsdata.AnimatedGraphicData
import com.google.android.gms.ads.AdRequest

/**
 * TitleScreenViewModel class
 *
 * @author TritiumGamingStudios
 */
class MainMenuViewModel : ViewModel() {

    /** @return animationData */
    @JvmField
    val animationData: AnimatedGraphicData = AnimatedGraphicData()

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