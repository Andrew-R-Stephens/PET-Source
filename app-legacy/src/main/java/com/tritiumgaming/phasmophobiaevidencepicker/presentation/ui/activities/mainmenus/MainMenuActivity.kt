package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus

import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
import com.google.android.play.core.install.model.UpdateAvailability.UPDATE_AVAILABLE
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.pet.PETActivity
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.NewsletterViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.OnboardingViewModel
import java.util.concurrent.atomic.AtomicBoolean

/**
 * TestActivity class
 *
 * @author TritiumGamingStudios
 */
class MainMenuActivity : PETActivity() {

    private var onboardingViewModel: OnboardingViewModel? = null
    private var newsLetterViewModel: NewsletterViewModel? = null

    //private val googleMobileAdsConsentManager: GoogleMobileAdsConsentManager? = null
    //private val isMobileAdsInitializeCalled = AtomicBoolean(false)

    private var appUpdateManager: AppUpdateManager? = null
    private var updateType: Int = IMMEDIATE
    private lateinit var activityUpdateResultLauncher: ActivityResultLauncher<IntentSenderRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_mainmenu)
        Log.d("MainMenuActivity", "OnCreate")

        //requestOnboardingActivity();

        activityUpdateResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()) {
                result: androidx.activity.result.ActivityResult ->
            // handle callback
            if (result.resultCode != RESULT_OK) {
                print("Update flow failed! Result code: " + result.resultCode);
            }
        }

        createConsentInformation()

    }

    override fun initViewModels(): AndroidViewModelFactory? {
        val factory: AndroidViewModelFactory? = super.initViewModels()

        factory?.let {
            initOnboardingViewModel(factory)
            initNewsletterViewModel(factory)

        }
        return factory
    }

    private fun initOnboardingViewModel(factory: AndroidViewModelFactory) {
        onboardingViewModel = factory.create(OnboardingViewModel::class.java)
        onboardingViewModel = ViewModelProvider(this)[OnboardingViewModel::class.java]
    }

    private fun initNewsletterViewModel(factory: AndroidViewModelFactory) {
        newsLetterViewModel = factory.create(NewsletterViewModel::class.java)
        newsLetterViewModel = ViewModelProvider(this)[NewsletterViewModel::class.java]
    }

    public override fun loadPreferences() {
        super.loadPreferences()

        globalPreferencesViewModel?.let { globalPreferencesViewModel ->
            globalPreferencesViewModel.incrementAppOpenCount(applicationContext)

            //set language
            if (setLanguage(globalPreferencesViewModel.currentLanguageAbbr)) {
                recreate()
            }
        }
    }

    private fun checkForAppUpdate(): Boolean {
        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)

        val hasUpdate = AtomicBoolean(false)

        appUpdateManager?.appUpdateInfo
            ?.addOnSuccessListener { appUpdateInfo: AppUpdateInfo ->

                if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {

                    val isUpdateAvailable = appUpdateInfo.updateAvailability() == UPDATE_AVAILABLE
                    val isUpdateAllowed = updateType == IMMEDIATE

                    if (isUpdateAvailable && isUpdateAllowed) {
                        try {
                            val result = requestAppUpdate(appUpdateInfo)
                            hasUpdate.set(result)
                        }
                        catch (e: Exception) { e.printStackTrace() }
                        catch (e: Exception) { e.printStackTrace() }
                    }

                }

            }

        return hasUpdate.get()
    }

    private fun requestAppUpdate(appUpdateInfo: AppUpdateInfo): Boolean {
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            //throw Exception("AppUpdate - activity not in a state to launch update flow.")
            return false
        }

        val result = appUpdateManager?.startUpdateFlowForResult(
            appUpdateInfo,
            activityUpdateResultLauncher,
            AppUpdateOptions.newBuilder(
                IMMEDIATE
            ).build()
        ) ?: false

        return result
    }

    private fun completePendingAppUpdate() {
        appUpdateManager?.appUpdateInfo?.addOnSuccessListener { appUpdateInfo ->
            try {
                val isUpdateInProgress =
                    appUpdateInfo.updateAvailability() == DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                if (isUpdateInProgress) {
                    appUpdateManager?.startUpdateFlowForResult(
                        appUpdateInfo,
                        activityUpdateResultLauncher,
                        AppUpdateOptions.newBuilder(IMMEDIATE).build()
                    )
                }
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        completePendingAppUpdate()
    }

    override fun onStart() {
        super.onStart()

        checkForAppUpdate()
    }

    override fun onDestroy() {
        //activityUpdateResultLauncher = null
        super.onDestroy()
    }

}