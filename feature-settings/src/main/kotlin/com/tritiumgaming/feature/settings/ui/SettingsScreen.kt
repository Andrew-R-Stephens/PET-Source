package com.tritiumgaming.feature.settings.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.base.Arrow60LeftIcon
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.core.ui.theme.LocalThemeProvider
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.widgets.menus.NavigationHeaderCenter
import com.tritiumgaming.core.ui.widgets.menus.NavigationHeaderComposable
import com.tritiumgaming.core.ui.widgets.menus.NavigationHeaderSideButton
import com.tritiumgaming.core.ui.widgets.switch.LabeledSwitch
import com.tritiumgaming.feature.settings.ui.components.CarouselComposable
import com.tritiumgaming.feature.settings.ui.components.CarouselUiActions
import com.tritiumgaming.feature.settings.ui.components.HuntTimeoutPreferenceSeekbar
import com.tritiumgaming.shared.data.market.model.IncrementDirection
import com.tritiumgaming.shared.data.preferences.model.properties.DensityType

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@Preview(name = "Small Phone", device = "id:small_phone")
@Preview(name = "Small Phone Landscape", device = "spec:parent=small_phone,orientation=landscape")
@Preview(name = "Medium Phone Portrait", device = "spec:width=411dp,height=891dp")
@Preview(name = "Medium Phone Landscape", device = "spec:width=891dp,height=411dp")
@Preview(name = "Medium Tablet Portrait", device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait")
@Preview(name = "Medium Tablet Landscape", device = "spec:width=1280dp,height=800dp,dpi=240")
@Preview(name = "Foldable", device = "spec:width=673dp,height=841dp")
private annotation class DevicePreviews

@Composable
@Preview
private fun PrivacyOptionsButtonPreview() {
    LocalThemeProvider {
        Surface(
            color = LocalPalette.current.surface
        ) {
            PrivacyOptionsButton()
        }
    }
}

@DevicePreviews
@Composable
@Preview
private fun SettingsScreenPreview() {
    LocalThemeProvider {
        Surface(
            color = LocalPalette.current.surface
        ) {
            SettingsContent(
                settingsScreenUiState = SettingsScreenUiState()
            )
        }
    }
}

@Composable
fun SettingsScreen(
    settingsViewModel: SettingsScreenViewModel,
    navController: NavController = rememberNavController()
) {
    val context = LocalContext.current
    val settingsScreenUiState by
        settingsViewModel.settingsScreenUiState.collectAsStateWithLifecycle()

    SettingsContent(
        settingsScreenUiState = settingsScreenUiState,
        onBack = { navController.popBackStack() },
        onScreenSaverPreferenceChange = { settingsViewModel.setScreenSaverPreference(it) },
        onNetworkPreferenceChange = { settingsViewModel.setNetworkPreference(it) },
        onRTLPreferenceChange = { settingsViewModel.setRTLPreference(it) },
        onUiDensityPreferenceChange = { settingsViewModel.setUiDensityPreference(it) },
        onHuntWarningAudioPreferenceChange = { settingsViewModel.setHuntWarningAudioPreference(it) },
        onGhostReorderPreferenceChange = { settingsViewModel.setGhostReorderPreference(it) },
        onHuntWarnDurationPreferenceChange = { settingsViewModel.setHuntWarnDurationPreference(it) },
        onPersonalizedAdsPreferenceChange = { settingsViewModel.setPersonalizedAdsPreference(it) },
        onAnalyticsPreferenceChange = { settingsViewModel.setAnalyticsPreference(it) },
        onShowPrivacyOptionsForm = {
            if (context is android.app.Activity) {
                settingsViewModel.showPrivacyOptionsForm(context)
            }
        },
        onPalettePrevious = { settingsViewModel.setNextAvailablePalette(IncrementDirection.BACKWARD) },
        onPaletteNext = { settingsViewModel.setNextAvailablePalette(IncrementDirection.FORWARD) },
        onTypographyPrevious = { settingsViewModel.setNextAvailableTypography(IncrementDirection.BACKWARD) },
        onTypographyNext = { settingsViewModel.setNextAvailableTypography(IncrementDirection.FORWARD) }
    )
}

@Composable
private fun SettingsContent(
    settingsScreenUiState: SettingsScreenUiState,
    onBack: () -> Unit = {},
    onScreenSaverPreferenceChange: (Boolean) -> Unit = {},
    onNetworkPreferenceChange: (Boolean) -> Unit = {},
    onRTLPreferenceChange: (Boolean) -> Unit = {},
    onUiDensityPreferenceChange: (Boolean) -> Unit = {},
    onHuntWarningAudioPreferenceChange: (Boolean) -> Unit = {},
    onGhostReorderPreferenceChange: (Boolean) -> Unit = {},
    onHuntWarnDurationPreferenceChange: (Long) -> Unit = {},
    onPersonalizedAdsPreferenceChange: (Boolean) -> Unit = {},
    onAnalyticsPreferenceChange: (Boolean) -> Unit = {},
    onShowPrivacyOptionsForm: () -> Unit = {},
    onPalettePrevious: () -> Unit = {},
    onPaletteNext: () -> Unit = {},
    onTypographyPrevious: () -> Unit = {},
    onTypographyNext: () -> Unit = {}
) {
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
                ) { onBack() }
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
            onChange = { state -> onScreenSaverPreferenceChange(state) }
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
            onChange = { state -> onNetworkPreferenceChange(state) }
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
            onChange = { state -> onRTLPreferenceChange(state) }
        )
    }

    val uiDensityPreferenceComponent: @Composable (Modifier) -> Unit = @Composable { modifier ->
        LabeledSwitch(
            modifier = modifier
                .fillMaxWidth(),
            label = stringResource(R.string.settings_uiDensity),
            state = settingsScreenUiState.uiDensityType == DensityType.COMPACT,
            switchColors = labeledSwitchColors,
            textColor = LocalPalette.current.onSurface,
            onChange = { state -> onUiDensityPreferenceChange(state) }
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
            onChange = { state -> onHuntWarningAudioPreferenceChange(state) }
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
            onChange = { state -> onGhostReorderPreferenceChange(state) }
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
                onHuntWarnDurationPreferenceChange(millis) }
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
                onPrevious = onPalettePrevious,
                onNext = onPaletteNext
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
                onPrevious = onTypographyPrevious,
                onNext = onTypographyNext
            )
        )
    }

    val personalizedAdsPreferenceComponent: @Composable (Modifier) -> Unit = @Composable { modifier ->
        LabeledSwitch(
            modifier = modifier
                .fillMaxWidth(),
            label = stringResource(R.string.settings_enablePersonalizedAds),
            switchColors = labeledSwitchColors,
            textColor = LocalPalette.current.onSurface,
            state = settingsScreenUiState.adPrivacyPreference,
            onChange = { state -> onPersonalizedAdsPreferenceChange(state) }
        )
    }

    val analyticsPreferenceComponent: @Composable (modifier: Modifier) -> Unit = @Composable { modifier ->
        LabeledSwitch(
            modifier = modifier
                .fillMaxWidth(),
            label = stringResource(R.string.settings_enableAnalytics),
            switchColors = labeledSwitchColors,
            textColor = LocalPalette.current.onSurface,
            state = settingsScreenUiState.analyticsPreference,
            onChange = { state -> onAnalyticsPreferenceChange(state) }
        )
    }

    val privacyOptionsComponent: @Composable (modifier: Modifier) -> Unit = @Composable { modifier ->

        Column(
            modifier = modifier
                .wrapContentHeight()
                .fillMaxWidth()
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(4.dp),
                text = stringResource(R.string.settings_adsgdprcontrolsection),
                color = LocalPalette.current.secondary,
                style = LocalTypography.current.primary.bold,
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                maxLines = 1,
            )

            PrivacyOptionsButton(
                modifier = Modifier
            ) {
                onShowPrivacyOptionsForm()
            }

        }
    }

    val privacyPreferenceComponent: @Composable (modifier: Modifier) -> Unit = @Composable { modifier ->

        settingsScreenUiState.isPrivacyOptionsRequired?.let { isPrivacyOptionsRequired ->
            if (isPrivacyOptionsRequired) {
                privacyOptionsComponent(modifier)

                Spacer(modifier = Modifier.height(16.dp))
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(4.dp),
                        text = stringResource(R.string.settings_adsconsentcontrolsection),
                        color = LocalPalette.current.secondary,
                        style = LocalTypography.current.primary.bold,
                        textAlign = TextAlign.Start,
                        fontSize = 18.sp,
                        maxLines = 1,
                    )

                    personalizedAdsPreferenceComponent(Modifier)

                    analyticsPreferenceComponent(Modifier)

                    Spacer(modifier = Modifier.height(16.dp))

                }
            }
        }
    }

    SettingsScreenLayout(
        navigationHeader,
        screenPreferenceComponent,
        dataUsagePreferenceComponent,
        leftHandedPreferenceComponent,
        uiDensityPreferenceComponent,
        audioWarningPreferenceComponent,
        ghostReorderPreferenceComponent,
        huntWarningTimeoutPreferenceComponent,
        palettePreferenceComponent,
        typographyPreferenceComponent,
        privacyPreferenceComponent
    )
}

@Composable
private fun SettingsScreenLayout(
    navigationHeader: @Composable ((Modifier) -> Unit),
    screenPreferenceComponent: @Composable ((Modifier) -> Unit),
    dataUsagePreferenceComponent: @Composable ((Modifier) -> Unit),
    leftHandedPreferenceComponent: @Composable ((Modifier) -> Unit),
    uiDensityPreferenceComponent: @Composable ((Modifier) -> Unit),
    audioWarningPreferenceComponent: @Composable ((Modifier) -> Unit),
    ghostReorderPreferenceComponent: @Composable ((Modifier) -> Unit),
    huntWarningTimeoutPreferenceComponent: @Composable ((Modifier) -> Unit),
    palettePreferenceComponent: @Composable ((Modifier) -> Unit),
    typographyPreferenceComponent: @Composable ((Modifier) -> Unit),
    privacyPreferenceComponent: @Composable ((Modifier) -> Unit)
) {
    Column(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
    ) {

        navigationHeader(
            Modifier
                .fillMaxWidth()
                .heightIn(max = 64.dp)
        )

        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

        when (deviceConfiguration) {
            DeviceConfiguration.MOBILE_PORTRAIT -> {
                SettingsContentPortrait(
                    modifier = Modifier
                        .padding(8.dp),
                    screenPreferenceComponent = screenPreferenceComponent,
                    dataUsagePreferenceComponent = dataUsagePreferenceComponent,
                    leftHandedPreferenceComponent = leftHandedPreferenceComponent,
                    uiDensityPreferenceComponent = uiDensityPreferenceComponent,
                    audioWarningPreferenceComponent = audioWarningPreferenceComponent,
                    ghostReorderPreferenceComponent = ghostReorderPreferenceComponent,
                    huntWarningTimeoutPreferenceComponent = huntWarningTimeoutPreferenceComponent,
                    palettePreferenceComponent = palettePreferenceComponent,
                    typographyPreferenceComponent = typographyPreferenceComponent,
                    privacyPolicyPreferenceComponent = privacyPreferenceComponent
                )
            }

            DeviceConfiguration.MOBILE_LANDSCAPE,
            DeviceConfiguration.TABLET_PORTRAIT,
            DeviceConfiguration.TABLET_LANDSCAPE,
            DeviceConfiguration.DESKTOP -> {
                SettingsContentLandscape(
                    modifier = Modifier
                        .padding(8.dp),
                    screenPreferenceComponent = screenPreferenceComponent,
                    dataUsagePreferenceComponent = dataUsagePreferenceComponent,
                    leftHandedPreferenceComponent = leftHandedPreferenceComponent,
                    uiDensityPreferenceComponent = uiDensityPreferenceComponent,
                    audioWarningPreferenceComponent = audioWarningPreferenceComponent,
                    ghostReorderPreferenceComponent = ghostReorderPreferenceComponent,
                    huntWarningTimeoutPreferenceComponent = huntWarningTimeoutPreferenceComponent,
                    palettePreferenceComponent = palettePreferenceComponent,
                    typographyPreferenceComponent = typographyPreferenceComponent,
                    privacyPolicyPreferenceComponent = privacyPreferenceComponent
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
    uiDensityPreferenceComponent: @Composable (Modifier) -> Unit,
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
            text = stringResource(R.string.settings_devicesettings),
            color = LocalPalette.current.secondary,
            style = LocalTypography.current.primary.bold,
            textAlign = TextAlign.Start,
            fontSize = 18.sp,
            maxLines = 1,
        )

        screenPreferenceComponent(Modifier)

        //dataUsagePreferenceComponent(Modifier)

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(4.dp),
            text = stringResource(R.string.settings_interfacesettings),
            color = LocalPalette.current.secondary,
            style = LocalTypography.current.primary.bold,
            textAlign = TextAlign.Start,
            fontSize = 18.sp,
            maxLines = 1,
        )

        leftHandedPreferenceComponent(Modifier)

        //uiDensityPreferenceComponent(Modifier)

        audioWarningPreferenceComponent(Modifier)

        ghostReorderPreferenceComponent(Modifier)

        Spacer(modifier = Modifier.height(16.dp))

        huntWarningTimeoutPreferenceComponent(Modifier)

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

        Spacer(modifier = Modifier.height(16.dp))

        privacyPolicyPreferenceComponent(Modifier)

    }
}

@Composable
private fun SettingsContentLandscape(
    modifier: Modifier = Modifier,
    screenPreferenceComponent: @Composable (Modifier) -> Unit,
    dataUsagePreferenceComponent: @Composable (Modifier) -> Unit,
    leftHandedPreferenceComponent: @Composable (Modifier) -> Unit,
    uiDensityPreferenceComponent: @Composable (Modifier) -> Unit,
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
                text = stringResource(R.string.settings_devicesettings),
                color = LocalPalette.current.secondary,
                style = LocalTypography.current.primary.bold,
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                maxLines = 1,
            )

            screenPreferenceComponent(Modifier)

            //dataUsagePreferenceComponent(Modifier)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(4.dp),
                text = stringResource(R.string.settings_interfacesettings),
                color = LocalPalette.current.secondary,
                style = LocalTypography.current.primary.bold,
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                maxLines = 1,
            )

            leftHandedPreferenceComponent(Modifier)

            //uiDensityPreferenceComponent(Modifier)

            audioWarningPreferenceComponent(Modifier)

            ghostReorderPreferenceComponent(Modifier)

            Spacer(modifier = Modifier.height(16.dp))

            huntWarningTimeoutPreferenceComponent(Modifier)

            Spacer(modifier = Modifier.height(16.dp))

            privacyPolicyPreferenceComponent(Modifier)

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

        }
    }
}

@Composable
fun PrivacyOptionsButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    Button (
        modifier = modifier
            .height(48.dp)
            .width(IntrinsicSize.Min)
            .padding(4.dp),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = LocalPalette.current.primaryContainer,
            contentColor = LocalPalette.current.onPrimaryContainer,
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(android.R.drawable.ic_menu_edit),
                contentDescription = null,
                tint = LocalPalette.current.onPrimaryContainer
            )

            Text(
                modifier = Modifier,
                text = stringResource(R.string.settings_gdprsettings_button),
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
@Preview
fun PrivacyOptionsButtonPreviewComponent() {
    LocalThemeProvider {
        PrivacyOptionsButton()
    }
}