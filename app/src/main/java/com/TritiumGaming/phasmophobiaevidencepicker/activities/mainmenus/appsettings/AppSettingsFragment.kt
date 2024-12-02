package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.appsettings

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
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.Navigation.findNavController
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.MainMenuFirebaseFragment
import com.TritiumGaming.phasmophobiaevidencepicker.activities.pet.PETActivity
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.subsets.ColorThemeHandler
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.subsets.FontThemeHandler
import com.TritiumGaming.phasmophobiaevidencepicker.data.model.settings.themes.ThemeModel
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.transactions.types.FirestoreUnlockHistory
import com.TritiumGaming.phasmophobiaevidencepicker.utils.FormatterUtils.millisToTime
import com.TritiumGaming.phasmophobiaevidencepicker.utils.GoogleMobileAdsConsentManager
import com.TritiumGaming.phasmophobiaevidencepicker.views.global.NavHeaderLayout
import com.TritiumGaming.phasmophobiaevidencepicker.views.global.PETImageButton
import com.google.android.gms.common.SignInButton
import com.google.android.ump.FormError
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot

class AppSettingsFragment : MainMenuFirebaseFragment() {

    private var googleMobileAdsConsentManager: GoogleMobileAdsConsentManager? = null

    private var loadThemes = true

    private var isAlwaysOnToggle: SettingsToggleItemView? = null
    private var networkToggle: SettingsToggleItemView? = null
    private var huntWarningAudioToggle: SettingsToggleItemView? = null
    private var reOrderGhostListToggle: SettingsToggleItemView? = null
    private var enableLeftHandMode: SettingsToggleItemView? = null

    private var seekbar: SeekBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.init()
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("AppSettingsFragment", "Start creating")

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
            manualSignInAccount()
            view.invalidate()
        }

        // SWITCHES

        // Always On Mode
        isAlwaysOnToggle?.isChecked = globalPreferencesViewModel.screenSaverPreference.value
        Log.d("Switches", "screensaver: ${globalPreferencesViewModel.screenSaverPreference.value}")

        // Allow Mobile Data
        networkToggle?.isChecked = globalPreferencesViewModel.networkPreference.value
        Log.d("Switches", "network: ${globalPreferencesViewModel.networkPreference.value}")

        // Allow Left Hand Mode
        enableLeftHandMode?.isChecked = globalPreferencesViewModel.rTLPreference.value
        Log.d("Switches", "rtl: ${globalPreferencesViewModel.rTLPreference.value}")

        // Allow Hunt Warning Audio
        huntWarningAudioToggle?.isChecked = globalPreferencesViewModel.huntWarningAudioPreference.value
        Log.d("Switches", "audio: ${globalPreferencesViewModel.huntWarningAudioPreference.value}")

        // Allow Reorder Ghost Views
        reOrderGhostListToggle?.isChecked = globalPreferencesViewModel.ghostReorderPreference.value
        Log.d("Switches", "reorder: ${globalPreferencesViewModel.ghostReorderPreference.value}")

        view.findViewById<ComposeView>(R.id.tempSwitch)?.setContent {
            LabeledSwitch(
                globalPreferencesViewModel,
                "Temp!",
                false
            )
        }

        // COLORBLIND DATA
        val colorThemesData = globalPreferencesViewModel.colorThemeHandler
        try { colorThemeTextView?.setText(colorThemesData.currentName) }
        catch (e: Exception) { e.printStackTrace() }

        // FONT-STYLE DATA
        val fontThemesData = globalPreferencesViewModel.fontThemeHandler
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
            globalPreferencesViewModel.getHuntWarnTimeoutPreference.value.let { value ->
                if (value < 0) {
                    seekbar.progress = seekbar.max }
                else { seekbar.progress = value.toInt() }
            }

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

        // Screen Always On
        isAlwaysOnToggle?.setSwitchClickListener {
            globalPreferencesViewModel.setScreenSaverPreference(
                isAlwaysOnToggle?.isChecked == true)
        }
        // Allow Mobile Data
        networkToggle?.setSwitchClickListener {
            globalPreferencesViewModel.setNetworkPreference(
                networkToggle?.isChecked == true)

        }
        // Allow Left Hand Mode
        enableLeftHandMode?.setSwitchClickListener {
            globalPreferencesViewModel.setRTLPreference(
                enableLeftHandMode?.isChecked == true)
        }

        // Allow Hunt Warning Audio
        huntWarningAudioToggle?.setSwitchClickListener {
            globalPreferencesViewModel.setHuntWarningAudioPreference(
                huntWarningAudioToggle?.isChecked == true)
        }
        // Allow Ghost View Reordering
        reOrderGhostListToggle?.setSwitchClickListener {
            globalPreferencesViewModel.setGhostReorderPreference(
                reOrderGhostListToggle?.isChecked == true)
        }

        // COLORBLIND LISTENERS
        colorThemePrevButton.setOnClickListener {
            globalPreferencesViewModel.colorThemeHandler.let { themeControl ->
                themeControl.iterateSelectedIndex(-1)
                try { colorThemeTextView?.text = getString(themeControl.currentName) }
                catch (e: Exception) { e.printStackTrace() }
                demoColorStyle(themeControl)
            }
        }

        colorThemeNextButton.setOnClickListener {
            globalPreferencesViewModel.colorThemeHandler.let { themeControl ->
                themeControl.iterateSelectedIndex(1)
                try { colorThemeTextView?.text = getString(themeControl.currentName) }
                catch (e: Exception) { e.printStackTrace() }
                demoColorStyle(themeControl)
            }
        }

        // FONT-STYLE LISTENERS
        fontThemePrevButton.setOnClickListener {
            globalPreferencesViewModel.fontThemeHandler.let { themeControl ->
                themeControl.iterateSelectedIndex(-1)
                try { fontThemeTextView?.text = getString(themeControl.currentName) }
                catch (e: Exception) { e.printStackTrace() }
                demoFontStyle(themeControl)
            }
        }

        fontThemeNextButton.setOnClickListener {
            globalPreferencesViewModel.fontThemeHandler.let { themeControl ->
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
            /*
            val params = Bundle()
            params.putString("event_type", "confirm_settings")
            globalPreferencesViewModel?.dataAsList?.let { settings ->
                for (key in settings.keys) {
                    val value = settings[key]
                    params.putString(key, value)
                }
                analytics?.logEvent("event_settings", params)
            }
            */
            saveStates()

            try { v?.let { view -> findNavController(view).popBackStack() } }
            catch (e: IllegalStateException) { e.printStackTrace() }
        }

        // Include a privacy setting if applicable
        if (googleMobileAdsConsentManager?.isPrivacyOptionsRequired == true) {
            val adsLayout = view.findViewById<View?>(R.id.container_gdpr_settings)
            adsLayout?.visibility = VISIBLE

            val adsButton = view.findViewById<View?>(R.id.button_gdpr_ads)
            adsButton?.setOnClickListener { v: View -> showAdsConsentForm(v.context) }
        }

        val gotoMarketplaceButton = view.findViewById<View?>(R.id.button_marketplace)
        gotoMarketplaceButton?.setOnClickListener{ v ->
            revertDemoChanges()
            try { v?.let { view -> findNavController(view).navigate(R.id.marketplaceFragment) } }
            catch (e: IllegalStateException) { e.printStackTrace() }
        }

        if (loadThemes) {
            loadUserPurchaseHistory()
            //getMarketplaceColorThemes();
            loadThemes = false
        }

        Log.d("AppSettingsFragment", "Finished creating")
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
                            globalPreferencesViewModel.colorThemeHandler.getThemeByUUID(uuid)
                        customTheme.setUnlocked(ThemeModel.Availability.UNLOCKED_PURCHASE)
                    }
                }
                .addOnFailureListener { e: Exception ->
                    Log.e("Firestore", "Could not retrieve unlock history!")
                    e.printStackTrace()
                }
        } catch (e: Exception) { e.printStackTrace() }
    }

    private fun revertDemoChanges() {
        globalPreferencesViewModel.let { globalPreferencesViewModel ->
            globalPreferencesViewModel.colorThemeHandler.revertToSavedIndex()
            globalPreferencesViewModel.fontThemeHandler.revertToSavedIndex()

            try { (requireActivity() as PETActivity).changeTheme(
                    globalPreferencesViewModel.colorThemeHandler.currentTheme,
                globalPreferencesViewModel.fontThemeHandler.currentTheme)
            } catch (e: IllegalStateException) { e.printStackTrace() }
        }
    }

    private fun demoColorStyle(colorThemeHandler: ColorThemeHandler?) {
        globalPreferencesViewModel.let { globalPreferencesViewModel ->
            try { (requireActivity() as PETActivity).changeTheme(
                    colorThemeHandler?.getThemeAtIndex(colorThemeHandler.selectedIndex),
                    globalPreferencesViewModel.fontThemeHandler.currentTheme)
            } catch (e: IllegalStateException) { e.printStackTrace() }
            refreshFragment()
        }
    }

    private fun demoFontStyle(fontThemeHandler: FontThemeHandler) {
        globalPreferencesViewModel.let { globalPreferencesViewModel ->
            try { (requireActivity() as PETActivity).changeTheme(
                    globalPreferencesViewModel.colorThemeHandler.currentTheme,
                    fontThemeHandler.getThemeAtIndex(fontThemeHandler.selectedIndex))
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

    fun saveStates() {
        globalPreferencesViewModel.let { globalPreferencesViewModel ->
            seekbar?.let{ seekbar ->
                globalPreferencesViewModel.setHuntWarnTimeoutPreference(seekbar.progress.toLong())
            }

            globalPreferencesViewModel.fontThemeHandler.saveSelectedIndex()
            globalPreferencesViewModel.colorThemeHandler.saveSelectedIndex()
            globalPreferencesViewModel.saveCurrentColorThemeID()

            /*try { globalPreferencesViewModel.saveAll() }
            catch (e: IllegalStateException) { e.printStackTrace() }*/
            /*try { globalPreferencesViewModel.saveToFile(requireContext()) }
            catch (e: IllegalStateException) { e.printStackTrace() }*/

            try {
                val activity = (requireActivity() as PETActivity)
                activity.changeTheme(
                    globalPreferencesViewModel.colorThemeHandler.currentTheme,
                    globalPreferencesViewModel.fontThemeHandler.currentTheme
                )
                if (globalPreferencesViewModel.screenSaverPreference.value) {
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
            val message = getString(R.string.toast_discardchanges)
            Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
        } catch (e: IllegalStateException) { e.printStackTrace() }
    }

    override fun onSignInAccountSuccess() {
        refreshFragment()

        // Generate a Firestore document for the User with default data if needed
        /*
        try { buildUserDocument() }
        catch (e: Exception) { throw RuntimeException(e) }
        */
        loadUserPurchaseHistory()
    }

    override fun onSignOutAccountSuccess() {
        val themeControl = globalPreferencesViewModel.colorThemeHandler

        themeControl.revertAllUnlockedStatuses()

        themeControl.iterateSelectedIndex(0)
        themeControl.selectedIndex = 0
        themeControl.saveIndex(0)

        globalPreferencesViewModel.saveCurrentColorThemeID()

        demoColorStyle(themeControl)

        refreshFragment()
    }

    override fun onDeleteAccountSuccess() {
        refreshFragment()
    }
}
