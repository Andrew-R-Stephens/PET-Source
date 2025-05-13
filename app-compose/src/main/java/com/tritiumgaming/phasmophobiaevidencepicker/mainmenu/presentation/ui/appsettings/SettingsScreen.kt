package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus.appsettings

import android.content.Context
import android.util.Log
import android.view.WindowManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
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
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.repository.market.core.ThemeRepository.IncrementDirection
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.activities.PETActivity
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.common.navigation.NavHeaderComposableParams
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.common.navigation.NavigationHeaderComposable
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.common.navigation.PETImageButtonType
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ExtendedPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalPalettesMap
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ExtendedTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.LocalTypographiesMap
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.GlobalPreferencesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.appsettings.content.CarouselComposable
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.appsettings.content.HuntTimeoutPreferenceSeekbar
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus.MainMenuFirebaseScreen
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
    navController: NavController = rememberNavController()
) {

    MainMenuFirebaseScreen(
        modifier = modifier
    ) {

        ConfigurationControl(
            globalPreferencesViewModel = globalPreferencesViewModel,
            onBack = {
                navController.popBackStack()
            }
        )
    }

}

@Composable
private fun SettingsContent(
    globalPreferencesViewModel: GlobalPreferencesViewModel =
        viewModel(factory = GlobalPreferencesViewModel.Factory),
    onBack: () -> Unit = {}
) {

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = LocalPalette.current.surface.color
    ) {

        Column(
            modifier = Modifier
                .padding(8.dp),
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
                        checked = globalPreferencesViewModel.screenSaverPreference,
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

                    CarouselComposable(
                        title = R.string.settings_colortheme_title,
                        state = MutableStateFlow(LocalPalette.current.extrasFamily.title),
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

                    CarouselComposable(
                        title = R.string.settings_fontstylesettings,
                        state = MutableStateFlow(LocalTypography.current.extrasFamily.title),
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
            }
        }
    }
}

@Composable
fun ConfigurationControl(
    modifier: Modifier = Modifier,
    globalPreferencesViewModel: GlobalPreferencesViewModel =
        viewModel(factory = GlobalPreferencesViewModel.Factory),
    content: @Composable () -> Unit = {}
) {

    val paletteState = globalPreferencesViewModel.currentPaletteUUID.collectAsStateWithLifecycle()
    val typographyState = globalPreferencesViewModel.currentTypographyUUID.collectAsStateWithLifecycle()

    val palette: ExtendedPalette = LocalPalettesMap[paletteState.value] ?: ClassicPalette
    val typography: ExtendedTypography = LocalTypographiesMap[typographyState.value] ?: ClassicTypography

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

@Composable
fun ConfigurationControl(
    globalPreferencesViewModel: GlobalPreferencesViewModel =
        viewModel(factory = GlobalPreferencesViewModel.Factory),
    onBack: () -> Unit = {}
) {

    val paletteState = globalPreferencesViewModel.currentPaletteUUID.collectAsStateWithLifecycle()
    val typographyState = globalPreferencesViewModel.currentTypographyUUID.collectAsStateWithLifecycle()

    val palette: ExtendedPalette = LocalPalettesMap[paletteState.value] ?: ClassicPalette
    val typography: ExtendedTypography = LocalTypographiesMap[typographyState.value] ?: ClassicTypography

    Log.d("Palette", stringResource(palette.extrasFamily.title))
    Log.d("Typography", stringResource(typography.extrasFamily.title))

    SelectiveTheme(
        palette = palette,
        typography = typography
    ) {

        SettingsContent(
            globalPreferencesViewModel = globalPreferencesViewModel
        ) { onBack() }
    }

}

private fun saveAllPreferences(
    globalPreferencesViewModel: GlobalPreferencesViewModel,
    context: Context
) {

    val activity = (context as PETActivity)

    if (globalPreferencesViewModel.screenSaverPreference.value) {
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    /*seekbar?.let{ seekbar ->
        globalPreferencesViewModel.setHuntWarnTimeoutPreference(seekbar.progress.toLong())
    }

    activity.recreate()*/

    val message = "Changes Saved"
    //Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}
