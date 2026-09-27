package com.tritiumgaming.core.common.settings.updatemanager

import android.app.Activity
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
import com.google.android.play.core.install.model.UpdateAvailability.UPDATE_AVAILABLE

interface AppUpdateManagerService {

    var appUpdateManager: AppUpdateManager?
    var updateType: Int

    var activityUpdateResultLauncher: ActivityResultLauncher<IntentSenderRequest>

    fun checkForAppUpdate(activity: Activity) {
        Log.d("AppUpdate", "Running check")

        appUpdateManager = AppUpdateManagerFactory.create(activity)

        appUpdateManager?.appUpdateInfo?.addOnSuccessListener { appUpdateInfo ->
            val isUpdateAvailable = appUpdateInfo.updateAvailability() == UPDATE_AVAILABLE
            val isUpdateAllowed = appUpdateInfo.isUpdateTypeAllowed(updateType)

            if (isUpdateAvailable && isUpdateAllowed) {
                requestAppUpdate(appUpdateInfo)
            }
        }?.addOnFailureListener {
            Log.e("AppUpdate", "Failed to check for updates", it) }
    }

    fun requestAppUpdate(appUpdateInfo: AppUpdateInfo) {
        try {
            appUpdateManager?.startUpdateFlowForResult(
                appUpdateInfo, activityUpdateResultLauncher,
                AppUpdateOptions.newBuilder(updateType).build()
            )
        } catch (e: Exception) {
            Log.e("AppUpdate", "Failed to start update flow", e)
        }
    }

    fun completePendingAppUpdate() {
        appUpdateManager?.appUpdateInfo?.addOnSuccessListener { appUpdateInfo ->
            val availability = appUpdateInfo.updateAvailability()

            if (availability == DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                requestAppUpdate(appUpdateInfo)
            }
            // If using FLEXIBLE, you'd also check for InstallStatus.DOWNLOADED here
        }
    }
}

