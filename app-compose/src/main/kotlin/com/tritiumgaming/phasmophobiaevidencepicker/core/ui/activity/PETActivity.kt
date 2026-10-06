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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.tritiumgaming.core.common.settings.updatemanager.AppUpdateManagerService
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.preview.DevicePreviews
import com.tritiumgaming.core.ui.theme.ExtendedUiConfiguration
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalThemeProvider
import com.tritiumgaming.core.ui.widgets.admob.provider.ConsentState
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
                    consentState = ConsentState(
                        allowPersonalizedAds = allowPersonalizedAds,
                        allowAnalytics = allowAnalytics ?: false
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .background(LocalPalette.current.surface)
                            .navigationBarsPadding()
                            .imePadding()
                    ) {
                        RootNavigation()

                        /*val showAnalyticsConsentDialog = state.isPrivacyOptionsRequired == false && !state.hasExplicitAnalyticsConsent
                        if (showAnalyticsConsentDialog) {
                            AnalyticsPreferencesDialog (
                                onSaveAndClose = { selection ->
                                    when(selection) {
                                        CcpaDataPreferenceOption.ALLOW_ANALYTICS -> {
                                            petActivityViewModel.setAllowAnalytics(true)
                                        }
                                        CcpaDataPreferenceOption.DENY_ANALYTICS -> {
                                            petActivityViewModel.setAllowAnalytics(false)
                                        }

                                    }
                                }
                            )
                        }*/
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

enum class CcpaDataPreferenceOption {
    ALLOW_ANALYTICS,
    DENY_ANALYTICS
}

@Composable
fun AnalyticsPreferencesDialog(
    onSaveAndClose: (CcpaDataPreferenceOption) -> Unit
) {
    var selectedOption by remember { mutableStateOf(CcpaDataPreferenceOption.ALLOW_ANALYTICS) }

    val options = listOf(
        Triple(
            CcpaDataPreferenceOption.ALLOW_ANALYTICS,
            "Share app usage & performance data",
            "Help us improve ${stringResource(R.string.app_name)} by sharing crash reports and usage statistics. " +
                    "This data is used solely to enhance feature performance and fix bugs."
        ),
        Triple(
            CcpaDataPreferenceOption.DENY_ANALYTICS,
            "Don't share usage data",
            "We won't collect usage statistics or performance logs. You can still use the app with all features intact."
        )
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = LocalPalette.current.scrim.copy(alpha = 0.75f)
    ) {
        AlertDialog(
            modifier = Modifier,
            onDismissRequest = {},
            containerColor = LocalPalette.current.surfaceContainer,
            icon = {
                Image(
                    modifier = Modifier
                        .size(48.dp),
                    painter = painterResource(id = R.drawable.icon_logo_app),
                    contentDescription = "",
                )
            },
            title = {
                Text(
                    text = "My data preferences",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Normal,
                        color = LocalPalette.current.onSurfaceVariant
                    )
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectableGroup()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    options.forEach { (option, title, subtext) ->
                        val isSelected = selectedOption == option

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { selectedOption = option }
                                .padding(12.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            RadioButton(
                                modifier = Modifier.padding(top = 2.dp),
                                selected = isSelected,
                                onClick = { selectedOption = option },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = LocalPalette.current.onSurfaceVariant,
                                    unselectedColor = LocalPalette.current.onSurface,
                                ),
                            )

                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top)
                            ) {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Normal,
                                        color = LocalPalette.current.onSurfaceVariant,
                                    )
                                )

                                Text(
                                    text = subtext,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = LocalPalette.current.onSurface,
                                    )
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { onSaveAndClose(selectedOption) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LocalPalette.current.primaryContainer
                    ),
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = "Save and close",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = LocalPalette.current.onPrimaryContainer
                    )
                }
            }
        )
    }
}

@DevicePreviews
@Composable
private fun CCPConsentDialogPreview() {
    LocalThemeProvider {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Red
        ) {
            AnalyticsPreferencesDialog(
                onSaveAndClose = { }
            )
        }
    }
}