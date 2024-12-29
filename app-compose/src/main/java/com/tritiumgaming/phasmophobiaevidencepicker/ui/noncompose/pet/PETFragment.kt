package com.tritiumgaming.phasmophobiaevidencepicker.ui.noncompose.pet

import android.os.Build
import android.widget.PopupWindow
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commitNow
import androidx.navigation.Navigation.findNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.datastore.viewmodel.PermissionsViewModel

abstract class PETFragment : Fragment {

    protected val globalPreferencesViewModel: com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.GlobalPreferencesViewModel by activityViewModels()
    protected val permissionsViewModel: PermissionsViewModel by activityViewModels()

    protected var analytics: FirebaseAnalytics? = null
    protected var popupWindow: PopupWindow? = null

    protected constructor()

    protected constructor(layout: Int) : super(layout)

    protected fun init() {
        setOnBackPressed()

        initFirebaseAnalytics()
    }

    // Done
    private fun initFirebaseAnalytics() {
        try { this.analytics = (requireActivity() as PETActivity).firebaseAnalytics }
        catch (e: IllegalStateException) { e.printStackTrace() }
    }

    protected open fun refreshFragment() {
        try {
            parentFragmentManager.commitNow {
                if (Build.VERSION.SDK_INT >= 26) { setReorderingAllowed(false) }
                detach(this@PETFragment)
            }
            parentFragmentManager.commitNow {
                if (Build.VERSION.SDK_INT >= 26) { setReorderingAllowed(false) }
                attach(this@PETFragment)
            }
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
