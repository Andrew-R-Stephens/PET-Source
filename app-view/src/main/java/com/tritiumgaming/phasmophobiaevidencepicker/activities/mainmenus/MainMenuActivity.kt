package com.tritiumgaming.phasmophobiaevidencepicker.activities.mainmenus

import android.content.IntentSender.SendIntentException
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.activities.pet.PETActivity
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodels.shared.NewsletterViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodels.shared.OnboardingViewModel
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
import com.google.android.play.core.install.model.UpdateAvailability.UPDATE_AVAILABLE
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

        setContentView(R.layout.activity_mainmenu)
        Log.d("MainMenuActivity", "OnCreate")

        //requestOnboardingActivity();

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

    /*
    fun requestOnboardingActivity() {
        if (onboardingViewModel != null && onboardingViewModel!!.showIntroduction) {
            Log.d("Onboarding", "Starting Activity")
            startActivity(Intent(this, OnboardingActivity::class.java))
        }
    }
    */

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

    /*
    fun checkForAppUpdates(): Boolean {
        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)

        val hasUpdate = AtomicBoolean(false)

        appUpdateManager?.appUpdateInfo?.addOnSuccessListener { info: AppUpdateInfo ->
            val isUpdateAvailable = info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            val isUpdateAllowed = updateType == AppUpdateType.IMMEDIATE
            if (isUpdateAvailable && isUpdateAllowed) {
                try {
                    appUpdateManager?.startUpdateFlowForResult(
                        info, updateType, this@MainMenuActivity, 123)
                    hasUpdate.set(true)
                } catch (e: SendIntentException) { throw RuntimeException(e) }
            }
        }

        return hasUpdate.get()
    }

    private fun completePendingAppUpdates() {
        appUpdateManager?.let { appUpdateManager ->
            if (updateType == AppUpdateType.IMMEDIATE) {
                appUpdateManager.appUpdateInfo.addOnSuccessListener { info: AppUpdateInfo ->
                    if (info.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                        try {
                            appUpdateManager.startUpdateFlowForResult(
                                info, updateType, this@MainMenuActivity, 123)
                        } catch (e: SendIntentException) { throw RuntimeException(e) }
                    }
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123) {
            when (resultCode) {
                RESULT_OK -> Log.e("UpdateResult", "Update Failed!")
                else -> Log.e("UpdateResult", "Update Succeeded!")
            }
        }
    }

    fun requestOnboardingActivity() {
        if (onboardingViewModel != null && onboardingViewModel!!.showIntroduction) {
            Log.d("Onboarding", "Starting Activity")
            startActivity(Intent(this, OnboardingActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

        completePendingAppUpdates()
    }
    */

}