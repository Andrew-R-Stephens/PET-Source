package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.noncompose.pet

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
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
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.GlobalPreferencesRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.LanguageRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.PaletteRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.ReviewTrackingRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.TypographyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.themes.ThemeModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.GlobalPreferencesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.PermissionsViewModel
import java.util.Locale
import java.util.concurrent.atomic.AtomicBoolean

private const val USER_PREFERENCES_NAME = "user_preferences"

val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME,
    produceMigrations = { context ->
        // Since we're migrating from SharedPreferences, add a migration based on the
        // SharedPreferences name
        // TODO change to real file
        listOf(SharedPreferencesMigration(context, USER_PREFERENCES_NAME))
    }
)

@Deprecated("Migrate to Jetpack Compose")
abstract class PETActivity : AppCompatActivity() {

    var firebaseAnalytics: FirebaseAnalytics? = null
        protected set

    protected lateinit var globalPreferencesViewModel: GlobalPreferencesViewModel
    private lateinit var permissionsViewModel: PermissionsViewModel

    private var consentInformation: ConsentInformation? = null

    // Use an atomic boolean to initialize the Google Mobile Ads SDK and load ads once.
    private val isMobileAdsInitializeCalled = AtomicBoolean(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initFirebaseAnalytics()

        Log.d("ViewModels", "PET begin...")
        //initViewModels()
        //loadPreferences()

        //automaticSignInAccount()
    }

    /** Set FirebaseAnalytics consent types to
     * [Consent V2](https://developers.google.com/tag-platform/security/guides/app-consent?platform=android&consentmode=advanced#upgrade-consent-v2). */
    private fun initFirebaseAnalytics() {
        //Obtain FirebaseAnalytics instance
        try { firebaseAnalytics = FirebaseAnalytics.getInstance(this)
            Log.d("Firebase", "Obtained instance.")
        } catch (e: IllegalStateException) { e.printStackTrace() }
    }

    protected open fun initViewModels() {
        Log.d("ViewModels", "PET init")

        initGlobalPreferencesViewModel()
        initPermissionsViewModel()
    }

    private fun initGlobalPreferencesViewModel() {
        globalPreferencesViewModel = ViewModelProvider(
            this,
            GlobalPreferencesViewModel.GlobalPreferencesFactory(
                GlobalPreferencesRepository(dataStore, this),
                ReviewTrackingRepository(dataStore, this),
                TypographyRepository(dataStore = dataStore, context = this),
                PaletteRepository(dataStore = dataStore, context = this),
                LanguageRepository(dataStore, this)
            )
        )[GlobalPreferencesViewModel::class.java]
        //globalPreferencesViewModel.init()
    }
    private fun initPermissionsViewModel() {
        permissionsViewModel = ViewModelProvider(
            this,
            PermissionsViewModel.PermissionsFactory()
        )[PermissionsViewModel::class.java]
    }

    protected open fun loadPreferences() {
        //set colorSpace
        /*changeTheme(
            globalPreferencesViewModel.colorThemeHandler.currentTheme,
            globalPreferencesViewModel.fontThemeHandler.currentTheme
        )*/

        if (globalPreferencesViewModel.screenSaverPreference.value) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    /** Sets the Skin Theme based on User Preferences.
     * @param colorSpace to be set */
    fun changeTheme(colorSpace: ThemeModel?, fontType: ThemeModel?) {
        fontType?.let {
            //theme.applyStyle(it.style, true)
        }
        colorSpace?.let {
            //theme.applyStyle(it.style, true)
        }
    }

    /** @param language The desired new language */
    fun configureLanguage(
        langCode: String = globalPreferencesViewModel.currentLanguageCode.value
    ): Boolean {

        val newLocale = Locale(langCode)
        val currentLocale = Locale.getDefault()

        val isChanged = (!currentLocale.language.equals(newLocale.language, ignoreCase = true))

        /*Locale.setDefault(newLocale)
        val config = resources.configuration
        config.setLocale(newLocale)
        resources.updateConfiguration(config, resources.displayMetrics)*/

        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.create(Locale.forLanguageTag(langCode))
        )

        return isChanged
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

    val isPrivacyOptionsRequired: Boolean
        // Show a privacy options button if required.
        get() = (consentInformation!!.privacyOptionsRequirementStatus
                == ConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED)

    protected fun createConsentInformation() {
        val debugSettings = ConsentDebugSettings.Builder(this)
            .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_NOT_EEA)
            .addTestDeviceHashedId("00E2BE3BE3FB3298734CA8B92655E237")
            .addTestDeviceHashedId("B3C272DE5AEAB81CA9CBBCB2A928A38E")
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
