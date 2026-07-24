package com.tritiumgaming.phasmophobiaevidencepicker.core.ui.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.tritiumgaming.core.common.settings.updatemanager.AppUpdateManagerService
import com.tritiumgaming.core.ui.theme.ExtendedUiConfiguration
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalThemeProvider
import com.tritiumgaming.core.ui.widgets.admob.provider.AdConsent
import com.tritiumgaming.core.ui.widgets.admob.provider.LocalPrivacyProvider
import com.tritiumgaming.phasmophobiaevidencepicker.core.navigation.RootNavigation

class PETActivity : AppCompatActivity(),
    AppUpdateManagerService {

    private val petActivityViewModel: PETActivityViewModel
        by viewModels { PETActivityViewModel.Factory }

    private lateinit var auth: FirebaseAuth

    /* Update */
    override var appUpdateManager: AppUpdateManager? = null
    override var updateType: Int = AppUpdateType.IMMEDIATE
    override var activityUpdateResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()) {
                result: ActivityResult ->
            when (result.resultCode) {
                RESULT_OK -> {
                    Log.d("AppUpdate", "Update started/completed successfully")
                }
                RESULT_CANCELED -> {
                    Log.w("AppUpdate", "Update canceled by user")
                }
                else -> {
                    Log.e("AppUpdate",
                        "Update failed with code: ${result.resultCode}")
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {

        enableEdgeToEdge()

        auth = Firebase.auth

        super.onCreate(savedInstanceState)

        petActivityViewModel.initMobileAdsConsentManager(this@PETActivity)

        checkForAppUpdate(this@PETActivity)

        setContent {

            val state by petActivityViewModel.petActivityUiState.collectAsStateWithLifecycle()

            val palette = state.paletteUiState.palette
            val typography =  state.typographyUiState.typography
            val uiConfigurations = state.uiConfiguration
            val allowPersonalizedAds = state.allowPersonalizedAds
            val allowAnalytics = state.allowAnalytics

            LocalActivity.current?.let { activity ->
                setScreenSaverFlag(
                    activity = activity,
                    preference = state.disableScreenSaver
                )
            }

            LocalThemeProvider(
                palette = palette,
                typography = typography,
                uiConfiguration = ExtendedUiConfiguration (
                    densityType = uiConfigurations.densityType,
                    isRtl = uiConfigurations.isRtl,
                )
            ) {
                LocalPrivacyProvider(
                    adConsent = AdConsent(
                        allowPersonalizedAds = allowPersonalizedAds,
                        allowAnalytics = allowAnalytics
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .background(LocalPalette.current.surface)
                            .navigationBarsPadding()
                            .imePadding()
                    ) {
                        RootNavigation()
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        recreate()
    }

    private fun setScreenSaverFlag(
        activity: Activity,
        preference: Boolean
    ) {
        if (preference) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
    }

    override fun onResume() {
        super.onResume()

        completePendingAppUpdate()
    }

}