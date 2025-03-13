package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation.findNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.InvestigationViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.MapViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.ObjectivesViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.pet.PETFragment

abstract class InvestigationFragment : PETFragment {

    protected val investigationViewModel: InvestigationViewModel by activityViewModels { InvestigationViewModel.Factory }
    protected val objectivesViewModel: ObjectivesViewModel by activityViewModels { ObjectivesViewModel.Factory }
    protected val mapViewModel: MapViewModel by activityViewModels { MapViewModel.Factory }

    protected var adRequest: AdRequest? = null

    constructor() : super()

    constructor(layout: Int) : super(layout)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.init()

        try { (requireActivity() as InvestigationActivity).initNavigationComponents()
        } catch (e: IllegalStateException) { e.printStackTrace() }

        initAd(view.findViewById(R.id.adView))
    }

    override fun initViewModels() {
        initGlobalPreferencesViewModel()
        initInvestigationViewModel()
    }

    private fun initInvestigationViewModel() {
        investigationViewModel.phaseWarnModel?.audioAllowed =
            globalPreferencesViewModel.isHuntWarnAudioAllowed.value == true
        globalPreferencesViewModel.huntWarnFlashTimeMax.value.let { value ->
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

