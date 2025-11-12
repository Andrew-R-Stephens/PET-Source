package com.tritiumgaming.feature.home.ui.appsettings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.common.menus.NavigationHeaderCenter
import com.tritiumgaming.core.ui.common.menus.NavigationHeaderComposable
import com.tritiumgaming.core.ui.common.menus.NavigationHeaderSideButton
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.ExtendedPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.ExtendedTypography
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.home.ui.HomeScreen
import com.tritiumgaming.feature.home.ui.appsettings.content.CarouselComposable
import com.tritiumgaming.feature.home.ui.appsettings.content.HuntTimeoutPreferenceSeekbar
import com.tritiumgaming.feature.home.ui.appsettings.content.LabeledSwitch
import com.tritiumgaming.shared.core.domain.market.model.IncrementDirection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.annotations.TestOnly
import androidx.compose.runtime.collectAsState

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

    HomeScreen(
        modifier = modifier
    ) {

        Column(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            NavigationHeader(
                onLeftClick = { navController.popBackStack() }
            )

            Spacer(modifier = Modifier.height(16.dp))

            val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
            val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

            when(deviceConfiguration) {
                DeviceConfiguration.MOBILE_PORTRAIT -> {
                    SettingsContentPortrait(
                        settingsViewModel = settingsViewModel,
                    )
                }
                DeviceConfiguration.MOBILE_LANDSCAPE,
                DeviceConfiguration.TABLET_PORTRAIT,
                DeviceConfiguration.TABLET_LANDSCAPE,
                DeviceConfiguration.DESKTOP -> {
                    SettingsContentLandscape(
                        settingsViewModel = settingsViewModel
                    )
                }
            }

        }
    }

}

@Composable
private fun NavigationHeader(
    onLeftClick: () -> Unit = {},
    onRightClick: () -> Unit = {}
) {
    NavigationHeaderComposable(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 64.dp),
        leftContent = { outerModifier ->
            NavigationHeaderSideButton(
                modifier = outerModifier,
                iconContent = { iconModifier ->
                    Image(
                        modifier = iconModifier,
                        painter = painterResource(R.drawable.ic_arrow_60_left),
                        colorFilter = ColorFilter.tint(LocalPalette.current.onSurface),
                        contentDescription = ""
                    )
                }
            ) { onLeftClick() }
        },
        rightContent = { outerModifier ->
            NavigationHeaderSideButton(
                modifier = outerModifier,
            )
        },
        centerContent = { outerModifier ->
            NavigationHeaderCenter(
                modifier = outerModifier,
                textContent = { modifier ->
                    BasicText(
                        modifier = modifier,
                        text = stringResource(R.string.settings_title),
                        style = LocalTypography.current.primary.regular.copy(
                            color = LocalPalette.current.primary,
                            textAlign = TextAlign.Center
                        ),
                        maxLines = 1,
                        autoSize = TextAutoSize.StepBased(
                            minFontSize = 2.sp, maxFontSize = 36.sp, stepSize = 2.sp)
                    )
                }
            )
        }
    )
}

@Composable
private fun ColumnScope.SettingsContentPortrait(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsScreenViewModel =
        viewModel(factory = SettingsScreenViewModel.Factory)
) {
    val settingsScreenUiState = settingsViewModel.settingsScreenUiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
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
            color = LocalPalette.current.secondary,
            style = LocalTypography.current.primary.bold,
            textAlign = TextAlign.Start,
            fontSize = 18.sp,
            maxLines = 1,
        )

        Column(
            modifier = Modifier,
        ) {

            ScreenPreferenceSwitch(
                state = settingsScreenUiState.value.screensaverPreference
            ) { state ->
                settingsViewModel.setScreenSaverPreference(state)
            }

            DataUsageSwitch(
                state = settingsScreenUiState.value.networkPreference
            ) { state ->
                settingsViewModel.setNetworkPreference(state)
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
                text = stringResource(R.string.settings_extrasettings),
                color = LocalPalette.current.secondary,
                style = LocalTypography.current.primary.bold,
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                maxLines = 1,
            )

            Column(
                modifier = Modifier,
            ) {
                LeftHandSupportSwitch(
                    state = settingsScreenUiState.value.rTLPreference
                ) { state ->
                    settingsViewModel.setRTLPreference(state)
                }

                WarningAudioSwitch(
                    state = settingsScreenUiState.value.huntWarningAudioPreference
                ) { state ->
                    settingsViewModel.setHuntWarningAudioPreference(state)
                }

                JournalReorderSwitch(
                    state = settingsScreenUiState.value.ghostReorderPreference
                ) { state ->
                    settingsViewModel.setGhostReorderPreference(state)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier,
            ) {
                HuntTimeoutSlider(settingsViewModel)
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
                color = LocalPalette.current.secondary,
                style = LocalTypography.current.primary.bold,
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                maxLines = 1,
            )

            val paletteState = settingsViewModel.currentPaletteUUID.collectAsStateWithLifecycle()
            val paletteLabel = settingsViewModel.getPaletteByUUID(paletteState.value.uuid)

            PalettePreferenceCarousel(settingsViewModel, paletteLabel)

            val typographyState = settingsViewModel.currentTypographyUUID.collectAsStateWithLifecycle()
            val typographyLabel = settingsViewModel.getTypographyByUUID(typographyState.value.uuid)

            TypographyPreferenceCarousel(typographyLabel, settingsViewModel)

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
                    color = LocalPalette.current.primary,
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

@Composable
private fun SettingsContentLandscape(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsScreenViewModel =
        viewModel(factory = SettingsScreenViewModel.Factory)
) {
    val settingsScreenUiState = settingsViewModel.settingsScreenUiState.collectAsStateWithLifecycle()

    Row(
        modifier = modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
    ) {

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
                color = LocalPalette.current.secondary,
                style = LocalTypography.current.primary.bold,
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                maxLines = 1,
            )

            Column(
                modifier = Modifier,
            ) {

                ScreenPreferenceSwitch(
                    state = settingsScreenUiState.value.screensaverPreference
                ) { state ->
                    settingsViewModel.setScreenSaverPreference(state)
                }

                DataUsageSwitch(
                    state = settingsScreenUiState.value.networkPreference
                ) { state ->
                    settingsViewModel.setNetworkPreference(state)
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
                    text = stringResource(R.string.settings_extrasettings),
                    color = LocalPalette.current.secondary,
                    style = LocalTypography.current.primary.bold,
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp,
                    maxLines = 1,
                )
                Column(
                    modifier = Modifier,
                ) {
                    LeftHandSupportSwitch(
                        state = settingsScreenUiState.value.rTLPreference
                    ) { state ->
                        settingsViewModel.setRTLPreference(state)
                    }

                    WarningAudioSwitch(
                        state = settingsScreenUiState.value.huntWarningAudioPreference
                    ) { state ->
                        settingsViewModel.setHuntWarningAudioPreference(state)
                    }

                    JournalReorderSwitch(
                        state = settingsScreenUiState.value.ghostReorderPreference
                    ) { state ->
                        settingsViewModel.setGhostReorderPreference(state)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier,
                ) {
                    HuntTimeoutSlider(settingsViewModel)
                }
            }

        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        ) {

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
                    color = LocalPalette.current.secondary,
                    style = LocalTypography.current.primary.bold,
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp,
                    maxLines = 1,
                )

                val paletteState = settingsViewModel.currentPaletteUUID.collectAsStateWithLifecycle()
                val paletteLabel = settingsViewModel.getPaletteByUUID(paletteState.value.uuid)

                PalettePreferenceCarousel(settingsViewModel, paletteLabel)

                val typographyState = settingsViewModel.currentTypographyUUID.collectAsStateWithLifecycle()
                val typographyLabel = settingsViewModel.getTypographyByUUID(typographyState.value.uuid)

                TypographyPreferenceCarousel(typographyLabel, settingsViewModel)

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
                        color = LocalPalette.current.primary,
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

@Composable
private fun TypographyPreferenceCarousel(
    typographyLabel: ExtendedTypography,
    settingsViewModel: SettingsScreenViewModel
) {
    CarouselComposable(
        title = R.string.settings_fontstylesettings,
        state = MutableStateFlow(LocalTypography.current.extrasFamily.title),
        label = stringResource(typographyLabel.extrasFamily.title),
        containerColor = LocalPalette.current.surfaceContainer,
        imageTint = LocalPalette.current.onSurface,
        primaryTextColor = LocalPalette.current.onSurface,
        secondaryTextColor = LocalPalette.current.onSurfaceVariant,
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

@Composable
private fun PalettePreferenceCarousel(
    settingsViewModel: SettingsScreenViewModel,
    paletteLabel: ExtendedPalette
) {
    CarouselComposable(
        title = R.string.settings_colortheme_title,
        state = settingsViewModel.currentPaletteUUID,
        label = stringResource(paletteLabel.extrasFamily.title),
        painterResource = painterResource(LocalPalette.current.extrasFamily.badge),
        containerColor = LocalPalette.current.surfaceContainer,
        primaryTextColor = LocalPalette.current.onSurface,
        secondaryTextColor = LocalPalette.current.onSurfaceVariant,
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
}

@Composable
private fun HuntTimeoutSlider(settingsViewModel: SettingsScreenViewModel) {
    HuntTimeoutPreferenceSeekbar(
        settingsViewModel = settingsViewModel,
        containerColor = LocalPalette.current.surfaceContainer,
        textColor = LocalPalette.current.onSurface,
        inactiveTrackColor = LocalPalette.current.progressBarColorThumbGradientStart,
        activeTrackColor = LocalPalette.current.progressBarColorStart,
        thumbOutlineColor = LocalPalette.current.progressBarColorStart,
        thumbInnerColor = LocalPalette.current.progressBarColorThumbGradientStart,
    )
}

@Composable
private fun JournalReorderSwitch(
    state: Boolean,
    onChange: (state: Boolean) -> Unit
) {
    LabeledSwitch(
        label = stringResource(R.string.settings_enableGhostReorder),
        textColor = LocalPalette.current.onSurface,
        state = state,
        onChange = { state -> onChange(state) }
    )
}

@Composable
private fun WarningAudioSwitch(
    state: Boolean,
    onChange: (state: Boolean) -> Unit
) {
    LabeledSwitch(
        label = stringResource(R.string.settings_enablehuntaudioqueue),
        state = state,
        textColor = LocalPalette.current.onSurface,
        onChange = { state -> onChange(state) }
    )
}

@Composable
private fun LeftHandSupportSwitch(
    state: Boolean,
    onChange: (state: Boolean) -> Unit
) {
    LabeledSwitch(
        label = stringResource(R.string.settings_enableLeftHandSupport),
        state = state,
        textColor = LocalPalette.current.onSurface,
        onChange = { state -> onChange(state) }
    )
}

@Composable
private fun DataUsageSwitch(
    state: Boolean,
    onChange: (state: Boolean) -> Unit
) {
    LabeledSwitch(
        label = stringResource(R.string.settings_networktitle),
        state = state,
        textColor = LocalPalette.current.onSurface,
        onChange = { state -> onChange(state) }
    )
}

@Composable
private fun ScreenPreferenceSwitch(
    state: Boolean,
    onChange: (state: Boolean) -> Unit
) {
    LabeledSwitch(
        label = stringResource(R.string.settings_screenalwayson),
        state = state,
        textColor = LocalPalette.current.onSurface,
        onChange = { state -> onChange(state) }
    )
}

@Composable
fun GDPRButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    Button (
        modifier = modifier
            .height(48.dp)
            .padding(4.dp),
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = LocalPalette.current.primary,
            containerColor = LocalPalette.current.primary,
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(android.R.drawable.ic_menu_edit),
                contentDescription = null,
                tint = LocalPalette.current.primaryContainer
            )

            Text(
                modifier = Modifier,
                text = stringResource(R.string.settings_adsconsentcontrolsettings),
                color = LocalPalette.current.onPrimaryContainer,
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
        GDPRButton()
    }
}