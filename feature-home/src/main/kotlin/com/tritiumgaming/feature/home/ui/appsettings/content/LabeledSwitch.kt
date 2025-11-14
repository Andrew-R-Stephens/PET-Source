package com.tritiumgaming.feature.home.ui.appsettings.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.StratagemHero
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography


@Composable
@Preview
fun Test() {
    SelectiveTheme(
        palette = StratagemHero
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(LocalPalette.current.surface)
                .padding(8.dp)
        ) {
            val rememberState1 = remember { mutableStateOf(false) }
            val rememberState2 = remember { mutableStateOf(true) }

            LabeledSwitch(state = rememberState1.value) {
                rememberState1.value = it
            }
            LabeledSwitch(state = rememberState2.value) {
                rememberState2.value = it
            }
        }
    }
}
/*@Composable
@Preview
fun Test() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        LocalPalettesMap.toList().forEach {
            SelectiveTheme(
                palette = it.second
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(LocalPalette.current.surface)
                        .padding(8.dp)
                ) {
                    LabeledSwitch(state = MutableStateFlow(false))
                    LabeledSwitch(state = MutableStateFlow(true))
                }
            }
        }
    }
}*/

/*@Composable
fun LabeledSwitch(
    modifier: Modifier = Modifier,
    label: String = "Default Label",
    state: StateFlow<Boolean> = MutableStateFlow(false),
    switchColors: SwitchColors = SwitchDefaults.colors(
        uncheckedTrackColor = LocalPalette.current.surfaceContainerHighest,
        uncheckedBorderColor = LocalPalette.current.outline,
        uncheckedThumbColor = LocalPalette.current.outline,
        checkedTrackColor = LocalPalette.current.primary,
        checkedBorderColor = Color.Transparent,
        checkedThumbColor = LocalPalette.current.onPrimary,
    ),
    textColor: Color = Color.White,
    onChange: (it: Boolean) -> Unit = {}
) {

    val rememberLabel by remember { mutableStateOf(label) }
    val checkedState = state.collectAsStateWithLifecycle()

    Row(
        modifier = modifier
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
            color = textColor,
            fontSize = 16.sp,
            maxLines = 1,
            style = LocalTypography.current.primary.regular
        )

    }

}*/

@Composable
fun LabeledSwitch(
    modifier: Modifier = Modifier,
    label: String = "Default Label",
    state: Boolean = false,
    switchColors: SwitchColors = SwitchDefaults.colors(
        uncheckedTrackColor = LocalPalette.current.surfaceContainerHighest,
        uncheckedBorderColor = LocalPalette.current.outline,
        uncheckedThumbColor = LocalPalette.current.outline,
        checkedTrackColor = LocalPalette.current.primary,
        checkedBorderColor = Color.Transparent,
        checkedThumbColor = LocalPalette.current.onPrimary,
    ),
    textColor: Color = Color.White,
    onChange: (state: Boolean) -> Unit = {}
) {
    Row(
        modifier = modifier
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Switch(
            colors = switchColors,
            checked = state,
            onCheckedChange = { newState ->
                onChange(newState)
            }
        )

        Text(
            text = label,
            color = textColor,
            fontSize = 16.sp,
            maxLines = 1,
            style = LocalTypography.current.primary.regular
        )

    }

}
