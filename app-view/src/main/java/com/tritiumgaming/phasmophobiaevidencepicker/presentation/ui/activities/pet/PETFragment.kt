package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.pet

import android.os.Build
import android.widget.PopupWindow
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.PermissionsViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.GlobalPreferencesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.util.NetworkUtils.isNetworkAvailable
import com.google.firebase.analytics.FirebaseAnalytics
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.impl.AccountManagementService


abstract class PETFragment : Fragment, AccountManagementService {

    /*protected var globalPreferencesViewModel: GlobalPreferencesViewModel? = null
    protected var permissionsViewModel: PermissionsViewModel? = null*/

    protected val globalPreferencesViewModel: GlobalPreferencesViewModel by activityViewModels { GlobalPreferencesViewModel.Factory }
    protected val permissionsViewModel: PermissionsViewModel by activityViewModels()

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
        globalPreferencesViewModel.init(requireContext())
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
