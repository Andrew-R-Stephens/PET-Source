package com.TritiumGaming.phasmophobiaevidencepicker.activities.pet

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.PermissionsViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.settings.ThemeModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared.GlobalPreferencesViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig.GoogleBuilder
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.tasks.Task
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.FormError
import com.google.android.ump.UserMessagingPlatform
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import java.util.List
import java.util.Locale
import java.util.concurrent.atomic.AtomicBoolean

/**
 * InvestigationActivity class
 *
 * @author TritiumGamingStudios
 */
abstract class PETActivity : AppCompatActivity() {
    var firebaseAnalytics: FirebaseAnalytics? = null
        protected set

    protected var globalPreferencesViewModel: GlobalPreferencesViewModel? = null
    protected var permissionsViewModel: PermissionsViewModel? = null

    private var consentInformation: ConsentInformation? = null

    // Use an atomic boolean to initialize the Google Mobile Ads SDK and load ads once.
    private val isMobileAdsInitializeCalled = AtomicBoolean(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initFirebaseAnalytics()

        initViewModels()
        loadPreferences()

        automaticSignInAccount()
    }

    /** Set FirebaseAnalytics consent types to
     * [Consent V2](https://developers.google.com/tag-platform/security/guides/app-consent?platform=android&consentmode=advanced#upgrade-consent-v2). */
    protected fun initFirebaseAnalytics() {
        //Obtain FirebaseAnalytics instance
        try { firebaseAnalytics = FirebaseAnalytics.getInstance(this)
            Log.d("Firebase", "Obtained instance.")
        } catch (e: IllegalStateException) { e.printStackTrace() }
    }

    protected open fun initViewModels(): AndroidViewModelFactory? {
        val factory: AndroidViewModelFactory =
            AndroidViewModelFactory.getInstance(this.application)

        initGlobalPreferencesViewModel(factory)
        initPermissionsViewModel(factory)

        return factory
    }

    private fun initGlobalPreferencesViewModel(factory: AndroidViewModelFactory) {
        globalPreferencesViewModel = factory.create(GlobalPreferencesViewModel::class.java)
        globalPreferencesViewModel =
            ViewModelProvider(this)[GlobalPreferencesViewModel::class.java]
        globalPreferencesViewModel?.init(this@PETActivity)
    }

    private fun initPermissionsViewModel(factory: AndroidViewModelFactory) {
        permissionsViewModel = factory.create(
            PermissionsViewModel::class.java
        )
        permissionsViewModel = ViewModelProvider(this)[PermissionsViewModel::class.java]
    }

    protected open fun loadPreferences() {
        //set colorSpace
        globalPreferencesViewModel?.let { globalPreferencesViewModel ->
            changeTheme(globalPreferencesViewModel.colorTheme, globalPreferencesViewModel.fontTheme)

            if (globalPreferencesViewModel.isAlwaysOn) {
                window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
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

    /** @param language The desired new language */
    fun setLanguage(language: String): Boolean {
        var isChanged = false

        val defaultLocale = Locale.getDefault()
        val locale = Locale(language)
        if (!(defaultLocale.language.equals(locale.language, ignoreCase = true))) {
            isChanged = true
        }

        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        return isChanged
    }

    val appLanguage: String
        /** @return the abbreviation of the chosen language that's saved to file
         */
        get() = globalPreferencesViewModel!!.languageName

    fun automaticSignInAccount() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            Log.d("AutoLogin", "User not null!")
            return
        }
        Log.d("AutoLogin", "User is null. Attempting silent log in.")

        val providers = List.of(
            GoogleBuilder().build()
        )

        AuthUI.getInstance()
            .silentSignIn(this, providers)
            .continueWithTask { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    return@continueWithTask task
                } else {
                    // Ignore any exceptions since we don't care about credential fetch errors.
                    return@continueWithTask FirebaseAuth.getInstance().signInAnonymously()
                }
            }
            .addOnCompleteListener(this) { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    Log.d("AuthUI", "Silent Anonymous login successful")
                } else {
                    Log.d("AuthUI", "Silent Anonymous login failed!")
                }
            }
    }

    val isPrivacyOptionsRequired: Boolean
        // Show a privacy options button if required.
        get() = (consentInformation!!.privacyOptionsRequirementStatus
                == ConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED)

    protected fun createConsentInformation() {
        val debugSettings = ConsentDebugSettings.Builder(this)
            .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_NOT_EEA)
            .addTestDeviceHashedId("00E2BE3BE3FB3298734CA8B92655E237")
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
    }
}
