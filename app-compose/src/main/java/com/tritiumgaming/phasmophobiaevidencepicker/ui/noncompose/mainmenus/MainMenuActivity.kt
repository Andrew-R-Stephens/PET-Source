package com.tritiumgaming.phasmophobiaevidencepicker.ui.noncompose.mainmenus

import android.content.IntentSender.SendIntentException
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
import com.google.android.play.core.install.model.UpdateAvailability.UPDATE_AVAILABLE
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.NewsletterRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.MainMenuViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.NewsletterViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.OnboardingViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.RootNavigation
import com.tritiumgaming.phasmophobiaevidencepicker.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Non_Colorblind
import com.tritiumgaming.phasmophobiaevidencepicker.theme.types.Classic
import com.tritiumgaming.phasmophobiaevidencepicker.ui.noncompose.pet.PETActivity
import com.tritiumgaming.phasmophobiaevidencepicker.ui.noncompose.pet.dataStore
import java.util.concurrent.atomic.AtomicBoolean

/**
 * TestActivity class
 *
 * @author TritiumGamingStudios
 */
class MainMenuActivity : PETActivity() {

    private lateinit var onboardingViewModel: OnboardingViewModel
    private lateinit var newsLetterViewModel: NewsletterViewModel
    private lateinit var mainMenuViewModel: MainMenuViewModel

    //private val googleMobileAdsConsentManager: GoogleMobileAdsConsentManager? = null
    //private val isMobileAdsInitializeCalled = AtomicBoolean(false)

    private var appUpdateManager: AppUpdateManager? = null
    private var updateType: Int = IMMEDIATE
    private var activityUpdateResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()) {
                result: androidx.activity.result.ActivityResult ->
            // handle callback
            if (result.resultCode != RESULT_OK) {
                print("Update flow failed! Result code: " + result.resultCode);
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.activity_mainmenu)
        setContent {
            SelectiveTheme(
                theme = Non_Colorblind,
                typography = Classic
            ) {
                RootNavigation()
            }
        }
        Log.d("MainMenuActivity", "OnCreate")

        //requestOnboardingActivity();

        createConsentInformation()

    }

    override fun initViewModels() {
        Log.d("ViewModels", "Main Menu init")
        super.initViewModels()

        initOnboardingViewModel()
        initNewsletterViewModel()
        initMainMenuViewModel()
    }

    private fun initOnboardingViewModel() {
        onboardingViewModel = ViewModelProvider(
            this,
            OnboardingViewModel.OnboardingFactory(
                // None
            )
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

    public override fun loadPreferences() {
        super.loadPreferences()

        globalPreferencesViewModel.incrementAppTimesOpened()

        //set language
        if (configureLanguage()) {
            recreate()
        }
    }

    fun checkForAppUpdate(): Boolean {
        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)

        val hasUpdate = AtomicBoolean(false)

        appUpdateManager?.appUpdateInfo?.addOnSuccessListener { appUpdateInfo: AppUpdateInfo ->
            val isUpdateAvailable =
                appUpdateInfo.updateAvailability() == UPDATE_AVAILABLE
            val isUpdateAllowed = updateType == IMMEDIATE

            if (isUpdateAvailable && isUpdateAllowed) {
                try {
                    requestAppUpdate(appUpdateInfo)
                    hasUpdate.set(true)
                } catch (e: SendIntentException) { throw RuntimeException(e) }
            }
        }

        return hasUpdate.get()
    }

    private fun requestAppUpdate(appUpdateInfo: AppUpdateInfo) {
        appUpdateManager?.startUpdateFlowForResult(
            appUpdateInfo, activityUpdateResultLauncher,
            AppUpdateOptions.newBuilder(IMMEDIATE).build())
    }

    private fun completePendingAppUpdate() {
        appUpdateManager?.appUpdateInfo?.addOnSuccessListener { appUpdateInfo ->
            val isUpdateInProgress =
                appUpdateInfo.updateAvailability() == DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                if (isUpdateInProgress) {
                    appUpdateManager?.startUpdateFlowForResult(
                        appUpdateInfo, activityUpdateResultLauncher,
                        AppUpdateOptions.newBuilder(IMMEDIATE).build()
                    )
                }
            }
    }

    override fun onResume() {
        super.onResume()

        completePendingAppUpdate()
    }

}