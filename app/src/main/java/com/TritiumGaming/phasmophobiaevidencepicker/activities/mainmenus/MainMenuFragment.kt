package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus

import androidx.fragment.app.activityViewModels
import com.TritiumGaming.phasmophobiaevidencepicker.activities.pet.PETFragment
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodel.datastore.ds.NewsletterViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodel.datastore.dsvolatile.MainMenuViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

abstract class MainMenuFragment : PETFragment {

    protected val mainMenuViewModel: MainMenuViewModel by activityViewModels()
    protected val newsLetterViewModel: NewsletterViewModel by activityViewModels()

    constructor() : super()

    constructor(layout: Int) : super(layout)

    protected fun initAdView(adView: AdView) {
        mainMenuViewModel.let { mainMenuViewModel ->
            try {
                MobileAds.initialize(requireActivity()) { }
                if (!mainMenuViewModel.hasAdRequest()) {
                    mainMenuViewModel.adRequest = AdRequest.Builder().build() }
                mainMenuViewModel.adRequest?.let { adRequest -> adView.loadAd(adRequest) }
            } catch (e: IllegalStateException) { e.printStackTrace() }
        }
    }

}