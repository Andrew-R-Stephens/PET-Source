package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.appsettings

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation.findNavController
import com.google.android.gms.common.SignInButton
import com.google.android.ump.FormError
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore.transactions.user.FirestoreUser.Companion.currentFirebaseUser
import com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore.transactions.user.account.transactions.types.FirestoreUnlockHistory
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.settings.ThemeModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.impl.SignInCredentialManager
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.MainMenuFirebaseFragment
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.pet.PETActivity
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.views.global.NavHeaderLayout
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.views.global.PETImageButton
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.controllers.theming.subsets.ColorThemeControl
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.controllers.theming.subsets.FontThemeControl
import com.tritiumgaming.phasmophobiaevidencepicker.util.FormatterUtils.millisToTime
import com.tritiumgaming.phasmophobiaevidencepicker.util.GoogleMobileAdsConsentManager

class AppSettingsFragment : MainMenuFirebaseFragment() {
    private var googleMobileAdsConsentManager: GoogleMobileAdsConsentManager? = null

    private var loadThemes = true

    private var isAlwaysOnToggle: SettingsToggleItemView? = null
    private var networkToggle: SettingsToggleItemView? = null
    private var huntWarningAudioToggle: SettingsToggleItemView? = null
    private var reOrderGhostListToggle: SettingsToggleItemView? = null
    private var enableLeftHandMode: SettingsToggleItemView? = null

    var seekbar: SeekBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.init()
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHeaderLayout = view.findViewById<NavHeaderLayout>(R.id.navHeaderLayout)
        val cancelButton = navHeaderLayout.findViewById<View>(R.id.button_left)
        val confirmButton = navHeaderLayout.findViewById<View>(R.id.button_right)

        isAlwaysOnToggle = view.findViewById(R.id.toggle_alwaysOn)
        networkToggle = view.findViewById(R.id.toggle_network)
        enableLeftHandMode = view.findViewById(R.id.toggle_leftHandMode)
        reOrderGhostListToggle = view.findViewById(R.id.toggle_reorderGhostViews)
        huntWarningAudioToggle = view.findViewById(R.id.toggle_huntwarningaudio)

        val accountLoginButton =
            view.findViewById<SignInButton>(R.id.settings_account_login_button)

        val clockTimeTextView =
            view.findViewById<AppCompatTextView?>(R.id.seekbar_huntwarningtimeout_timetext)
        val clockOtherTextView =
            view.findViewById<AppCompatTextView?>(R.id.seekbar_huntwarningtimeout_othertext)
        seekbar = view.findViewById(R.id.settings_huntwarning_seekbar)

        val colorThemeTextView =
            view.findViewById<AppCompatTextView?>(R.id.colorblindmode_selectedname)
        val fontThemeTextView = view.findViewById<AppCompatTextView?>(R.id.font_selectedname)

        val colorThemePrevButton =
            view.findViewById<PETImageButton?>(R.id.colorblindmode_leftbutton)
        val colorThemeNextButton =
            view.findViewById<PETImageButton?>(R.id.colorblindmode_rightbutton)

        val fontThemePrevButton = view.findViewById<PETImageButton?>(R.id.font_leftbutton)
        val fontThemeNextButton = view.findViewById<PETImageButton?>(R.id.font_rightbutton)

        try { googleMobileAdsConsentManager = GoogleMobileAdsConsentManager(requireActivity()) }
        catch (e: IllegalStateException) { e.printStackTrace() }

        accountLoginButton?.setOnClickListener {
            signIn(
                requireActivity(),
                SignInCredentialManager.SignInOptions.GOOGLE,
                onSuccess = {

                    createUserDocument(
                        onSuccess = {
                            currentFirebaseUser?.let { user ->
                                val message =
                                    "${requireActivity().getString(com.tritiumgaming.core.resources.R.string.alert_account_welcome)} ${user.displayName}"
                                val toast = Toast.makeText(activity, message, Toast.LENGTH_LONG)
                                toast.show()
                            }

                            view.invalidate()
                        }
                    )

                }
            )
        }

        // SWITCHES
        // Screen Always On
        globalPreferencesViewModel?.let { globalPreferencesViewModel ->
            // Always On Mode
            isAlwaysOnToggle?.isChecked = globalPreferencesViewModel.isAlwaysOn

            // Allow Mobile Data
            networkToggle?.isChecked = globalPreferencesViewModel.networkPreference

            // Allow Left Hand Mode
            enableLeftHandMode?.isChecked = globalPreferencesViewModel.isLeftHandSupportEnabled

            // Allow Hunt Warning Audio
            huntWarningAudioToggle?.isChecked = globalPreferencesViewModel.isHuntWarnAudioAllowed.value

            // Allow Reorder Ghost Views
            reOrderGhostListToggle?.isChecked = globalPreferencesViewModel.reorderGhostViews


            // COLORBLIND DATA
            val colorThemesData = globalPreferencesViewModel.colorThemeControl
            try { colorThemeTextView?.setText(colorThemesData.currentName) }
            catch (e: Exception) { e.printStackTrace() }

            // FONT-STYLE DATA
            val fontThemesData = globalPreferencesViewModel.fontThemeControl
            try { fontThemeTextView?.setText(fontThemesData.currentName) }
            catch (e: Exception) { e.printStackTrace() }

            val showClockTime: (Boolean) -> Unit = { showTime: Boolean ->
                when(showTime) {
                    true -> {
                        clockTimeTextView.visibility = VISIBLE
                        clockOtherTextView.visibility = GONE
                    }
                    false -> {
                        clockTimeTextView.visibility = GONE
                        clockOtherTextView.visibility = VISIBLE
                    }
                }
            }

            // Hunt warning timeout setting
            seekbar?.let { seekbar ->
                val seconds = 300
                val millisPerSecond = 1000
                seekbar.max = (seconds * millisPerSecond) + 1
                if (globalPreferencesViewModel.huntWarnFlashTimeMax.value < 0) {
                    seekbar.progress = seekbar.max }
                else { seekbar.progress = globalPreferencesViewModel.huntWarnFlashTimeMax.value.toInt() }

                seekbar.setOnSeekBarChangeListener(
                    object : OnSeekBarChangeListener {
                        override fun onProgressChanged(
                            seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                            if (fromUser) {
                                val progressMax =
                                    (seconds * millisPerSecond) / seekbar.max.toDouble()

                                if (progress < seekbar.max) {
                                    val breakdown = (progressMax * progress / 1000L).toLong()
                                    val text = millisToTime("%sm %ss", breakdown)
                                    clockTimeTextView.text = text
                                    showClockTime(true)
                                } else if (progress == seekbar.max) {
                                    showClockTime(false)
                                }
                            }
                        }
                        override fun onStartTrackingTouch(seekBar: SeekBar) { }
                        override fun onStopTrackingTouch(seekBar: SeekBar) { }
                    })

                val progressMax = 300000 / seekbar.max.toDouble()

                if (seekbar.progress >= 0 && seekbar.progress < seekbar.max) {
                    val breakdown = (progressMax * seekbar.progress / 1000L).toLong()
                    val text = millisToTime("%sm %ss", breakdown)
                    showClockTime(true)
                    clockTimeTextView.text = text
                } else { showClockTime(false) }
            }
        }

        // Screen Always On
        isAlwaysOnToggle?.setSwitchClickListener {
            globalPreferencesViewModel?.isAlwaysOn =
                isAlwaysOnToggle?.isChecked == true
        }
        // Allow Mobile Data
        networkToggle?.setSwitchClickListener {
            globalPreferencesViewModel?.networkPreference =
                networkToggle?.isChecked == true

        }
        // Allow Left Hand Mode
        enableLeftHandMode?.setSwitchClickListener {
            globalPreferencesViewModel?.isLeftHandSupportEnabled =
                enableLeftHandMode?.isChecked == true
        }

        // Allow Hunt Warning Audio
        huntWarningAudioToggle?.setSwitchClickListener {
            globalPreferencesViewModel?.setHuntWarnAudioAllowed(
                huntWarningAudioToggle?.isChecked == true)
        }
        // Allow Ghost View Reordering
        reOrderGhostListToggle?.setSwitchClickListener {
            globalPreferencesViewModel?.reorderGhostViews =
                reOrderGhostListToggle?.isChecked == true
        }

        // COLORBLIND LISTENERS
        colorThemePrevButton.setOnClickListener {
            globalPreferencesViewModel?.colorThemeControl?.let { themeControl ->
                themeControl.iterateSelectedIndex(-1)
                try { colorThemeTextView?.text = getString(themeControl.currentName) }
                catch (e: Exception) { e.printStackTrace() }
                demoColorStyle(themeControl)
            }
        }

        colorThemeNextButton.setOnClickListener {
            globalPreferencesViewModel?.colorThemeControl?.let { themeControl ->
                themeControl.iterateSelectedIndex(1)
                try { colorThemeTextView?.text = getString(themeControl.currentName) }
                catch (e: Exception) { e.printStackTrace() }
                demoColorStyle(themeControl)
            }
        }

        // FONT-STYLE LISTENERS
        fontThemePrevButton.setOnClickListener {
            globalPreferencesViewModel?.fontThemeControl?.let { themeControl ->
                themeControl.iterateSelectedIndex(-1)
                try { fontThemeTextView?.text = getString(themeControl.currentName) }
                catch (e: Exception) { e.printStackTrace() }
                demoFontStyle(themeControl)
            }
        }

        fontThemeNextButton.setOnClickListener {
            globalPreferencesViewModel?.fontThemeControl?.let { themeControl ->
                themeControl.iterateSelectedIndex(1)
                try { fontThemeTextView?.text = getString(themeControl.currentName) }
                catch (e: Exception) { e.printStackTrace() }
                demoFontStyle(themeControl)
            }
        }

        // CANCEL BUTTON
        cancelButton.setOnClickListener { v: View? ->
            revertDemoChanges()
            try { v?.let { view -> findNavController(view).popBackStack() } }
            catch (e: IllegalStateException) { e.printStackTrace() }
        }

        // CONFIRM BUTTON
        confirmButton.setOnClickListener { v: View? ->
            val params = Bundle()
            params.putString("event_type", "confirm_settings")
            globalPreferencesViewModel?.dataAsList?.let { settings ->
                for (key in settings.keys) {
                    val value = settings[key]
                    params.putString(key, value)
                }
                analytics?.logEvent("event_settings", params)
            }

            saveStates()
            try { v?.let { view -> findNavController(view).popBackStack() } }
            catch (e: IllegalStateException) { e.printStackTrace() }
        }

        // Include a privacy setting if applicable
        if (googleMobileAdsConsentManager?.isPrivacyOptionsRequired == true) {
            val adsLayout = view.findViewById<ConstraintLayout>(R.id.constraintLayout_ads)
            adsLayout.visibility = VISIBLE

            val adsButton = view.findViewById<AppCompatButton>(R.id.settings_ads_label)
            adsButton.setOnClickListener { v: View -> showAdsConsentForm(v.context) }
        }

        if (loadThemes) {
            loadUserPurchaseHistory()
            //getMarketplaceColorThemes();
            loadThemes = false
        }

    }

    private fun loadUserPurchaseHistory() {
        var unlockHistoryCollection: CollectionReference? = null
        try { unlockHistoryCollection = FirestoreUnlockHistory.unlockHistoryCollection }
        catch (e: Exception) { e.printStackTrace() }

        if (unlockHistoryCollection == null) { return }

        try {
            unlockHistoryCollection.get()
                .addOnSuccessListener { task: QuerySnapshot ->
                    for (documentSnapshot in task.documents) {
                        val documentReference = documentSnapshot.reference

                        val uuid = documentReference.id
                        val customTheme =
                            globalPreferencesViewModel?.colorThemeControl?.getThemeByUUID(uuid)
                        customTheme?.setUnlocked(ThemeModel.Availability.UNLOCKED_PURCHASE)
                    }
                }
                .addOnFailureListener { e: Exception ->
                    Log.e("Firestore", "Could not retrieve unlock history!")
                    e.printStackTrace()
                }
        } catch (e: Exception) { e.printStackTrace() }
    }

    private fun revertDemoChanges() {
        globalPreferencesViewModel?.let { globalPreferencesViewModel ->
            globalPreferencesViewModel.colorThemeControl.revertToSavedIndex()
            globalPreferencesViewModel.fontThemeControl.revertToSavedIndex()

            try { (requireActivity() as PETActivity).changeTheme(
                    globalPreferencesViewModel.colorTheme, globalPreferencesViewModel.fontTheme)
            } catch (e: IllegalStateException) { e.printStackTrace() }
        }
    }

    private fun demoColorStyle(colorThemeControl: ColorThemeControl?) {
        globalPreferencesViewModel?.let { globalPreferencesViewModel ->
            try { (requireActivity() as PETActivity).changeTheme(
                    colorThemeControl?.getThemeAtIndex(colorThemeControl.selectedIndex),
                    globalPreferencesViewModel.fontTheme)
            } catch (e: IllegalStateException) { e.printStackTrace() }
            refreshFragment()
        }
    }

    private fun demoFontStyle(fontThemeControl: FontThemeControl) {
        globalPreferencesViewModel?.let { globalPreferencesViewModel ->
            try { (requireActivity() as PETActivity).changeTheme(
                    globalPreferencesViewModel.colorTheme,
                    fontThemeControl.getThemeAtIndex(fontThemeControl.selectedIndex))
            } catch (e: IllegalStateException) { e.printStackTrace() }
            refreshFragment()
        }
    }

    private fun showAdsConsentForm(context: Context?) {
        // Handle changes to user consent.
        try { googleMobileAdsConsentManager?.showPrivacyOptionsForm(requireActivity()) {
                formError: FormError? -> formError?.let {
                Toast.makeText(context, formError.message, Toast.LENGTH_SHORT).show() }
            }
            Log.d("AdsConsent", "should show consent form")
        } catch (e: IllegalStateException) { e.printStackTrace() }
    }

    public override fun saveStates() {
        globalPreferencesViewModel?.let { globalPreferencesViewModel ->
            seekbar?.let{ seekbar ->
                globalPreferencesViewModel.setHuntWarningFlashTimeMax(seekbar.progress.toLong())
            }

            globalPreferencesViewModel.fontThemeControl.saveSelectedIndex()
            globalPreferencesViewModel.colorThemeControl.saveSelectedIndex()

            try { globalPreferencesViewModel.saveToFile(requireContext()) }
            catch (e: IllegalStateException) { e.printStackTrace() }

            try {
                val activity = (requireActivity() as PETActivity)
                activity.changeTheme(
                    globalPreferencesViewModel.colorTheme, globalPreferencesViewModel.fontTheme
                )
                if (globalPreferencesViewModel.isAlwaysOn) {
                    activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                }
                activity.recreate()
            } catch (e: IllegalStateException) { e.printStackTrace() }
        }
    }

    override fun backPressedHandler() {
        revertDemoChanges()

        try { findNavController(requireView()).popBackStack() }
        catch (e: IllegalStateException) { e.printStackTrace() }

        try {
            val message = getString(com.tritiumgaming.core.resources.R.string.toast_discardchanges)
            Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
        } catch (e: IllegalStateException) { e.printStackTrace() }
    }

    /*
    override fun onSignInAccountSuccess() {
        refreshFragment()

        loadUserPurchaseHistory()
    }

    override fun onSignOutAccountSuccess() {
        val themeControl = globalPreferencesViewModel?.colorThemeControl

        themeControl?.revertAllUnlockedStatuses()

        themeControl?.iterateSelectedIndex(0)
        themeControl?.selectedIndex = 0
        themeControl?.saveIndex(0)

        globalPreferencesViewModel?.saveColorSpace(requireContext())

        demoColorStyle(themeControl)

        refreshFragment()
    }

    override fun onDeleteAccountSuccess() {
        refreshFragment()
    }
    */
}
