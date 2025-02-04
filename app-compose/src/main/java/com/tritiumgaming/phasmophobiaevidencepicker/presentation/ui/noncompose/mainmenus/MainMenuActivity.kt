package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.noncompose.mainmenus

import android.content.IntentSender.SendIntentException
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
import com.google.android.play.core.install.model.UpdateAvailability.UPDATE_AVAILABLE
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.noncompose.pet.PETActivity
import java.util.concurrent.atomic.AtomicBoolean


@Deprecated("Migrate to Jetpack Compose")
class MainMenuActivity : PETActivity() {

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

        Log.d("MainMenuActivity", "OnCreate")

        //requestOnboardingActivity();

        createConsentInformation()

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