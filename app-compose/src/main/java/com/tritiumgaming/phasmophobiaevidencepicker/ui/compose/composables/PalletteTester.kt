package com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.composables

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Agent
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Analyst
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Artist
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Commissioner
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.ContentCreator
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Detective
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Deuteranomaly
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Developer
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Discord
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Easter
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.ExtendedColorScheme
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Halloween23
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Holiday22
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Holiday23
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Investigator
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Monochromacy
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Non_Colorblind
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Operator
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.PrivateInvestigator
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Protanomaly
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Recruit
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Specialist
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.StrategemHero
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Technician
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Translator
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Tritanomaly
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Whiteboard
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.Winner
import com.tritiumgaming.phasmophobiaevidencepicker.theme.types.Classic
import com.tritiumgaming.phasmophobiaevidencepicker.theme.types.ExtendedTypography
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
        TestPalette(colorScheme = Non_Colorblind, colorSchemeName = "Non_Colorblind")
        TestPalette(colorScheme = Monochromacy, colorSchemeName = "Monochromacy")
        TestPalette(colorScheme = Deuteranomaly, colorSchemeName = "Deuteranomaly")
        TestPalette(colorScheme = Protanomaly, colorSchemeName = "Protanomaly")
        TestPalette(colorScheme = Tritanomaly, colorSchemeName = "Tritanomaly")
        TestPalette(colorScheme = Whiteboard, colorSchemeName = "Whiteboard")
        TestPalette(colorScheme = Recruit, colorSchemeName = "Recruit")
        TestPalette(colorScheme = Investigator, colorSchemeName = "Investigator")
        TestPalette(colorScheme = PrivateInvestigator, colorSchemeName = "PrivateInvestigator")
        TestPalette(colorScheme = Detective, colorSchemeName = "Detective")
        TestPalette(colorScheme = Technician, colorSchemeName = "Technician")
        TestPalette(colorScheme = Specialist, colorSchemeName = "Specialist")
        TestPalette(colorScheme = Analyst, colorSchemeName = "Analyst")
        TestPalette(colorScheme = Agent, colorSchemeName = "Agent")
        TestPalette(colorScheme = Operator, colorSchemeName = "Operator")
        TestPalette(colorScheme = Commissioner, colorSchemeName = "Commissioner")
        TestPalette(colorScheme = Easter, colorSchemeName = "Easter")
        TestPalette(colorScheme = Halloween23, colorSchemeName = "Halloween23")
        TestPalette(colorScheme = Holiday22, colorSchemeName = "Holiday22")
        TestPalette(colorScheme = Holiday23, colorSchemeName = "Holiday23")
        TestPalette(colorScheme = Discord, colorSchemeName = "Discord")
        TestPalette(colorScheme = ContentCreator, colorSchemeName = "ContentCreator")
        TestPalette(colorScheme = Developer, colorSchemeName = "Developer")
        TestPalette(colorScheme = Translator, colorSchemeName = "Translator")
        TestPalette(colorScheme = Winner, colorSchemeName = "Winner")
        TestPalette(colorScheme = Artist, colorSchemeName = "Artist")
        TestPalette(colorScheme = StrategemHero, colorSchemeName = "StrategemHero")
    }
}

@TestOnly
@Composable
fun TestPalette(
    colorScheme: ExtendedColorScheme = Non_Colorblind,
    typography: ExtendedTypography = Classic,
    colorSchemeName: String = "",
    typographyName: String = ""
) {

    SelectiveTheme(
        theme = colorScheme,
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
fun PaletteColor(
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