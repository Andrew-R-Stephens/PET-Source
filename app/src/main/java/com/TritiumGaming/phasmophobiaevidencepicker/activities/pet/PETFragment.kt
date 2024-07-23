package com.TritiumGaming.phasmophobiaevidencepicker.activities.pet

import android.annotation.SuppressLint
import android.os.Build
import android.widget.PopupWindow
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.PermissionsViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared.GlobalPreferencesViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.utils.NetworkUtils.isNetworkAvailable
import com.google.firebase.analytics.FirebaseAnalytics


abstract class PETFragment : Fragment {

    protected var globalPreferencesViewModel: GlobalPreferencesViewModel? = null
    protected var permissionsViewModel: PermissionsViewModel? = null

    protected var analytics: FirebaseAnalytics? = null
    protected var popupWindow: PopupWindow? = null

    protected constructor()

    protected constructor(layout: Int) : super(layout)

    protected fun init() {
        setOnBackPressed()

        initViewModels()
        initFirebaseAnalytics()
    }

    protected fun initGlobalPreferencesViewModel() {
        if (globalPreferencesViewModel == null) {
            try {
                globalPreferencesViewModel =
                    ViewModelProvider(requireActivity())[GlobalPreferencesViewModel::class.java]
                //globalPreferencesViewModel?.init(requireContext())
            } catch (e: IllegalStateException) { e.printStackTrace() }
        }
    }

    private fun initPermissionsViewModel() {
        if (permissionsViewModel == null) {
            try {
                permissionsViewModel =
                    ViewModelProvider(requireActivity())[PermissionsViewModel::class.java]
            } catch (e: IllegalStateException) { e.printStackTrace()}
        }
    }

    private fun initFirebaseAnalytics() {
        try { this.analytics = (requireActivity() as PETActivity).firebaseAnalytics }
        catch (e: IllegalStateException) { e.printStackTrace() }
    }

    protected fun saveGlobalPreferencesViewModel() {
        try { globalPreferencesViewModel?.saveToFile(requireContext())
        } catch (e: IllegalStateException) { e.printStackTrace() }
    }

    //@SuppressLint("DetachAndAttachSameFragment")
    protected open fun refreshFragment() {
        var ft = parentFragmentManager.beginTransaction()
        if (Build.VERSION.SDK_INT >= 26) { ft.setReorderingAllowed(false) }
        ft.detach(this@PETFragment).commitNow()
        //ft = parentFragmentManager.beginTransaction()
        ft = parentFragmentManager.beginTransaction()
        if (Build.VERSION.SDK_INT >= 26) { ft.setReorderingAllowed(false) }
        ft.attach(this@PETFragment).commitNow()
    }

    protected fun closePopup(): Boolean {
        popupWindow?.let { popupWindow ->
            if (popupWindow.isShowing) {
                popupWindow.dismiss()
                return true
            }
        }

        return false
    }

    protected fun checkInternetConnection(): Boolean {
        return globalPreferencesViewModel?.let { globalPreferencesViewModel ->
            try {
                return (isNetworkAvailable(
                    requireContext(), globalPreferencesViewModel.networkPreference))
            } catch (e: IllegalStateException) {
                e.printStackTrace()
                return false
            }
        } ?:  return false
    }

    protected open fun saveStates() {
        saveGlobalPreferencesViewModel()
    }

    protected open fun backPressedHandler() {
        if (closePopup()) { return }

        try { findNavController(requireView()).popBackStack() }
        catch (e: IllegalStateException) { e.printStackTrace() }
    }

    private fun setOnBackPressed() {
        try {
            requireActivity().onBackPressedDispatcher
                .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() { backPressedHandler() }
                })
        } catch (e: IllegalStateException) { e.printStackTrace() }
    }

    protected abstract fun initViewModels()

}
