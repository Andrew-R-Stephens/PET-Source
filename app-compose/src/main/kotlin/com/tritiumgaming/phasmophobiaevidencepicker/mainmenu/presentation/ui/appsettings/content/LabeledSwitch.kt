package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus.appsettings.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.LocalTypography
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@Composable
@Preview
fun Test() {
    LabeledSwitch(checked = false)
}

@Composable
fun LabeledSwitch(
    label: String = "Default Label",
    checked: StateFlow<Boolean> = MutableStateFlow(false),
    switchColors: SwitchColors = SwitchDefaults.colors(
        uncheckedTrackColor = LocalPalette.current.switchTheme.trackInactive,
        checkedTrackColor = LocalPalette.current.switchTheme.trackActive,
        uncheckedThumbColor = LocalPalette.current.switchTheme.thumbInactive,
        checkedThumbColor = LocalPalette.current.switchTheme.thumbActive,
    ),
    onChange: (it: Boolean) -> Unit = {}
) {

    CSwitch(
        label = label,
        checked = checked,
        switchColors = switchColors,
        onChange = onChange
    )

}

@Composable
fun LabeledSwitch(
    label: String = "Default Label",
    checked: Boolean = false,
    switchColors: SwitchColors = SwitchDefaults.colors(),
    onChange: (it: Boolean) -> Unit = {}
) {

    CSwitch(
        label = label,
        checked = checked,
        switchColors = switchColors,
        onChange = onChange
    )

}

@Composable
private fun CSwitch(
    label: String = "Default Label",
    checked: Boolean = false,
    switchColors: SwitchColors = SwitchDefaults.colors(),
    onChange: (it: Boolean) -> Unit = {}
) {

    val rememberLabel by remember { mutableStateOf(label) }
    var rememberChecked by remember { mutableStateOf(checked) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Switch(
            colors = switchColors,
            checked = rememberChecked,
            onCheckedChange = {
                rememberChecked = !rememberChecked
                onChange(rememberChecked)
            }
        )

        Text(
            text = rememberLabel,
            color = LocalPalette.current.textFamily.body,
            fontSize = 16.sp,
            maxLines = 1,
            style = LocalTypography.current.primary.regular
        )

    }

}

@Composable
private fun CSwitch(
    label: String = "Default Label",
    checked: StateFlow<Boolean> = MutableStateFlow(false),
    switchColors: SwitchColors = SwitchDefaults.colors(),
    onChange: (it: Boolean) -> Unit = {}
) {

    val rememberLabel by remember { mutableStateOf(label) }
    var checkedState = checked.collectAsStateWithLifecycle()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Switch(
            colors = switchColors,
            checked = checkedState.value,
            onCheckedChange = { it ->
                onChange(it)
            }
        )

        Text(
            text = rememberLabel,
            color = LocalPalette.current.textFamily.body,
            fontSize = 16.sp,
            maxLines = 1,
            style = LocalTypography.current.primary.regular
        )

    }

}
