package com.tritiumgaming.core.ui.widgets.switch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.core.ui.theme.LocalThemeProvider
import com.tritiumgaming.shared.data.market.palette.mappers.PaletteResources


@Composable
@Preview
fun Test() {
    LocalThemeProvider(
        palette = PaletteResources.PaletteType.STRATAGEM_HERO
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(LocalPalette.current.surface)
                .padding(8.dp)
        ) {
            val rememberState1 = remember { mutableStateOf(false) }
            val rememberState2 = remember { mutableStateOf(true) }

            LabeledSwitch(
                label = "Default",
                state = rememberState1.value
            ) {
                rememberState1.value = it
            }
            LabeledSwitch(
                label = "Default",
                state = rememberState2.value
            ) {
                rememberState2.value = it
            }
        }
    }
}

@Composable
fun LabeledSwitch(
    modifier: Modifier = Modifier,
    label: String = "",
    state: Boolean = false,
    switchColors: SwitchColors = SwitchDefaults.colors(),
    textColor: Color = Color.White,
    onChange: (state: Boolean) -> Unit = {}
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Max)
            .width(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.Top
    ) {

        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.TopStart
        ) {
            Switch(
                modifier = Modifier,
                colors = switchColors,
                checked = state,
                onCheckedChange = { newState ->
                    onChange(newState)
                }
            )
        }

        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(),
                text = label,
                color = textColor,
                fontSize = 16.sp,
                style = LocalTypography.current.primary.regular
            )
        }

    }

}
