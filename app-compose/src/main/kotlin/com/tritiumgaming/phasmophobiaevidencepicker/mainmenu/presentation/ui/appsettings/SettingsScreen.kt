package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.appsettings

import android.content.Context
import android.util.Log
import android.view.WindowManager
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.model.IncrementDirection
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.activities.PETActivity
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.navigation.NavHeaderComposableParams
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.navigation.NavigationHeaderComposable
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.navigation.PETImageButtonType
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.GlobalPreferencesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.permissions.PermissionsViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.appsettings.content.CarouselComposable
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.appsettings.content.HuntTimeoutPreferenceSeekbar
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus.MainMenuScreen
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus.appsettings.content.LabeledSwitch
import kotlinx.coroutines.flow.MutableStateFlow
import org.jetbrains.annotations.TestOnly

@Preview
@TestOnly
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen()
}

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
        .fillMaxSize(),
    globalPreferencesViewModel: GlobalPreferencesViewModel =
        viewModel(factory = GlobalPreferencesViewModel.Factory),
    permissionsViewModel: PermissionsViewModel =
        viewModel(factory = PermissionsViewModel.Factory),
    navController: NavController = rememberNavController()
) {

    MainMenuScreen(
        modifier = modifier
    ) {

        ThemeConfigurationControl(
            globalPreferencesViewModel = globalPreferencesViewModel
        ) {
            SettingsContent(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                globalPreferencesViewModel = globalPreferencesViewModel,
                permissionsViewModel  = permissionsViewModel
            ) { navController.popBackStack() }
        }

    }

}

@Composable
private fun SettingsContent(
    modifier: Modifier = Modifier,
    globalPreferencesViewModel: GlobalPreferencesViewModel =
        viewModel(factory = GlobalPreferencesViewModel.Factory),
    permissionsViewModel: PermissionsViewModel =
        viewModel(factory = PermissionsViewModel.Factory),
    onBack: () -> Unit = {}
) {
    val activity = LocalActivity.current

    val permissionsUiState = permissionsViewModel.uiState.collectAsStateWithLifecycle()

    Surface(
        modifier = modifier
            .fillMaxWidth(),
        color = LocalPalette.current.surface.color
    ) {

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top),
        ) {
            NavigationHeaderComposable(
                NavHeaderComposableParams(
                    leftType = PETImageButtonType.BACK,
                    centerTitleRes = R.string.settings_title,
                    leftOnClick = { onBack() }
                )
            )

            HorizontalDivider()

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            ) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(4.dp),
                    text = stringResource(R.string.settings_generalsettings),
                    color = LocalPalette.current.textFamily.primary,
                    style = LocalTypography.current.primary.bold,
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp,
                    maxLines = 1,
                )

                Column(
                    modifier = Modifier,
                ) {

                    LabeledSwitch(
                        label = stringResource(R.string.settings_screenalwayson),
                        checked = globalPreferencesViewModel.screensaverPreference,
                        onChange = {
                            globalPreferencesViewModel.setScreenSaverPreference(it)
                        }
                    )

                    LabeledSwitch(
                        label = stringResource(R.string.settings_networktitle),
                        checked = globalPreferencesViewModel.networkPreference,
                        onChange = {
                            globalPreferencesViewModel.setNetworkPreference(it)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                ) {

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(4.dp),
                        text = stringResource(R.string.settings_extrasettings),
                        color = LocalPalette.current.textFamily.primary,
                        style = LocalTypography.current.primary.bold,
                        textAlign = TextAlign.Start,
                        fontSize = 18.sp,
                        maxLines = 1,
                    )
                    Column(
                        modifier = Modifier,
                    ) {
                        LabeledSwitch(
                            label = stringResource(R.string.settings_enableLeftHandSupport),
                            checked = globalPreferencesViewModel.rTLPreference,
                            onChange = { globalPreferencesViewModel.setRTLPreference(it) }
                        )

                        LabeledSwitch(
                            label = stringResource(R.string.settings_enablehuntaudioqueue),
                            checked = globalPreferencesViewModel.huntWarningAudioPreference,
                            onChange = {
                                globalPreferencesViewModel.setHuntWarningAudioPreference(it) }
                        )

                        LabeledSwitch(
                            label = stringResource(R.string.settings_enableGhostReorder),
                            checked = globalPreferencesViewModel.ghostReorderPreference,
                            onChange = { globalPreferencesViewModel.setGhostReorderPreference(it) }
                        )
                    }

                    Column(
                        modifier = Modifier,
                    ) {
                        HuntTimeoutPreferenceSeekbar()
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                ) {

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(4.dp),
                        text = stringResource(R.string.settings_appearancesettings),
                        color = LocalPalette.current.textFamily.primary,
                        style = LocalTypography.current.primary.bold,
                        textAlign = TextAlign.Start,
                        fontSize = 18.sp,
                        maxLines = 1,
                    )

                    val paletteState = globalPreferencesViewModel.currentPaletteUUID.collectAsStateWithLifecycle()
                    val paletteLabel = globalPreferencesViewModel.getPaletteByUUID(paletteState.value)

                    CarouselComposable(
                        title = R.string.settings_colortheme_title,
                        state = globalPreferencesViewModel.currentPaletteUUID,
                        label = stringResource(paletteLabel.extrasFamily.title),
                        painterResource = painterResource(LocalPalette.current.extrasFamily.badge),
                        leftOnClick = {
                            globalPreferencesViewModel.setNextAvailablePalette(
                                IncrementDirection.BACKWARD
                            )
                        },
                        rightOnClick = {
                            globalPreferencesViewModel.setNextAvailablePalette(
                                IncrementDirection.FORWARD
                            )
                        }
                    )

                    val typographyState = globalPreferencesViewModel.currentTypographyUUID.collectAsStateWithLifecycle()
                    val typographyLabel = globalPreferencesViewModel.getTypographyByUUID(typographyState.value)

                    CarouselComposable(
                        title = R.string.settings_fontstylesettings,
                        state = MutableStateFlow(LocalTypography.current.extrasFamily.title),
                        label = stringResource(typographyLabel.extrasFamily.title),
                        colorFilter = ColorFilter.tint(LocalPalette.current.textFamily.body),
                        painterResource = painterResource(R.drawable.ic_font_family),
                        leftOnClick = {
                            globalPreferencesViewModel.setNextAvailableTypography(
                                IncrementDirection.BACKWARD
                            )
                        },
                        rightOnClick = {
                            globalPreferencesViewModel.setNextAvailableTypography(
                                IncrementDirection.FORWARD
                            )
                        }
                    )

                }

                if(permissionsUiState.value.isPrivacyOptionsRequired) {

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                    ) {

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(4.dp),
                            text = stringResource(R.string.settings_adsconsentcontrolsettings),
                            color = LocalPalette.current.textFamily.primary,
                            style = LocalTypography.current.primary.bold,
                            textAlign = TextAlign.Start,
                            fontSize = 18.sp,
                            maxLines = 1,
                        )

                        EditButton() {
                            activity?.let {
                                permissionsViewModel.showPrivacyOptionsForm(activity = it) {  }
                            }
                        }

                    }
                }

            }
        }
    }
}

@Composable
fun EditButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    Button (
        modifier = Modifier
            .height(48.dp)
            .padding(4.dp),
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = LocalPalette.current.coreFamily.primary,
            containerColor = LocalPalette.current.coreFamily.primary,
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(android.R.drawable.ic_menu_edit),
                contentDescription = null,
                tint = LocalPalette.current.textFamily.body
            )

            Text(
                modifier = Modifier,
                text = stringResource(R.string.settings_adsconsentcontrolsettings),
                color = LocalPalette.current.textFamily.body,
                style = LocalTypography.current.quaternary.bold,
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                maxLines = 1,
            )
        }

    }

}

@Composable
fun ThemeConfigurationControl(
    modifier: Modifier = Modifier,
    globalPreferencesViewModel: GlobalPreferencesViewModel =
        viewModel(factory = GlobalPreferencesViewModel.Factory),
    content: @Composable () -> Unit = {}
) {

    val paletteState = globalPreferencesViewModel.currentPaletteUUID.collectAsStateWithLifecycle()
    val typographyState = globalPreferencesViewModel.currentTypographyUUID.collectAsStateWithLifecycle()

    val palette = globalPreferencesViewModel.getPaletteByUUID(paletteState.value)
    val typography = globalPreferencesViewModel.getTypographyByUUID(typographyState.value)

    Log.d("Palette", stringResource(palette.extrasFamily.title))
    Log.d("Typography", stringResource(typography.extrasFamily.title))

    SelectiveTheme(
        palette = palette,
        typography = typography
    ) {
        Box(
            modifier = modifier
        ) {
            content()
        }

    }

}

private fun saveAllPreferences(
    globalPreferencesViewModel: GlobalPreferencesViewModel,
    context: Context
) {

    val activity = (context as PETActivity)

    if (globalPreferencesViewModel.screensaverPreference.value) {
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    /*seekbar?.let{ seekbar ->
        globalPreferencesViewModel.setHuntWarnTimeoutPreference(seekbar.progress.toLong())
    }

    activity.recreate()*/

    val message = "Changes Saved"
    //Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

@Composable
@Preview
fun EditButtonPreview() {
    SelectiveTheme(
        palette = LocalPalette.current,
        typography = LocalTypography.current
    ) {
        EditButton()
    }
}