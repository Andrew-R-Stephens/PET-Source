package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.pet

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.WindowManager
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import com.google.android.gms.ads.MobileAds
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.FormError
import com.google.android.ump.UserMessagingPlatform
import com.google.firebase.analytics.FirebaseAnalytics
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.settings.ThemeModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.impl.AccountManagementService
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.GlobalPreferencesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.PermissionsViewModel
import java.util.Locale
import java.util.concurrent.atomic.AtomicBoolean
import androidx.core.view.isNotEmpty

abstract class PETActivity : AppCompatActivity(), AccountManagementService {
    var firebaseAnalytics: FirebaseAnalytics? = null
        protected set

    protected var globalPreferencesViewModel: GlobalPreferencesViewModel? = null
    private var permissionsViewModel: PermissionsViewModel? = null

    private var consentInformation: ConsentInformation? = null

    // Use an atomic boolean to initialize the Google Mobile Ads SDK and load ads once.
    private val isMobileAdsInitializeCalled = AtomicBoolean(false)

    override fun onCreate(savedInstanceState: Bundle?) {

        /*
         * Patch to support edge-to-edge mode with View
         */
        val contentView = findViewById<View>(android.R.id.content)
        ViewCompat.setOnApplyWindowInsetsListener(contentView) { view, windowInsets ->
            val systemBarsInsets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                systemBarsInsets.left,
                systemBarsInsets.top,
                systemBarsInsets.right,
                systemBarsInsets.bottom
            )
            WindowInsetsCompat.CONSUMED
        }

        super.onCreate(savedInstanceState)

        initFirebaseAnalytics()

        initViewModels()
        loadPreferences()

    }

    /** Set FirebaseAnalytics consent types to
     * [Consent V2](https://developers.google.com/tag-platform/security/guides/app-consent?platform=android&consentmode=advanced#upgrade-consent-v2). */
    private fun initFirebaseAnalytics() {
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
        permissionsViewModel = factory.create(PermissionsViewModel::class.java)
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

    /** @param language The desired new language */
    fun setLanguage(language: String): Boolean {
        val languageLocale = Locale(language)
        val currentLocale = Locale.getDefault()

        var isChanged = false
        if (!(currentLocale.language.equals(languageLocale.language, ignoreCase = true))) {
            isChanged = true
        }

        Locale.setDefault(languageLocale)
        val config = resources.configuration
        config.setLocale(languageLocale)
        resources.updateConfiguration(config, resources.displayMetrics)

        return isChanged
    }

    /*
    private fun automaticSignInAccount() {
        signIn(
            this@PETActivity,
            SignInCredentialManager.SignInOptions.SILENT
        )
    }

    private fun automaticSignInAccount() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            Log.d("AutoLogin", "User not null!")
            return
        }
        Log.d("AutoLogin", "User is null. Attempting silent log in.")

        val providers = listOf(GoogleBuilder().build())

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
    */

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
