package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.model.AppUpdateManagerService
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.model.FirebaseAnalyticsService
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.navigation.RootNavigation
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.GlobalPreferencesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.permissions.PermissionsViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.appsettings.ThemeConfigurationControl
import com.tritiumgaming.shared.core.domain.icons.IconResources

class PETActivity : AppCompatActivity(),
    AppUpdateManagerService, FirebaseAnalyticsService {

    private val globalPreferencesViewModel: GlobalPreferencesViewModel
        by viewModels { GlobalPreferencesViewModel.Factory }

    private val permissionsViewModel: PermissionsViewModel
            by viewModels { PermissionsViewModel.Factory }

    /* Firebase Analytics */
    private lateinit var auth: FirebaseAuth
    override var firebaseAnalytics: FirebaseAnalytics? = null

    /* Consent */
    //override var consentInformation: ConsentInformation? = null
    // Use an atomic boolean to initialize the Google Mobile Ads SDK and load ads once.
    //override val isMobileAdsInitializeCalled = AtomicBoolean(false)

    /* Update */
    override var appUpdateManager: AppUpdateManager? = null
    override var updateType: Int = IMMEDIATE
    override var activityUpdateResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()) {
                result: ActivityResult ->
            // handle callback
            if (result.resultCode != RESULT_OK) {
                print("Update flow failed! Result code: " + result.resultCode);
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {

        enableEdgeToEdge()

        auth = Firebase.auth

        super.onCreate(savedInstanceState)

        setContent {

            ThemeConfigurationControl(
                globalPreferencesViewModel = globalPreferencesViewModel
            ) {

                Scaffold {

                    Box(
                        modifier = Modifier
                            .background(LocalPalette.current.surface.color)
                            .padding(it)
                    ) {
                        RootNavigation(
                            globalPreferencesViewModel = globalPreferencesViewModel,
                            permissionsViewModel = permissionsViewModel
                        )
                    }

                }

            }

        }

        // Initialize the view model. This will gather consent and initialize Google Mobile Ads.
        permissionsViewModel.initMobileAdsConsentManager(this@PETActivity)

        initFirebaseAnalytics(this)

        checkForAppUpdate(this@PETActivity)

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
