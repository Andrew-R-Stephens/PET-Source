package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.pet.PETFragment
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.MainMenuViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.NewsletterViewModel

abstract class MainMenuFragment : PETFragment {

    protected var mainMenuViewModel: MainMenuViewModel? = null
    protected var newsLetterViewModel: NewsletterViewModel? = null

    constructor() : super()

    constructor(layout: Int) : super(layout)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    protected fun initMainMenuViewModel() {
        try { mainMenuViewModel = mainMenuViewModel ?:
                ViewModelProvider(requireActivity())[MainMenuViewModel::class.java]
        } catch (e: IllegalStateException) { e.printStackTrace() }
    }

    protected fun initNewsletterViewModel() {
        if(newsLetterViewModel == null) {
            try {
                newsLetterViewModel =
                    ViewModelProvider(requireActivity())[NewsletterViewModel::class.java]
                newsLetterViewModel?.init(requireContext())
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
        }
    }

    private fun saveNewsletterViewModel() {
        try { newsLetterViewModel?.saveToFile(requireActivity()) }
        catch (e: IllegalStateException) { e.printStackTrace() }
    }

    protected fun initAdView(adView: AdView) {
        mainMenuViewModel?.let { mainMenuViewModel ->
            try {
                MobileAds.initialize(requireActivity()) { }
                if (!mainMenuViewModel.hasAdRequest()) {
                    mainMenuViewModel.adRequest = AdRequest.Builder().build() }
                mainMenuViewModel.adRequest?.let { adRequest -> adView.loadAd(adRequest) }
            } catch (e: IllegalStateException) { e.printStackTrace() }
        }
    }

    override fun initViewModels() {
        super.initGlobalPreferencesViewModel()
    }

    override fun saveStates() {
        super.saveStates()
        saveNewsletterViewModel()
    }
}