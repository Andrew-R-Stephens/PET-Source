package com.tritiumgaming.phasmophobiaevidencepicker.ui.pet

import android.os.Build
import android.util.Log
import android.widget.PopupWindow
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commitNow
import androidx.navigation.Navigation.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.datastore.ds.GlobalPreferencesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.datastore.dsvolatile.PermissionsViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.utils.NetworkUtils.isNetworkAvailable

abstract class PETFragment : Fragment {

    protected val globalPreferencesViewModel: GlobalPreferencesViewModel by activityViewModels()
    protected val permissionsViewModel: PermissionsViewModel by activityViewModels()

    /*
    protected val globalPreferencesViewModel: GlobalPreferencesViewModel by activityViewModels()
    protected val permissionsViewModel: PermissionsViewModel by activityViewModels()
    */

    protected var analytics: FirebaseAnalytics? = null
    protected var popupWindow: PopupWindow? = null

    protected constructor()

    protected constructor(layout: Int) : super(layout)

    protected fun init() {
        Log.d("Fragment", "PET init")
        setOnBackPressed()

        initFirebaseAnalytics()
    }

    private fun initFirebaseAnalytics() {
        try { this.analytics = (requireActivity() as PETActivity).firebaseAnalytics }
        catch (e: IllegalStateException) { e.printStackTrace() }
    }

    protected open fun refreshFragment() {
        try {

            /*
            activity?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    it.overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, 0, 0)
                } else {
                    it.overridePendingTransition(0, 0);
                }
            }
            */

            parentFragmentManager.commitNow {
                if (Build.VERSION.SDK_INT >= 26) { setReorderingAllowed(false) }
                detach(this@PETFragment)
            }
            parentFragmentManager.commitNow {
                if (Build.VERSION.SDK_INT >= 26) { setReorderingAllowed(false) }
                attach(this@PETFragment)
            }

            /*var ft = parentFragmentManager.beginTransaction()
            if (Build.VERSION.SDK_INT >= 26) { ft.setReorderingAllowed(false) }
            ft.detach(this@PETFragment).commitNow()

            ft = parentFragmentManager.beginTransaction()
            if (Build.VERSION.SDK_INT >= 26) { ft.setReorderingAllowed(false) }
            ft.attach(this@PETFragment).commitNow()*/
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
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
        globalPreferencesViewModel.let { globalPreferencesViewModel ->
            try {
                return (isNetworkAvailable(
                    requireContext(), globalPreferencesViewModel.networkPreference.value))
            } catch (e: IllegalStateException) {
                e.printStackTrace()
                return false
            }
        }
    }

    /*
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
    */

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

}
