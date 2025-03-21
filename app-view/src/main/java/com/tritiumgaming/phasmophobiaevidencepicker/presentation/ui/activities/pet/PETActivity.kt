package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.pet

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.ump.ConsentInformation
import com.google.firebase.analytics.FirebaseAnalytics
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.settings.ThemeModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.impl.AccountManagementService
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.impl.AdsConsentManagementService
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.impl.FirebaseAnalyticsConsentManagementService
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.GlobalPreferencesViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.concurrent.atomic.AtomicBoolean

abstract class PETActivity : AppCompatActivity(),
    AccountManagementService, AdsConsentManagementService,
    FirebaseAnalyticsConsentManagementService {

    protected val globalPreferencesViewModel: GlobalPreferencesViewModel by viewModels { GlobalPreferencesViewModel.Factory }

    var firebaseAnalytics: FirebaseAnalytics? = null
        protected set

    override var consentInformation: ConsentInformation? = null

    override val isPrivacyOptionsRequired: Boolean
        // Show a privacy options button if required.
        get() = (consentInformation?.privacyOptionsRequirementStatus
                == ConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED)

    // Use an atomic boolean to initialize the Google Mobile Ads SDK and load ads once.
    override val isMobileAdsInitializeCalled = AtomicBoolean(false)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        initViewModels()
        loadPreferences()

        initFirebaseAnalytics()

    }

    /** Set FirebaseAnalytics consent types to
     * [Consent V2](https://developers.google.com/tag-platform/security/guides/app-consent?platform=android&consentmode=advanced#upgrade-consent-v2). */
    private fun initFirebaseAnalytics() {

        //Obtain FirebaseAnalytics instance
        try { firebaseAnalytics = FirebaseAnalytics.getInstance(this)
            Log.d("Firebase", "Obtained instance.")
        } catch (e: IllegalStateException) { e.printStackTrace() }

        initializeFirebaseAnalyticsConsent()

    }

    protected open fun initViewModels() {
        initGlobalPreferencesViewModel()
    }

    private fun initGlobalPreferencesViewModel() {
        globalPreferencesViewModel.init(this@PETActivity)
    }

    protected open fun loadPreferences() {
        //set colorSpace
        changeTheme(globalPreferencesViewModel.colorTheme, globalPreferencesViewModel.fontTheme)

        lifecycleScope.launch {
            globalPreferencesViewModel.screenSaverPreference.collect {
                setScreenSaverFlag(it)
            }
        }

    }

    /** Sets the Skin Theme based on User Preferences.
     * @param colorSpace to be set */
    fun changeTheme(colorSpace: ThemeModel?, fontType: ThemeModel?) {
        if (fontType != null) {
            val styleId = fontType.style
            theme.applyStyle(styleId, true)
        }

        if (colorSpace != null) {
            val colorSpaceId = colorSpace.style
            theme.applyStyle(colorSpaceId, true)
        }
    }

    /** Sets the Skin Theme based on User Preferences.
     * @param colorSpace to be set */
    fun changeTheme(colorSpace: Int?, fontType: Int?) {
        if (fontType != null) {
            theme.applyStyle(fontType, true)
        }

        if (colorSpace != null) {
            theme.applyStyle(colorSpace, true)
        }
    }

    /** @param newLanguage The desired new language */
    fun setLanguage(newLanguage: String): Boolean {

        val currentLocale = Locale.getDefault()
        val newLocale = Locale(newLanguage)

        if (!(currentLocale.language.equals(newLocale.language, ignoreCase = true))) {
            Locale.setDefault(newLocale)
            val config = resources.configuration
            config.setLocale(newLocale)
            resources.updateConfiguration(config, resources.displayMetrics)

            return true
        }

        return false
    }

    /*protected fun createConsentInformation() {
        val debugSettings = ConsentDebugSettings.Builder(this)
            .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_NOT_EEA)
            .addTestDeviceHashedId("00E2BE3BE3FB3298734CA8B92655E237")
            .addTestDeviceHashedId("35C63C64AD5C412021F7831FF07C5411")
            .build()

        // Create a ConsentRequestParameters object.
        val params = ConsentRequestParameters.Builder()
            .setConsentDebugSettings(debugSettings)
            .setTagForUnderAgeOfConsent(false)
            .build()


        consentInformation = UserMessagingPlatform.getConsentInformation(this)
        consentInformation?.let { consentInformation ->
            consentInformation.requestConsentInfoUpdate(this, params, {
                UserMessagingPlatform.loadAndShowConsentFormIfRequired(this) {
                    // Consent gathering failed.
                    loadAndShowError: FormError? ->
                        loadAndShowError?.let {
                            Log.w("ConsentManagerV2", String.format("%s: %s",
                                loadAndShowError.errorCode, loadAndShowError.message))
                        }
                    }
                },
                // Consent gathering failed.
                { requestConsentError: FormError ->
                    Log.w("ConsentManagerV2", String.format("%s: %s",
                            requestConsentError.errorCode, requestConsentError.message))
                })
            // Check if you can initialize the Google Mobile Ads SDK in parallel
            // while checking for new consent information. Consent obtained in
            // the previous session can be used to request ads.
            if (consentInformation.canRequestAds()) { initializeMobileAdsSdk() }
        }

    }

    private fun initializeMobileAdsSdk() {
        if (isMobileAdsInitializeCalled.getAndSet(true)) { return }

        // Initialize the Google Mobile Ads SDK.
        MobileAds.initialize(this)

        // TODO: Request an ad.
        // InterstitialAd.load(...);
    }*/

    fun setScreenSaverFlag(disableScreenSaver: Boolean) {
        if(disableScreenSaver) {
            this@PETActivity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            this@PETActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

}
