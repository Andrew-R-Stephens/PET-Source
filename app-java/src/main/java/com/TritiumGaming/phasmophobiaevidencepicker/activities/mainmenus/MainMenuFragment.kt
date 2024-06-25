package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.TritiumGaming.phasmophobiaevidencepicker.activities.pet.PETFragment
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MainMenuViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared.NewsletterViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus

/**
 * TitleScreenFragment class
 *
 * @author TritiumGamingStudios
 */
abstract class MainMenuFragment : PETFragment {
    protected var mainMenuViewModel: MainMenuViewModel? = null
    protected var newsLetterViewModel: NewsletterViewModel? = null

    constructor() : super()

    constructor(layout: Int) : super(layout)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initViewModels() {
        super.initGlobalPreferencesViewModel()
    }

    protected fun initMainMenuViewModel() {
        if (mainMenuViewModel == null) {
            try { mainMenuViewModel =
                    ViewModelProvider(requireActivity())[MainMenuViewModel::class.java]
            } catch (e: IllegalStateException) { e.printStackTrace() }
        }
    }

    protected fun initNewsletterViewModel() {
        if (newsLetterViewModel == null) {
            try { newsLetterViewModel =
                ViewModelProvider(requireActivity())[NewsletterViewModel::class.java]
            } catch (e: IllegalStateException) { e.printStackTrace() }
        }
    }

    protected fun saveNewsletterViewModel() {
        try {
            newsLetterViewModel?.saveToFile(requireActivity())
        } catch (e: IllegalStateException) { e.printStackTrace() }

    }

    protected fun initAdView(adView: AdView) {
        mainMenuViewModel?.let { mainMenuViewModel ->
            try {
                MobileAds.initialize(requireActivity()) { initializationStatus: InitializationStatus? -> }
                if (!mainMenuViewModel.hasAdRequest()) {
                    mainMenuViewModel.adRequest = AdRequest.Builder().build()
                }
                mainMenuViewModel.adRequest?.let { adRequest -> adView.loadAd(adRequest) }
            } catch (e: IllegalStateException) { e.printStackTrace() }
        }
    }

    override fun backPressedHandler() {
        super.backPressedHandler()
    }

    override fun saveStates() {
        super.saveStates()
        saveNewsletterViewModel()
    }
}