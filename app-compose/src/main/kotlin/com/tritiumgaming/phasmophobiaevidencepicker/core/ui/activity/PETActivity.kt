package com.tritiumgaming.phasmophobiaevidencepicker.core.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.tritiumgaming.core.common.settings.analytics.FirebaseAnalyticsService
import com.tritiumgaming.core.common.settings.updatemanager.AppUpdateManagerService
import com.tritiumgaming.core.ui.theme.ExtendedUiConfiguration
import com.tritiumgaming.core.ui.theme.ThemeConfigurationControl
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.navigation.RootNavigation

class PETActivity : AppCompatActivity(),
    AppUpdateManagerService, FirebaseAnalyticsService {

    private val petActivityViewModel: PETActivityViewModel
        by viewModels { PETActivityViewModel.Factory }

    /* Firebase Analytics */
    private lateinit var auth: FirebaseAuth
    override var firebaseAnalytics: FirebaseAnalytics? = null

    /* Update */
    override var appUpdateManager: AppUpdateManager? = null
    override var updateType: Int = AppUpdateType.IMMEDIATE
    override var activityUpdateResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()) {
                result: ActivityResult ->
            // handle callback
            if (result.resultCode != RESULT_OK) {
                print("Update flow failed! Result code: " + result.resultCode)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {

        enableEdgeToEdge()

        auth = Firebase.auth

        super.onCreate(savedInstanceState)

        // Initialize the view model ad manager.
        // This will gather consent and initialize Google Mobile Ads.
        petActivityViewModel.initMobileAdsConsentManager(this@PETActivity)

        initFirebaseAnalytics(this)

        checkForAppUpdate(this@PETActivity)

        setContent {

            val state by petActivityViewModel.petActivityUiState.collectAsStateWithLifecycle()

            val palette = state.paletteUiState.palette
            val typography =  state.typographyUiState.typography
            val uiConfigurations = state.uiConfiguration

            ThemeConfigurationControl(
                palette = palette,
                typography = typography,
                uiConfiguration = ExtendedUiConfiguration (
                    densityType = uiConfigurations.densityType,
                    isRtl = uiConfigurations.isRtl
                )
            ) {

                Scaffold {

                    Box(
                        modifier = Modifier
                            .background(LocalPalette.current.surface)
                            .padding(it)
                    ) {
                         RootNavigation(
                            windowInsets = WindowInsets(
                                bottom = it.calculateBottomPadding(),
                            )
                        )
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

    override fun onResume() {
        super.onResume()

        completePendingAppUpdate()
    }

}