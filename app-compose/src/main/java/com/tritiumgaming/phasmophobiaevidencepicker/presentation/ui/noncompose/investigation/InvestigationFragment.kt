package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.noncompose.investigation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.noncompose.pet.PETFragment
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.InvestigationViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.MapMenuViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.ObjectivesViewModel

@Deprecated("Migrate to Jetpack Compose")
abstract class InvestigationFragment : PETFragment {
    protected val investigationViewModel: InvestigationViewModel by activityViewModels()
    protected val objectivesViewModel: ObjectivesViewModel by activityViewModels()
    protected val mapMenuViewModel: MapMenuViewModel by activityViewModels()

    /*protected var investigationViewModel: InvestigationViewModel? = null
    protected var objectivesViewModel: ObjectivesViewModel? = null
    protected var mapMenuViewModel: MapMenuViewModel? = null*/

    protected var adRequest: AdRequest? = null

    constructor() : super()

    constructor(layout: Int) : super(layout)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.init()

        try { (requireActivity() as InvestigationActivity).initNavigationComponents()
        } catch (e: IllegalStateException) { e.printStackTrace() }

        initAd(view.findViewById(R.id.adView))
    }

    fun initViewModels() {
        initInvestigationViewModel()
    }

    private fun initInvestigationViewModel() {
        investigationViewModel.phaseWarnModel?.audioAllowed =
            globalPreferencesViewModel.huntWarningAudioPreference.value == true

        globalPreferencesViewModel.getHuntWarnTimeoutPreference.value.let { value ->
            investigationViewModel.phaseWarnModel?.flashTimeMax = value
        }
    }

    protected fun initAd(mAdView: AdView?) {
        mAdView?.let {
            MobileAds.initialize(requireContext()) { }
            adRequest = AdRequest.Builder().build()
            adRequest?.let { adRequest -> mAdView.loadAd(adRequest) }
        }
    }

    override fun backPressedHandler() {
        try {
            if ((requireActivity() as InvestigationActivity).closeNavigationDrawer()) { return }
        } catch (e: IllegalStateException) { e.printStackTrace() }

        if (popupWindow?.isShowing == true) {
            popupWindow?.dismiss()
            return
        }

        Log.d("Backstack", "Popping")
        try {
            if (!findNavController(requireView()).popBackStack()) {
                Log.d("Backstack", "Could not Pop")
                requireActivity().finish()
            }
        } catch (e: IllegalStateException) { e.printStackTrace() }
    }

    abstract fun reset()

}

