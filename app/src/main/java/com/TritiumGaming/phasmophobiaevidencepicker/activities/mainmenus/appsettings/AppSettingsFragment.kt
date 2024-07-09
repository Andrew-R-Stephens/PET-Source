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
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation.findNavController
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.MainMenuFirebaseFragment
import com.TritiumGaming.phasmophobiaevidencepicker.activities.pet.PETActivity
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.subsets.ColorThemeControl
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.subsets.FontThemeControl
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.settings.ThemeModel
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser.Companion.buildUserDocument
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.transactions.types.FirestoreUnlockHistory
import com.TritiumGaming.phasmophobiaevidencepicker.utils.FormatterUtils.millisToTime
import com.TritiumGaming.phasmophobiaevidencepicker.utils.GoogleMobileAdsConsentManager
import com.TritiumGaming.phasmophobiaevidencepicker.views.global.NavHeaderLayout
import com.TritiumGaming.phasmophobiaevidencepicker.views.global.PETImageButton
import com.google.android.ump.FormError
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot

class AppSettingsFragment : MainMenuFirebaseFragment() {
    private var googleMobileAdsConsentManager: GoogleMobileAdsConsentManager? = null

    private val showEmail = false
    private var loadThemes = true

    private var isAlwaysOnToggle: SettingsToggleItemView? = null
    private var networkToggle: SettingsToggleItemView? = null
    private var huntWarningAudioToggle: SettingsToggleItemView? = null
    private var reOrderGhostListToggle: SettingsToggleItemView? = null
    private var enableLeftHandMode: SettingsToggleItemView? = null

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

        val accountLoginButton =
            view.findViewById<AppCompatButton>(R.id.settings_account_login_button)
        /*final AppCompatButton btn_account_logout =
                view.findViewById(R.id.settings_account_logout_button);
        final PETImageButton btn_account_delete =
                view.findViewById(R.id.settings_account_delete_button);*/
        val btn_account_infoContainer =
            view.findViewById<ConstraintLayout?>(R.id.constraintLayout_accountInformation)
        val btn_account_info =
            view.findViewById<AppCompatTextView?>(R.id.settings_accountsettings_info)

        val clockTimeTextView =
            view.findViewById<AppCompatTextView?>(R.id.seekbar_huntwarningtimeout_timetext)
        val clockOtherTextView =
            view.findViewById<AppCompatTextView?>(R.id.seekbar_huntwarningtimeout_othertext)

        isAlwaysOnToggle = view.findViewById(R.id.toggle_alwaysOn)
        networkToggle = view.findViewById(R.id.toggle_network)
        enableLeftHandMode = view.findViewById(R.id.toggle_leftHandMode)
        reOrderGhostListToggle = view.findViewById(R.id.toggle_reorderGhostViews)
        huntWarningAudioToggle = view.findViewById(R.id.toggle_huntwarningaudio)

        val seekbar = view.findViewById<SeekBar?>(R.id.settings_huntwarning_seekbar)

        val colorThemeTextView =
            view.findViewById<AppCompatTextView?>(R.id.colorblindmode_selectedname)
        val fontThemeTextView = view.findViewById<AppCompatTextView?>(R.id.font_selectedname)

        val colorThemePrevButton = view.findViewById<PETImageButton?>(R.id.colorblindmode_leftbutton)
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

        /*if(btn_account_logout != null) {
            btn_account_logout.setOnClickListener(v -> {
                signOutAccount();

                view.invalidate();
            });
        }
        if(btn_account_delete != null) {
            btn_account_delete.setOnClickListener(v -> {
                deleteAccount();

                view.invalidate();
            });
        }*/

        /*
        initAccountView(
                btn_account_login, btn_account_logout, btn_account_delete,
                btn_account_infoContainer, btn_account_info);
        */

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
                        clockOtherTextView.visibility = VISIBLE
                        clockTimeTextView.visibility = GONE
                    }
                }
            }

            // Hunt warning timeout setting
            seekbar?.let {
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
                                globalPreferencesViewModel.setHuntWarningFlashTimeMax(progress.toLong())

                                val progressMax =
                                    (seconds * millisPerSecond) / seekbar.max.toDouble()

                                if (progress < seekbar.max) {
                                    val breakdown = (progressMax * progress / 1000L).toLong()
                                    val text = millisToTime("%sm %ss", breakdown)
                                    clockTimeTextView.text = text
                                    showClockTime(true)
                                } else if (progress == seekbar.max) {
                                    showClockTime(false)
                                    clockOtherTextView.setText(R.string.settings_huntwarningflashtimeout_never)
                                }
                            }
                        }
                        override fun onStartTrackingTouch(seekBar: SeekBar) { }
                        override fun onStopTrackingTouch(seekBar: SeekBar) { }
                    })

                val progressMax = 300000 / seekbar.max.toDouble()

                if (seekbar.progress >= 0 && seekbar.progress < seekbar.max) {
                    val breakdown =
                        (progressMax * seekbar.progress / 1000L).toLong()
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

        /*

        if(btn_account_delete != null) {
            btn_account_delete.setVisibility(View.GONE);
        }
        */
    }

    /*
    private void initAccountView(AppCompatButton btn_account_login, AppCompatButton btn_account_logout, AppCompatButton btn_account_delete, ConstraintLayout btn_account_infoContainer, AppCompatTextView btn_account_info) {
        String accountEmail = "";
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null) {
            accountEmail = firebaseUser.getEmail();
        }
        SpannableString email_displayed =
                new SpannableString(accountEmail);

        TypedValue typedValue = new TypedValue();
        @ColorInt int obfuscationColor = getResources().getColor(R.color.white);
        try {
            Resources.Theme theme = requireContext().getTheme();
            theme.resolveAttribute(R.attr.textColorBodyEmphasis, typedValue, true);
            obfuscationColor = typedValue.data;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        SpannableString email_obfuscated = email_displayed;
        if(accountEmail != null) {
            email_obfuscated = FormatterUtils.obfuscateEmailSpannable(
                    accountEmail, obfuscationColor);
        }
        SpannableString finalEmail_obfuscated = email_obfuscated;

        btn_account_infoContainer.setOnClickListener(v -> {
            showEmail = !showEmail;

            if (showEmail) {
                btn_account_info.setText(email_displayed);
            } else {
                btn_account_info.setText(finalEmail_obfuscated);
            }
        });

        if(firebaseUser == null) {
            btn_account_login.setVisibility(View.VISIBLE);
            btn_account_logout.setVisibility(View.GONE);
            btn_account_infoContainer.setVisibility(View.GONE);
            btn_account_delete.setVisibility(View.GONE);
        } else {
            btn_account_login.setVisibility(View.GONE);
            btn_account_logout.setVisibility(View.VISIBLE);
            btn_account_infoContainer.setVisibility(View.VISIBLE);
            btn_account_delete.setVisibility(View.VISIBLE);
            btn_account_info.setText(email_displayed);

            if(!showEmail) {
                btn_account_info.setText(email_obfuscated);
            }
        }
    }
    */

    override fun backPressedHandler() {
        revertDemoChanges()

        try { findNavController(requireView()).popBackStack() }
        catch (e: IllegalStateException) { e.printStackTrace() }

        try {
            val message = getString(R.string.toast_discardchanges)
            val toast = Toast.makeText(requireActivity(), message, com.google.android.material.R.integer.material_motion_duration_short_2)
            toast.show()
        } catch (e: IllegalStateException) { e.printStackTrace() }
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

    /*
    private void demoStyles() {
        PETActivity activity = ((PETActivity)getActivity());
        if(activity != null) {
            activity.changeTheme(
                    globalPreferencesViewModel.getColorTheme(),
                    globalPreferencesViewModel.getFontTheme()
            );
        }
        refreshFragment();
    }
    */

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

    override fun onSignInAccountSuccess() {
        refreshFragment()

        // Generate a Firestore document for the User with default data if needed
        try { buildUserDocument() }
        catch (e: Exception) { throw RuntimeException(e) }

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
    }
}
