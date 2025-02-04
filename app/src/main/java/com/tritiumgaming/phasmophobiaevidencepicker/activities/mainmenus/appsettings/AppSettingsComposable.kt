package com.tritiumgaming.phasmophobiaevidencepicker.ui.mainmenus.appsettings

import android.view.WindowManager
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.ui.pet.PETActivity
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.datastore.ds.GlobalPreferencesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.theme.palettes.LocalColorSchemes
import com.tritiumgaming.phasmophobiaevidencepicker.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.theme.palettes.Non_Colorblind
import com.tritiumgaming.phasmophobiaevidencepicker.theme.types.Classic
import com.tritiumgaming.phasmophobiaevidencepicker.theme.types.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.theme.types.LocalTypographys
import com.tritiumgaming.phasmophobiaevidencepicker.views.composables.NavigationHeaderComposable
import com.tritiumgaming.phasmophobiaevidencepicker.views.composables.PETImageButtonType
import kotlinx.coroutines.flow.MutableStateFlow
import org.jetbrains.annotations.TestOnly

@Preview
@TestOnly
@Composable
private fun SettingsFragmentPreview() {
    SettingsFragment()
}

@Composable
fun SettingsFragment(
    globalPreferencesViewModel: GlobalPreferencesViewModel? = null,
    onSave: () -> Unit = {},
    onCancel: () -> Unit = {}
) {

    val colorThemeState = globalPreferencesViewModel?.currentColorID?.collectAsState()
    val fontThemeState = globalPreferencesViewModel?.currentFontID?.collectAsState()

    var rememberColorTheme = remember {
        mutableStateOf(colorThemeState?.value)
    }
    var rememberFontTheme = remember {
        mutableStateOf(fontThemeState?.value)
    }

    val palette =
        LocalColorSchemes.firstOrNull { it.uuid == rememberColorTheme.value }
            ?.palette ?: Non_Colorblind
    val typography =
        LocalTypographys.firstOrNull { it.uuid == rememberFontTheme.value }
            ?.typography ?: Classic

    SelectiveTheme(
        theme = palette,
        typography = typography
    ){
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
                //.fillMaxSize(),
            color = LocalPalette.current.surface.color
        ) {

            Column(
                modifier = Modifier
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top),
            ) {
                NavigationHeaderComposable(
                    PETImageButtonType.CANCEL,
                    PETImageButtonType.CONFIRM,
                    centerTitleRes = R.string.settings_title,
                    leftOnClick = onCancel,
                    rightOnClick = onSave
                    /*leftOnClick = {
                        globalPreferencesViewModel?.let { globalPreferencesViewModel ->
                            globalPreferencesViewModel.colorThemeHandler.revertToSavedIndex()
                            globalPreferencesViewModel.fontThemeHandler.revertToSavedIndex()

                            (LocalContext.current.applicationContext as PETActivity).changeTheme(
                                globalPreferencesViewModel.colorThemeHandler.currentTheme,
                                globalPreferencesViewModel.fontThemeHandler.currentTheme)

                        }

                        NavController(LocalContext.current).popBackStack()
                    },
                    rightOnClick = {
                        SaveAllPreferences(globalPreferencesViewModel)

                        NavController(LocalContext.current).popBackStack()
                    }*/
                )

                HorizontalDivider()

                Column(
                    modifier = Modifier,
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
                            checked = globalPreferencesViewModel?.screenSaverPreference
                                ?: MutableStateFlow(false),
                            onChange = { globalPreferencesViewModel?.setScreenSaverPreference(it) }
                        )

                        LabeledSwitch(
                            label = stringResource(R.string.settings_networktitle),
                            checked = globalPreferencesViewModel?.networkPreference
                                ?: MutableStateFlow(false),
                            onChange = { globalPreferencesViewModel?.setNetworkPreference(it) }
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
                            checked = globalPreferencesViewModel?.rTLPreference
                                ?: MutableStateFlow(false),
                            onChange = { globalPreferencesViewModel?.setRTLPreference(it) }
                        )

                        LabeledSwitch(
                            label = stringResource(R.string.settings_enablehuntaudioqueue),
                            checked = globalPreferencesViewModel?.huntWarningAudioPreference
                                ?: MutableStateFlow(false),
                            onChange = {
                                globalPreferencesViewModel?.setHuntWarningAudioPreference(
                                    it
                                )
                            }
                        )

                        LabeledSwitch(
                            label = stringResource(R.string.settings_enableGhostReorder),
                            checked = globalPreferencesViewModel?.ghostReorderPreference
                                ?: MutableStateFlow(false),
                            onChange = { globalPreferencesViewModel?.setGhostReorderPreference(it) }
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
                            globalPreferencesViewModel?.let {
                                it.colorThemeHandler.iterateSelectedIndex(-1)
                                rememberColorTheme.value =
                                    it.colorThemeHandler.getThemeAtIndex(
                                        it.colorThemeHandler.selectedIndex).iD
                            }
                        },
                        rightOnClick = {
                            globalPreferencesViewModel?.let {
                                it.colorThemeHandler.iterateSelectedIndex(1)
                                rememberColorTheme.value =
                                    it.colorThemeHandler.getThemeAtIndex(
                                        it.colorThemeHandler.selectedIndex).iD
                            }
                        }
                    )

                    CarouselComposable(
                        title = R.string.settings_fontstylesettings,
                        state = MutableStateFlow(LocalTypography.current.extrasFamily.title),
                        colorFilter = ColorFilter.tint(LocalPalette.current.textFamily.body),
                        painterResource = painterResource(R.drawable.ic_font_family),
                        leftOnClick = {
                            globalPreferencesViewModel?.let {
                                it.fontThemeHandler.iterateSelectedIndex(-1)
                                rememberFontTheme.value =
                                    it.fontThemeHandler.getThemeAtIndex(
                                        it.fontThemeHandler.selectedIndex).iD
                            }
                        },
                        rightOnClick = {
                            globalPreferencesViewModel?.let {
                                it.fontThemeHandler.iterateSelectedIndex(1)
                                rememberFontTheme.value =
                                    it.fontThemeHandler.getThemeAtIndex(
                                        it.fontThemeHandler.selectedIndex).iD
                            }
                        }
                    )

                }
            }
        }
    }
}

@Composable
private fun SaveAllPreferences(globalPreferencesViewModel: GlobalPreferencesViewModel? = null) {
    globalPreferencesViewModel?.let { globalPreferencesViewModel ->
        /*seekbar?.let{ seekbar ->
            globalPreferencesViewModel.setHuntWarnTimeoutPreference(seekbar.progress.toLong())
        }*/

        globalPreferencesViewModel.fontThemeHandler.saveSelectedIndex()
        globalPreferencesViewModel.colorThemeHandler.saveSelectedIndex()
        globalPreferencesViewModel.saveCurrentColorID()

        /*try { globalPreferencesViewModel.saveAll() }
        catch (e: IllegalStateException) { e.printStackTrace() }*/
        /*try { globalPreferencesViewModel.saveToFile(requireContext()) }
        catch (e: IllegalStateException) { e.printStackTrace() }*/

        val activity = (LocalContext.current as PETActivity)
        activity.changeTheme(
            globalPreferencesViewModel.colorThemeHandler.currentTheme,
            globalPreferencesViewModel.fontThemeHandler.currentTheme
        )
        if (globalPreferencesViewModel.screenSaverPreference.value) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
        activity.recreate()
    }

    val message = "Changes Saved"
    Toast.makeText(LocalContext.current, message, Toast.LENGTH_LONG).show()
}