package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.mainmenus.appsettings

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.navigation.NavHeaderComposableParams
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.navigation.NavigationHeaderComposable
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.navigation.PETImageButtonType
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.mainmenus.MainMenuFirebaseScreen
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.mainmenus.appsettings.content.CarouselComposable
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.mainmenus.appsettings.content.LabeledSwitch
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.pet.activities.PETActivity
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.ExtendedPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.LocalPalettesMap
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.ExtendedTypography
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.LocalTypographiesMap
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.GlobalPreferencesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.controllers.theming.AThemeHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
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
        val context = LocalContext.current
        val petActivity = (LocalContext.current) as PETActivity

        ConfigurationControl(
            globalPreferencesViewModel = globalPreferencesViewModel,
            onCancel = {
                navController.popBackStack()
            },
            onSave = { color, font ->
                saveAllPreferences(globalPreferencesViewModel, context)

                globalPreferencesViewModel.setCurrentPaletteUUID(color)
                globalPreferencesViewModel.setCurrentTypographyUUID(font)

                navController.popBackStack()
            }
        )
    }

}

@Composable
private fun SettingsContent(
    globalPreferencesViewModel: GlobalPreferencesViewModel =
        viewModel(factory = GlobalPreferencesViewModel.Factory),
    onChangeColor: (String) -> Unit = {},
    onChangeFont: (String) -> Unit = {},
    onSave: () -> Unit = {},
    onCancel: () -> Unit = {},
    tempCurrentColor: String,
    tempCurrentTypography: String
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
                    leftType = PETImageButtonType.CANCEL,
                    rightType = PETImageButtonType.CONFIRM,
                    centerTitleRes = R.string.settings_title,
                    leftOnClick = { onCancel() },
                    rightOnClick = { onSave() }
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
                            onChangeColor(
                                globalPreferencesViewModel.paletteHandler.findNextAvailable(
                                    AThemeHandler.IncrementDirection.BACKWARD,
                                    tempCurrentColor
                                )
                            )
                        },
                        rightOnClick = {
                            onChangeColor(
                                globalPreferencesViewModel.paletteHandler.findNextAvailable(
                                    AThemeHandler.IncrementDirection.FORWARD,
                                    tempCurrentColor
                                )
                            )
                        }
                    )

                    CarouselComposable(
                        title = R.string.settings_fontstylesettings,
                        state = MutableStateFlow(LocalTypography.current.extrasFamily.title),
                        colorFilter = ColorFilter.tint(LocalPalette.current.textFamily.body),
                        painterResource = painterResource(R.drawable.ic_font_family),
                        leftOnClick = {
                            onChangeFont(
                                globalPreferencesViewModel.typographyHandler.findNextAvailable(
                                    AThemeHandler.IncrementDirection.BACKWARD,
                                    tempCurrentTypography
                                )
                            )
                        },
                        rightOnClick = {
                            onChangeFont(
                                globalPreferencesViewModel.typographyHandler.findNextAvailable(
                                    AThemeHandler.IncrementDirection.FORWARD,
                                    tempCurrentTypography
                                )
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

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val paletteState = globalPreferencesViewModel.currentPaletteUUID.collectAsStateWithLifecycle().value
    val typographyState = globalPreferencesViewModel.currentTypographyUUID.collectAsStateWithLifecycle().value

    var rememberPalette by remember { mutableStateOf(paletteState) }
    var rememberTypography by remember { mutableStateOf(typographyState) }

    LaunchedEffect(Unit) {

        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                globalPreferencesViewModel.currentPaletteUUID.collect {
                    rememberPalette = it
                    Log.d("Palette", "Palette init: ${LocalPalettesMap[rememberPalette]?.extrasFamily?.title?.let { context.getString(it) }}")
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                globalPreferencesViewModel.currentTypographyUUID.collect {
                    rememberTypography = it
                    Log.d("Typography", "Typography init: ${LocalTypographiesMap[rememberTypography]?.extrasFamily?.title?.let { context.getString(it) }}")
                }
            }
        }
    }

    val palette: ExtendedPalette = LocalPalettesMap[rememberPalette] ?: ClassicPalette
    val typography: ExtendedTypography = LocalTypographiesMap[rememberTypography] ?: ClassicTypography

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
    onSave: (String, String) -> Unit = { color, typography -> },
    onCancel: () -> Unit = {},
    globalPreferencesViewModel: GlobalPreferencesViewModel =
        viewModel(factory = GlobalPreferencesViewModel.Factory),
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val paletteState = globalPreferencesViewModel.currentPaletteUUID.collectAsStateWithLifecycle().value
    val typographyState = globalPreferencesViewModel.currentTypographyUUID.collectAsStateWithLifecycle().value

    var rememberPalette by remember {
        mutableStateOf(paletteState)
    }
    var rememberTypography by remember {
        mutableStateOf(typographyState)
    }

    LaunchedEffect(Unit) {

        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                globalPreferencesViewModel.currentPaletteUUID.collect {
                    rememberPalette = it
                    Log.d("Palette", "Palette init: ${LocalPalettesMap[rememberPalette]?.extrasFamily?.title?.let { context.getString(it) }}")
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                globalPreferencesViewModel.currentTypographyUUID.collect {
                    rememberTypography = it
                    Log.d("Typography", "Typography init: ${LocalTypographiesMap[rememberTypography]?.extrasFamily?.title?.let { context.getString(it) }}")
                }
            }
        }
    }


    val palette: ExtendedPalette = LocalPalettesMap[rememberPalette] ?: ClassicPalette
    val typography: ExtendedTypography = LocalTypographiesMap[rememberTypography] ?: ClassicTypography

    Log.d("Palette", stringResource(palette?.extrasFamily?.title ?: R.string.alert_gui_palette_error))
    Log.d("Typography", stringResource(typography?.extrasFamily?.title ?: R.string.alert_gui_typography_error))

    SelectiveTheme(
        palette = palette,
        typography = typography
    ) {

        SettingsContent(
            globalPreferencesViewModel = globalPreferencesViewModel,
            onChangeColor = { rememberPalette = it },
            onChangeFont = { rememberTypography = it },
            onSave = { onSave(rememberPalette, rememberTypography) },
            onCancel = onCancel,
            tempCurrentColor = rememberPalette,
            tempCurrentTypography = rememberTypography
        )
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

    globalPreferencesViewModel.fontThemeHandler.saveSelectedIndex()
    globalPreferencesViewModel.colorThemeHandler.saveSelectedIndex()
    globalPreferencesViewModel.saveCurrentColorID()

    try { globalPreferencesViewModel.saveAll() }
    catch (e: IllegalStateException) { e.printStackTrace() }

    try { globalPreferencesViewModel.saveToFile(requireContext()) }
    catch (e: IllegalStateException) { e.printStackTrace() }

    activity.changeTheme(
        globalPreferencesViewModel.colorThemeHandler.currentTheme,
        globalPreferencesViewModel.fontThemeHandler.currentTheme
    )

    activity.recreate()*/

    val message = "Changes Saved"
    //Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}
