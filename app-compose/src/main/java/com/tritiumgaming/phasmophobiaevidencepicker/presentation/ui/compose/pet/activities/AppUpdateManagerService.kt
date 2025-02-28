package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.pet.activities

import android.app.Activity
import android.content.IntentSender.SendIntentException
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
import com.google.android.play.core.install.model.UpdateAvailability.UPDATE_AVAILABLE
import java.util.concurrent.atomic.AtomicBoolean

interface AppUpdateManagerService {

    var appUpdateManager: AppUpdateManager?
    var updateType: Int

    var activityUpdateResultLauncher: ActivityResultLauncher<IntentSenderRequest>

    fun checkForAppUpdate(activity: Activity): Boolean {
        appUpdateManager = AppUpdateManagerFactory.create(activity)

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

    fun requestAppUpdate(appUpdateInfo: AppUpdateInfo) {
        appUpdateManager?.startUpdateFlowForResult(
            appUpdateInfo, activityUpdateResultLauncher,
            AppUpdateOptions.newBuilder(IMMEDIATE).build())
    }

    fun completePendingAppUpdate() {
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


}
