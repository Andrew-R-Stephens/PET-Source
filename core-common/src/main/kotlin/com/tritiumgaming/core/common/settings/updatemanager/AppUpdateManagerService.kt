package com.tritiumgaming.core.common.settings.updatemanager

import android.app.Activity
import android.content.IntentSender
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import java.util.concurrent.atomic.AtomicBoolean

interface AppUpdateManagerService {

    var appUpdateManager: AppUpdateManager?
    var updateType: Int

    var activityUpdateResultLauncher: ActivityResultLauncher<IntentSenderRequest>

    fun checkForAppUpdate(activity: Activity): Boolean {
        appUpdateManager = AppUpdateManagerFactory.create(activity)

        val hasUpdate = AtomicBoolean(false)

        appUpdateManager?.appUpdateInfo
            ?.addOnSuccessListener { appUpdateInfo: AppUpdateInfo ->
                val isUpdateAvailable =
                    appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                val isUpdateAllowed = updateType == AppUpdateType.IMMEDIATE

                if (isUpdateAvailable && isUpdateAllowed) {
                    try {
                        requestAppUpdate(appUpdateInfo)
                        hasUpdate.set(true)
                    } catch (e: IntentSender.SendIntentException) { throw RuntimeException(e) }
                }
            }

        return hasUpdate.get()
    }

    fun requestAppUpdate(appUpdateInfo: AppUpdateInfo) {
        appUpdateManager?.startUpdateFlowForResult(
            appUpdateInfo, activityUpdateResultLauncher,
            AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build())
    }

    fun completePendingAppUpdate() {
        appUpdateManager?.appUpdateInfo
            ?.addOnSuccessListener { appUpdateInfo ->
                try {
                    val isUpdateInProgress =
                        appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                    if (isUpdateInProgress) {
                        appUpdateManager?.startUpdateFlowForResult(
                            appUpdateInfo, activityUpdateResultLauncher,
                            AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()
                        )
                    }
                } catch (e: IllegalStateException) {
                    e.printStackTrace()
                }
            }
    }


}