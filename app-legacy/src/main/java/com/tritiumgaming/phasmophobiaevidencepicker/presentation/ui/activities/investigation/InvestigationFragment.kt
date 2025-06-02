package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.pet.PETFragment
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.InvestigationViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.MapMenuViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.ObjectivesViewModel

abstract class InvestigationFragment : PETFragment {
    protected var investigationViewModel: InvestigationViewModel? = null
    protected var objectivesViewModel: ObjectivesViewModel? = null
    protected var mapMenuViewModel: MapMenuViewModel? = null

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
        initObjectivesViewModel()
        initMapMenuViewModel()
    }

    private fun initMapMenuViewModel() {
        if (mapMenuViewModel == null) {
            mapMenuViewModel = ViewModelProvider(requireActivity())[MapMenuViewModel::class.java]
            mapMenuViewModel?.init()
        }
    }

    private fun initObjectivesViewModel() {
        if (objectivesViewModel == null) {
            objectivesViewModel =
                ViewModelProvider(requireActivity())[ObjectivesViewModel::class.java]
            objectivesViewModel?.init()
        }
    }

    private fun initInvestigationViewModel() {
        if (investigationViewModel == null) {
            investigationViewModel =
                ViewModelProvider(requireActivity())[InvestigationViewModel::class.java]
            investigationViewModel?.init()
        }
        investigationViewModel?.phaseWarnModel?.audioAllowed =
            globalPreferencesViewModel?.isHuntWarnAudioAllowed?.value == true
        globalPreferencesViewModel?.huntWarnFlashTimeMax?.value?.let { value ->
            investigationViewModel?.phaseWarnModel?.flashTimeMax = value
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

