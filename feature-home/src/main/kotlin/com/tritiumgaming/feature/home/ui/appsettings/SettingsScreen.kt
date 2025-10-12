package com.tritiumgaming.feature.home.ui.appsettings

import androidx.compose.foundation.layout.Arrangement
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
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.common.menus.NavHeaderComposableParams
import com.tritiumgaming.core.ui.common.menus.NavigationHeaderComposable
import com.tritiumgaming.core.ui.common.menus.PETImageButtonType
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.home.ui.MainMenuScreen
import com.tritiumgaming.feature.home.ui.appsettings.content.CarouselComposable
import com.tritiumgaming.feature.home.ui.appsettings.content.HuntTimeoutPreferenceSeekbar
import com.tritiumgaming.feature.home.ui.appsettings.content.LabeledSwitch
import com.tritiumgaming.shared.core.domain.market.model.IncrementDirection
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
    settingsViewModel: SettingsScreenViewModel =
        viewModel(factory = SettingsScreenViewModel.Factory),
    navController: NavController = rememberNavController()
) {

    MainMenuScreen(
        modifier = modifier
    ) {

        SettingsContent(
            settingsViewModel = settingsViewModel
        ) { navController.popBackStack() }

        /*
        val paletteState = settingsViewModel.currentPaletteUUID.collectAsStateWithLifecycle()
        val typographyState = settingsViewModel.currentTypographyUUID.collectAsStateWithLifecycle()

        val palette = settingsViewModel.getPaletteByUUID(paletteState.value.uuid)
        val typography = settingsViewModel.getTypographyByUUID(typographyState.value.uuid)
        */

        /*
        ThemeConfigurationControl(
            palette = palette,
            typography = typography
        ) {
            SettingsContent(
                settingsViewModel = settingsViewModel
            ) { navController.popBackStack() }
        }
        */

    }

}

@Composable
private fun SettingsContent(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsScreenViewModel =
        viewModel(factory = SettingsScreenViewModel.Factory),
    onBack: () -> Unit = {}
) {

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
                params = NavHeaderComposableParams(
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
                        state = settingsViewModel.screensaverPreference,
                        onChange = {
                            settingsViewModel.setScreenSaverPreference(it)
                        }
                    )

                    LabeledSwitch(
                        label = stringResource(R.string.settings_networktitle),
                        state = settingsViewModel.networkPreference,
                        onChange = {
                            settingsViewModel.setNetworkPreference(it)
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
                            state = settingsViewModel.rTLPreference,
                            onChange = { settingsViewModel.setRTLPreference(it) }
                        )

                        LabeledSwitch(
                            label = stringResource(R.string.settings_enablehuntaudioqueue),
                            state = settingsViewModel.huntWarningAudioPreference,
                            onChange = {
                                settingsViewModel.setHuntWarningAudioPreference(it) }
                        )

                        LabeledSwitch(
                            label = stringResource(R.string.settings_enableGhostReorder),
                            state = settingsViewModel.ghostReorderPreference,
                            onChange = { settingsViewModel.setGhostReorderPreference(it) }
                        )
                    }

                    Column(
                        modifier = Modifier,
                    ) {
                        HuntTimeoutPreferenceSeekbar(
                            settingsViewModel = settingsViewModel
                        )
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

                    val paletteState = settingsViewModel.currentPaletteUUID.collectAsStateWithLifecycle()
                    val paletteLabel = settingsViewModel.getPaletteByUUID(paletteState.value.uuid)

                    CarouselComposable(
                        title = R.string.settings_colortheme_title,
                        state = settingsViewModel.currentPaletteUUID,
                        label = stringResource(paletteLabel.extrasFamily.title),
                        painterResource = painterResource(LocalPalette.current.extrasFamily.badge),
                        leftOnClick = {
                            settingsViewModel.setNextAvailablePalette(
                                IncrementDirection.BACKWARD
                            )
                        },
                        rightOnClick = {
                            settingsViewModel.setNextAvailablePalette(
                                IncrementDirection.FORWARD
                            )
                        }
                    )

                    val typographyState = settingsViewModel.currentTypographyUUID.collectAsStateWithLifecycle()
                    val typographyLabel = settingsViewModel.getTypographyByUUID(typographyState.value.uuid)

                    CarouselComposable(
                        title = R.string.settings_fontstylesettings,
                        state = MutableStateFlow(LocalTypography.current.extrasFamily.title),
                        label = stringResource(typographyLabel.extrasFamily.title),
                        colorFilter = ColorFilter.tint(LocalPalette.current.textFamily.body),
                        painterResource = painterResource(R.drawable.ic_font_family),
                        leftOnClick = {
                            settingsViewModel.setNextAvailableTypography(
                                IncrementDirection.BACKWARD
                            )
                        },
                        rightOnClick = {
                            settingsViewModel.setNextAvailableTypography(
                                IncrementDirection.FORWARD
                            )
                        }
                    )

                }

                /*if(permissionsUiState.value.isPrivacyOptionsRequired) {

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

                        EditButton {
                            activity?.let {
                                permissionsViewModel.showPrivacyOptionsForm(activity = it) {  }
                            }
                        }

                    }
                }*/

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
private fun saveAllPreferences(
    settingsViewModel: SettingsScreenViewModel
) {

    /*val activity = (context as PETActivity)

    if (globalPreferencesViewModel.screensaverPreference.value) {
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }*/

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