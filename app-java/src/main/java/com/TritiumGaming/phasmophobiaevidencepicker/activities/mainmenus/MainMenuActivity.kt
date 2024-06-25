package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus

import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.onboarding.OnboardingActivity
import com.TritiumGaming.phasmophobiaevidencepicker.activities.pet.PETActivity
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared.NewsletterViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared.OnboardingViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.utils.GoogleMobileAdsConsentManager
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import java.util.concurrent.atomic.AtomicBoolean

/**
 * TestActivity class
 *
 * @author TritiumGamingStudios
 */
class MainMenuActivity : PETActivity() {
    protected var onboardingViewModel: OnboardingViewModel? = null
    protected var newsLetterViewModel: NewsletterViewModel? = null

    private val googleMobileAdsConsentManager: GoogleMobileAdsConsentManager? = null
    private val isMobileAdsInitializeCalled = AtomicBoolean(false)

    protected var appUpdateManager: AppUpdateManager? = null
    protected var updateType: Int = AppUpdateType.IMMEDIATE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_mainmenu)
        Log.d("MainMenuActivity", "OnCreate")

        //requestOnboardingActivity();

        //showConsentForm();
        //requestAdsConsentInformation(); Moved to PETActivity
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
        //newsLetterViewModel.init(this);
    }

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

    fun requestOnboardingActivity() {
        if (onboardingViewModel != null && onboardingViewModel!!.showIntroduction) {
            Log.d("Onboarding", "Starting Activity")
            startActivity(Intent(this, OnboardingActivity::class.java))
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

    public override fun loadPreferences() {
        super.loadPreferences()

        globalPreferencesViewModel?.let { globalPreferencesViewModel ->
            globalPreferencesViewModel.incrementAppOpenCount(applicationContext)

            //set language
            if (setLanguage(globalPreferencesViewModel.getLanguage(applicationContext))) {
                recreate()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        completePendingAppUpdates()
    }
}