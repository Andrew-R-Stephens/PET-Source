package com.tritiumgaming.feature.settings.ui

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
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.icon.impl.base.Arrow60LeftIcon
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.widgets.menus.NavigationHeaderCenter
import com.tritiumgaming.core.ui.widgets.menus.NavigationHeaderComposable
import com.tritiumgaming.core.ui.widgets.menus.NavigationHeaderSideButton
import com.tritiumgaming.feature.settings.ui.components.CarouselComposable
import com.tritiumgaming.feature.settings.ui.components.HuntTimeoutPreferenceSeekbar
import com.tritiumgaming.core.ui.widgets.switch.LabeledSwitch
import com.tritiumgaming.feature.settings.ui.components.CarouselUiActions
import com.tritiumgaming.shared.data.market.model.IncrementDirection
import org.jetbrains.annotations.TestOnly

@Preview
@TestOnly
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen()
}

@Composable
fun SettingsScreen(
    settingsViewModel: SettingsScreenViewModel =
        viewModel(factory = SettingsScreenViewModel.Factory),
    navController: NavController = rememberNavController()
) {
    val settingsScreenUiState by
        settingsViewModel.settingsScreenUiState.collectAsStateWithLifecycle()

    // TODO val privacyPolicyUiState by settingsViewModel.privacyPolicyUiState.collectAsStateWithLifecycle()

    val navigationHeader: @Composable (Modifier) -> Unit = @Composable { modifier ->
        NavigationHeaderComposable(
            modifier = modifier,
            leftContent = { outerModifier ->
                NavigationHeaderSideButton(
                    modifier = outerModifier,
                    iconContent = { iconModifier ->
                        Arrow60LeftIcon(
                            modifier = iconModifier,
                            colors = IconVectorColors.defaults(
                                fillColor = LocalPalette.current.onSurface
                            )
                        )
                    }
                ) { navController.popBackStack() }
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

    val labeledSwitchColors = SwitchDefaults.colors(
        uncheckedTrackColor = LocalPalette.current.surfaceContainerHighest,
        uncheckedBorderColor = LocalPalette.current.outline,
        uncheckedThumbColor = LocalPalette.current.outline,
        checkedTrackColor = LocalPalette.current.primary,
        checkedBorderColor = Color.Transparent,
        checkedThumbColor = LocalPalette.current.onPrimary,
    )

    val screenPreferenceComponent: @Composable (Modifier) -> Unit = @Composable { modifier ->
        LabeledSwitch(
            modifier = modifier
                .fillMaxWidth(),
            label = stringResource(R.string.settings_screenalwayson),
            state = settingsScreenUiState.screensaverPreference,
            switchColors = labeledSwitchColors,
            textColor = LocalPalette.current.onSurface,
            onChange = { state -> settingsViewModel.setScreenSaverPreference(state) }
        )
    }

    val dataUsagePreferenceComponent: @Composable (Modifier) -> Unit = @Composable { modifier ->
        LabeledSwitch(
            modifier = modifier
                .fillMaxWidth(),
            label = stringResource(R.string.settings_networktitle),
            state = settingsScreenUiState.networkPreference,
            switchColors = labeledSwitchColors,
            textColor = LocalPalette.current.onSurface,
            onChange = { state -> settingsViewModel.setNetworkPreference(state) }
        )
    }

    val leftHandedPreferenceComponent: @Composable (Modifier) -> Unit = @Composable { modifier ->
        LabeledSwitch(
            modifier = modifier
                .fillMaxWidth(),
            label = stringResource(R.string.settings_enableLeftHandSupport),
            state = settingsScreenUiState.rTLPreference,
            switchColors = labeledSwitchColors,
            textColor = LocalPalette.current.onSurface,
            onChange = { state -> settingsViewModel.setRTLPreference(state) }
        )
    }

    val audioWarningPreferenceComponent: @Composable (Modifier) -> Unit = @Composable { modifier ->
        LabeledSwitch(
            modifier = modifier
                .fillMaxWidth(),
            label = stringResource(R.string.settings_enablehuntaudioqueue),
            state = settingsScreenUiState.huntWarningAudioPreference,
            switchColors = labeledSwitchColors,
            textColor = LocalPalette.current.onSurface,
            onChange = { state -> settingsViewModel.setHuntWarningAudioPreference(state) }
        )
    }

    val ghostReorderPreferenceComponent: @Composable (Modifier) -> Unit = @Composable { modifier ->
        LabeledSwitch(
            modifier = modifier
                .fillMaxWidth(),
            label = stringResource(R.string.settings_enableGhostReorder),
            switchColors = labeledSwitchColors,
            textColor = LocalPalette.current.onSurface,
            state = settingsScreenUiState.ghostReorderPreference,
            onChange = { state -> settingsViewModel.setGhostReorderPreference(state) }
        )

    }

    val huntWarningTimeoutPreferenceComponent: @Composable (Modifier) -> Unit = @Composable { modifier ->
        HuntTimeoutPreferenceSeekbar(
            modifier = modifier
                .fillMaxWidth(),
            state = settingsScreenUiState.huntWarnDurationPreference,
            containerColor = LocalPalette.current.surfaceContainer,
            textColor = LocalPalette.current.onSurface,
            inactiveTrackColor = LocalPalette.current.onSurface,
            activeTrackColor = LocalPalette.current.primary,
            thumbOutlineColor = LocalPalette.current.primary,
            thumbInnerColor = LocalPalette.current.onSurface,
            onValueChange = { millis ->
                settingsViewModel.setHuntWarnDurationPreference(millis) }
        )
    }

    val palettePreferenceComponent: @Composable (Modifier) -> Unit = @Composable { modifier ->
        val state = settingsScreenUiState.paletteUiState

        CarouselComposable(
            modifier = modifier
                .fillMaxWidth(),
            title = R.string.settings_colortheme_title,
            state = state.uuid,
            label = stringResource(state.palette.extrasFamily.title),
            iconComponent = { modifier ->
                Image(
                    modifier = modifier,
                    alignment = Alignment.Center,
                    painter = painterResource(LocalPalette.current.extrasFamily.badge),
                    colorFilter = null,
                    contentDescription = "",
                    contentScale = ContentScale.Inside
                )
            },
            containerColor = LocalPalette.current.surfaceContainer,
            primaryTextColor = LocalPalette.current.onSurface,
            secondaryTextColor = LocalPalette.current.onSurfaceVariant,
            actions = CarouselUiActions(
                onPrevious = {
                    settingsViewModel.setNextAvailablePalette(IncrementDirection.BACKWARD)
                },
                onNext = {
                    settingsViewModel.setNextAvailablePalette(IncrementDirection.FORWARD)
                }
            )
        )
    }

    val typographyPreferenceComponent: @Composable (Modifier) -> Unit = @Composable { modifier ->
        val state = settingsScreenUiState.typographyUiState

        CarouselComposable(
            modifier = modifier
                .fillMaxWidth(),
            title = R.string.settings_fontstylesettings,
            state = state.uuid,
            label = stringResource(state.typography.extrasFamily.title),
            iconComponent = { modifier ->
                Image(
                    modifier = modifier,
                    alignment = Alignment.Center,
                    painter = painterResource(R.drawable.ic_font_family),
                    colorFilter = ColorFilter.tint(LocalPalette.current.onSurface),
                    contentDescription = "",
                    contentScale = ContentScale.Inside
                )
            },
            containerColor = LocalPalette.current.surfaceContainer,
            primaryTextColor = LocalPalette.current.onSurface,
            secondaryTextColor = LocalPalette.current.onSurfaceVariant,
            actions = CarouselUiActions(
                onPrevious = {
                    settingsViewModel.setNextAvailableTypography(IncrementDirection.BACKWARD)
                },
                onNext = {
                    settingsViewModel.setNextAvailableTypography(IncrementDirection.FORWARD)
                }
            )
        )
    }

    val privacyPolicyPreferenceComponent: @Composable (Modifier) -> Unit = @Composable { modifier ->

        //TODO
        /*if(permissionsUiState.value.isPrivacyOptionsRequired) {

            val activity = LocalContext.current.applicationContext

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

                GDPRButton {
                    activity?.let {
                        permissionsViewModel.showPrivacyOptionsForm(activity = it) {  }
                    }
                }

            }
        }*/
    }

    Column(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        navigationHeader(Modifier
            .fillMaxWidth()
            .heightIn(max = 64.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

        when(deviceConfiguration) {
            DeviceConfiguration.MOBILE_PORTRAIT -> {
                SettingsContentPortrait(
                    modifier = Modifier,
                    screenPreferenceComponent = screenPreferenceComponent,
                    dataUsagePreferenceComponent = dataUsagePreferenceComponent,
                    leftHandedPreferenceComponent = leftHandedPreferenceComponent,
                    audioWarningPreferenceComponent = audioWarningPreferenceComponent,
                    ghostReorderPreferenceComponent = ghostReorderPreferenceComponent,
                    huntWarningTimeoutPreferenceComponent = huntWarningTimeoutPreferenceComponent,
                    palettePreferenceComponent = palettePreferenceComponent,
                    typographyPreferenceComponent = typographyPreferenceComponent,
                    privacyPolicyPreferenceComponent = privacyPolicyPreferenceComponent
                )
            }
            DeviceConfiguration.MOBILE_LANDSCAPE,
            DeviceConfiguration.TABLET_PORTRAIT,
            DeviceConfiguration.TABLET_LANDSCAPE,
            DeviceConfiguration.DESKTOP -> {
                SettingsContentLandscape(
                    modifier = Modifier,
                    screenPreferenceComponent = screenPreferenceComponent,
                    dataUsagePreferenceComponent = dataUsagePreferenceComponent,
                    leftHandedPreferenceComponent = leftHandedPreferenceComponent,
                    audioWarningPreferenceComponent = audioWarningPreferenceComponent,
                    ghostReorderPreferenceComponent = ghostReorderPreferenceComponent,
                    huntWarningTimeoutPreferenceComponent = huntWarningTimeoutPreferenceComponent,
                    palettePreferenceComponent = palettePreferenceComponent,
                    typographyPreferenceComponent = typographyPreferenceComponent,
                    privacyPolicyPreferenceComponent = privacyPolicyPreferenceComponent
                )
            }
        }

    }
}

@Composable
private fun ColumnScope.SettingsContentPortrait(
    modifier: Modifier = Modifier,
    screenPreferenceComponent: @Composable (Modifier) -> Unit,
    dataUsagePreferenceComponent: @Composable (Modifier) -> Unit,
    leftHandedPreferenceComponent: @Composable (Modifier) -> Unit,
    audioWarningPreferenceComponent: @Composable (Modifier) -> Unit,
    ghostReorderPreferenceComponent: @Composable (Modifier) -> Unit,
    huntWarningTimeoutPreferenceComponent: @Composable (Modifier) -> Unit,
    palettePreferenceComponent: @Composable (Modifier) -> Unit,
    typographyPreferenceComponent: @Composable (Modifier) -> Unit,
    privacyPolicyPreferenceComponent: @Composable (Modifier) -> Unit,
) {
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

        screenPreferenceComponent(Modifier)

        dataUsagePreferenceComponent(Modifier)

        Spacer(modifier = Modifier.height(16.dp))


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

        leftHandedPreferenceComponent(Modifier)

        audioWarningPreferenceComponent(Modifier)

        ghostReorderPreferenceComponent(Modifier)

        Spacer(modifier = Modifier.height(16.dp))

        huntWarningTimeoutPreferenceComponent(Modifier)

        privacyPolicyPreferenceComponent(Modifier)

        Spacer(modifier = Modifier.height(16.dp))

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

        palettePreferenceComponent(Modifier)

        typographyPreferenceComponent(Modifier)

    }
}

@Composable
private fun SettingsContentLandscape(
    modifier: Modifier = Modifier,
    screenPreferenceComponent: @Composable (Modifier) -> Unit,
    dataUsagePreferenceComponent: @Composable (Modifier) -> Unit,
    leftHandedPreferenceComponent: @Composable (Modifier) -> Unit,
    audioWarningPreferenceComponent: @Composable (Modifier) -> Unit,
    ghostReorderPreferenceComponent: @Composable (Modifier) -> Unit,
    huntWarningTimeoutPreferenceComponent: @Composable (Modifier) -> Unit,
    palettePreferenceComponent: @Composable (Modifier) -> Unit,
    typographyPreferenceComponent: @Composable (Modifier) -> Unit,
    privacyPolicyPreferenceComponent: @Composable (Modifier) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp)
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

            screenPreferenceComponent(Modifier)

            dataUsagePreferenceComponent(Modifier)

            Spacer(modifier = Modifier.height(16.dp))

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

            leftHandedPreferenceComponent(Modifier)

            audioWarningPreferenceComponent(Modifier)

            ghostReorderPreferenceComponent(Modifier)

            Spacer(modifier = Modifier.height(16.dp))

            huntWarningTimeoutPreferenceComponent(Modifier)

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


                palettePreferenceComponent(Modifier)

                typographyPreferenceComponent(Modifier)

            }

            privacyPolicyPreferenceComponent(Modifier)

        }
    }
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

    "Changes Saved"
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