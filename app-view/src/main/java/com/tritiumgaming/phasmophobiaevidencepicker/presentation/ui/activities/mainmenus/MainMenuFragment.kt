package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.pet.PETFragment
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.MainMenuViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.NewsletterViewModel

abstract class MainMenuFragment : PETFragment {

    protected val mainMenuViewModel: MainMenuViewModel by activityViewModels { MainMenuViewModel.Factory }
    protected val newsLetterViewModel: NewsletterViewModel by activityViewModels { NewsletterViewModel.Factory }

    constructor() : super()

    constructor(layout: Int) : super(layout)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    protected fun initAdView(adView: AdView) {
        mainMenuViewModel.let { mainMenuViewModel ->
            try {
                MobileAds.initialize(requireActivity()) { }
                mainMenuViewModel.adRequest ?: {
                    mainMenuViewModel.adRequest = AdRequest.Builder().build().also {
                            adView.loadAd(it) }
                }

            } catch (e: IllegalStateException) { e.printStackTrace() }
        }
    }

}