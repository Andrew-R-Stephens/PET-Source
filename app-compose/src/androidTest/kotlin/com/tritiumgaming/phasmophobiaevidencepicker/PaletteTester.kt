package com.tritiumgaming.compose

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.ExtendedPalette
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.core.ui.theme.palette.LocalPalettesMap
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.theme.type.ExtendedTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import org.jetbrains.annotations.TestOnly


@TestOnly
@Preview
@Composable
fun PaletteTester() {
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LocalPalettesMap.forEach {
            TestPalette(
                colorScheme = it.value,
                colorSchemeName = stringResource(it.value.extrasFamily.title)
            )
        }

    }
}

@TestOnly
@Composable
private fun TestPalette(
    colorScheme: ExtendedPalette = ClassicPalette,
    typography: ExtendedTypography = ClassicTypography,
    colorSchemeName: String = "",
    typographyName: String = ""
) {

    SelectiveTheme(
        palette = colorScheme,
        typography = typography
    ) {
        Column(
            Modifier
                .background(LocalPalette.current.surface.color)
                .padding(4.dp)
        ) {

            Text(
                text = colorSchemeName,
                color = colorScheme.textFamily.primary
            )
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .wrapContentHeight()
                    .wrapContentWidth()
            ) {

                PaletteColor(name = "primary", color = LocalPalette.current.coreFamily.primary)
                PaletteColor(name = "color_1", color = LocalPalette.current.coreFamily.color_1)
                PaletteColor(name = "color_2", color = LocalPalette.current.coreFamily.color_2)
                PaletteColor(name = "color_3", color = LocalPalette.current.coreFamily.color_3)
                PaletteColor(name = "color_4", color = LocalPalette.current.coreFamily.color_4)
                PaletteColor(name = "color_5", color = LocalPalette.current.coreFamily.color_5)
                PaletteColor(name = "color_6", color = LocalPalette.current.coreFamily.color_6)
                PaletteColor(name = "color_7", color = LocalPalette.current.coreFamily.color_7)
                PaletteColor(name = "color_8", color = LocalPalette.current.coreFamily.color_8)

                PaletteColor(
                    color = LocalPalette.current.primary.color,
                    onColor = LocalPalette.current.primary.onColor
                )
                PaletteColor(
                    color = LocalPalette.current.secondary.color,
                    onColor = LocalPalette.current.secondary.onColor
                )
                PaletteColor(
                    color = LocalPalette.current.tertiary.color,
                    onColor = LocalPalette.current.tertiary.onColor
                )
            }

            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .wrapContentHeight()
                    .wrapContentWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Switch(
                    colors = SwitchDefaults.colors(
                        uncheckedThumbColor = LocalPalette.current.switchTheme.thumbInactive,
                        checkedThumbColor = LocalPalette.current.switchTheme.thumbActive,
                        uncheckedTrackColor = LocalPalette.current.switchTheme.trackInactive,
                        checkedTrackColor = LocalPalette.current.switchTheme.trackActive,
                    ),
                    checked = false,
                    onCheckedChange = {}
                )

                Switch(
                    colors = SwitchDefaults.colors(
                        uncheckedThumbColor = LocalPalette.current.switchTheme.thumbInactive,
                        checkedThumbColor = LocalPalette.current.switchTheme.thumbActive,
                        uncheckedTrackColor = LocalPalette.current.switchTheme.trackInactive,
                        checkedTrackColor = LocalPalette.current.switchTheme.trackActive,
                    ),
                    checked = true,
                    onCheckedChange = {}
                )
            }
        }
    }

}

@TestOnly
@Composable
private fun PaletteColor(
    name: String = "",
    color: Color = Color.Unspecified,
    onColor: Color = color
) {
    if(color == Color.Unspecified) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_selector_inc_unsel),
                contentDescription = ""
            )
        }
    } else {
        val context = LocalContext.current
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(onColor)
                .clickable(
                    onClick = {
                        Toast.makeText(context, name, Toast.LENGTH_LONG).show()
                    }
                ),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(color)
            )
        }
    }

}