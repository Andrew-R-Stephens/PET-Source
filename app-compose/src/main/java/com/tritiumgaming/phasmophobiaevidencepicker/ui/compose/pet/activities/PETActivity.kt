package com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.pet.activities

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
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
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.reviews.ReviewTrackingRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.settings.themes.ThemeModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.CodexRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.ColorThemeRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.DifficultyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.EvidenceRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.FontThemeRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.GhostRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.GlobalPreferencesRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.LanguageRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.MapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.MissionRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.NewsletterRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.GlobalPreferencesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.InvestigationViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.MainMenuViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.MapMenuViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.NewsletterViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.ObjectivesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.OnboardingViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.PermissionsViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.RootNavigation
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.pet.application.data.dataStore
import java.util.Locale
import java.util.concurrent.atomic.AtomicBoolean

abstract class PETActivity : AppCompatActivity() {

    var firebaseAnalytics: FirebaseAnalytics? = null
        protected set

    protected lateinit var globalPreferencesViewModel: GlobalPreferencesViewModel
    private lateinit var permissionsViewModel: PermissionsViewModel
    private lateinit var onboardingViewModel: OnboardingViewModel
    private lateinit var newsLetterViewModel: NewsletterViewModel
    private lateinit var mainMenuViewModel: MainMenuViewModel
    private lateinit var investigationViewModel: InvestigationViewModel
    private lateinit var objectivesViewModel: ObjectivesViewModel
    private lateinit var mapMenuViewModel: MapMenuViewModel


    private var consentInformation: ConsentInformation? = null

    // Use an atomic boolean to initialize the Google Mobile Ads SDK and load ads once.
    private val isMobileAdsInitializeCalled = AtomicBoolean(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initFirebaseAnalytics()

        Log.d("ViewModels", "PET begin...")
        initViewModels()
        loadPreferences()

        automaticSignInAccount()

        setContent {

            RootNavigation(
                /*onboardingViewModel = onboardingViewModel,
                newsletterViewModel = newsLetterViewModel,
                mainMenuViewModel = mainMenuViewModel,
                globalPreferencesViewModel = globalPreferencesViewModel,
                permissionsViewModel = permissionsViewModel,
                investigationViewModel = investigationViewModel*/
            )

        }

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

        val mapRepository = MapRepository(application)

        initGlobalPreferencesViewModel()
        initPermissionsViewModel()
        initOnboardingViewModel()
        initNewsletterViewModel()
        initMainMenuViewModel()

        initInvestigationViewModel(mapRepository)
        initObjectivesViewModel()
        initMapMenuViewModel(mapRepository)

    }

    private fun initGlobalPreferencesViewModel() {
        globalPreferencesViewModel = ViewModelProvider(
            this,
            GlobalPreferencesViewModel.GlobalPreferencesFactory(
                GlobalPreferencesRepository(dataStore, this),
                ReviewTrackingRepository(dataStore, this),
                FontThemeRepository(dataStore, this),
                ColorThemeRepository(dataStore, this),
                LanguageRepository(dataStore, this)
            )
        )[GlobalPreferencesViewModel::class.java]
        globalPreferencesViewModel.init()
    }
    private fun initPermissionsViewModel() {
        permissionsViewModel = ViewModelProvider(
            this,
            PermissionsViewModel.PermissionsFactory()
        )[PermissionsViewModel::class.java]
    }

    private fun initOnboardingViewModel() {
        onboardingViewModel = ViewModelProvider(
            this,
            OnboardingViewModel.OnboardingFactory()
        )[OnboardingViewModel::class.java]
    }

    private fun initNewsletterViewModel() {
        newsLetterViewModel = ViewModelProvider(
            this,
            NewsletterViewModel.NewsletterFactory(
                NewsletterRepository(dataStore, application)
            )
        )[NewsletterViewModel::class.java]
    }

    private fun initMainMenuViewModel() {
        mainMenuViewModel = ViewModelProvider(
            this,
            MainMenuViewModel.MainMenuFactory()
        )[MainMenuViewModel::class.java]
    }
    private fun initMapMenuViewModel(mapRepository: MapRepository) {
        mapMenuViewModel = ViewModelProvider(
            this,
            MapMenuViewModel.MapMenuFactory(
                mapRepository
            )
        )[MapMenuViewModel::class.java]
    }

    private fun initObjectivesViewModel() {
        objectivesViewModel = ViewModelProvider(
            this,
            ObjectivesViewModel.ObjectivesFactory(
                MissionRepository(application)
            )
        )[ObjectivesViewModel::class.java]
    }

    private fun initInvestigationViewModel(mapRepository: MapRepository) {
        val evidenceRepository = EvidenceRepository(application)

        investigationViewModel = ViewModelProvider(
            this,
            InvestigationViewModel.InvestigationFactory(
                evidenceRepository,
                GhostRepository(evidenceRepository, application),
                DifficultyRepository(application),
                mapRepository,
                CodexRepository(application)
            )
        )[InvestigationViewModel::class.java]
    }

    protected open fun loadPreferences() {
        //set colorSpace
        changeTheme(
            globalPreferencesViewModel.colorThemeHandler.currentTheme,
            globalPreferencesViewModel.fontThemeHandler.currentTheme
        )

        if (globalPreferencesViewModel.screenSaverPreference.value) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    /** Sets the Skin Theme based on User Preferences.
     * @param colorSpace to be set */
    fun changeTheme(colorSpace: ThemeModel?, fontType: ThemeModel?) {
        fontType?.let {
            theme.applyStyle(it.style, true)
        }
        colorSpace?.let {
            theme.applyStyle(it.style, true)
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
