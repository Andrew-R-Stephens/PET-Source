package com.tritiumgaming.phasmophobiaevidencepicker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.ExtendedPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalettesMap
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.theme.type.ExtendedTypography
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.settings.ui.components.LabeledSwitch
import org.jetbrains.annotations.TestOnly


@TestOnly
@Preview(device = "spec:width=1500px,height=4000px,dpi=440")
@Composable
fun PaletteTester() {
    LazyColumn {
        items(items = LocalPalettesMap.entries.toList()) {
            TestM3Palette(
                palette = it.value
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
                .background(LocalPalette.current.surfaceContainer)
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

                PaletteColor(
                    modifier = Modifier
                        .weight(1f)
                        .height(32.dp)
                        .width(128.dp),
                    name = "primary",
                    color = LocalPalette.current.coreFamily.primary)
                PaletteColor(
                    modifier = Modifier
                        .weight(1f)
                        .height(32.dp)
                        .width(128.dp),
                    name = "color_1",
                    color = LocalPalette.current.coreFamily.color_1)
                PaletteColor(
                    modifier = Modifier
                        .weight(1f)
                        .height(32.dp)
                        .width(128.dp),
                    name = "color_2",
                    color = LocalPalette.current.coreFamily.color_2)
                PaletteColor(
                    modifier = Modifier
                        .weight(1f)
                        .height(32.dp)
                        .width(128.dp),
                    name = "color_3",
                    color = LocalPalette.current.coreFamily.color_3)
                PaletteColor(
                    modifier = Modifier
                        .weight(1f)
                        .height(32.dp)
                        .width(128.dp),
                    name = "color_4",
                    color = LocalPalette.current.coreFamily.color_4)
                PaletteColor(
                    modifier = Modifier
                        .weight(1f)
                        .height(32.dp)
                        .width(128.dp),
                    name = "color_5",
                    color = LocalPalette.current.coreFamily.color_5)
                PaletteColor(
                    modifier = Modifier
                        .weight(1f)
                        .height(32.dp)
                        .width(128.dp),
                    name = "color_6",
                    color = LocalPalette.current.coreFamily.color_6)
                PaletteColor(
                    modifier = Modifier
                        .weight(1f)
                        .height(32.dp)
                        .width(128.dp),
                    name = "color_7",
                    color = LocalPalette.current.coreFamily.color_7)
                PaletteColor(
                    modifier = Modifier
                        .weight(1f)
                        .height(32.dp)
                        .width(128.dp),
                    name = "color_8",
                    color = LocalPalette.current.coreFamily.color_8)

                PaletteColor(
                    modifier = Modifier
                        .weight(1f)
                        .height(32.dp)
                        .width(128.dp),
                    color = LocalPalette.current.primary,
                    onColor = LocalPalette.current.onPrimary
                )
                PaletteColor(
                    modifier = Modifier
                        .weight(1f)
                        .height(32.dp)
                        .width(128.dp),
                    color = LocalPalette.current.secondary,
                    onColor = LocalPalette.current.onSecondary
                )
                PaletteColor(
                    modifier = Modifier
                        .weight(1f)
                        .height(32.dp)
                        .width(128.dp),
                    color = LocalPalette.current.tertiary,
                    onColor = LocalPalette.current.onTertiary
                )
            }

            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .wrapContentHeight()
                    .wrapContentWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                /*Switch(
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
                )*/
            }
        }
    }

}

@TestOnly
@Composable
private fun PaletteColor(
    modifier: Modifier = Modifier,
    name: String = "",
    color: Color = Color.Unspecified,
    onColor: Color = color
) {
    if(color == Color.Unspecified) {
        Box(
            modifier = modifier
                .size(32.dp)
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_selector_inc_unsel),
                contentDescription = "",
                colorFilter = ColorFilter.tint(color = LocalPalette.current.onSurface)
            )
        }
    } else {
        Box(
            modifier = modifier
                .background(color)
                .padding(8.dp),
            contentAlignment = Alignment.Center,
        ) {
            if(color != onColor) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .align(Alignment.TopStart),
                    text = "On $name",
                    fontSize = 8.sp,
                    color = onColor,
                    style = LocalTypography.current.quaternary.bold
                )
                /*Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(onColor)
                )*/
            }
        }
    }

}

@TestOnly
@Composable
private fun TestM3Palette(
    palette: ExtendedPalette = ClassicPalette
) {

    SelectiveTheme(
        palette = palette
    ) {
        Column(
            Modifier
                .background(LocalPalette.current.surface)
                .padding(4.dp)
        ) {

            Text(
                text = stringResource(palette.extrasFamily.title),
                color = LocalPalette.current.onSurface,
                style = LocalTypography.current.quaternary.bold,
                fontSize = 24.sp,
            )

            Text(
                text = stringResource(palette.extrasFamily.title),
                color = LocalPalette.current.onSurfaceVariant,
                style = LocalTypography.current.quaternary.bold,
                fontSize = 12.sp,
            )

            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .wrapContentHeight()
                    .wrapContentWidth()
            ) {

                Column {
                    PaletteColor(
                        name = "Primary",
                        color = LocalPalette.current.primary,
                        onColor = LocalPalette.current.onPrimary
                    )
                    PaletteColor(
                        name = "Primary Container",
                        color = LocalPalette.current.primaryContainer,
                        onColor = LocalPalette.current.onPrimaryContainer
                    )
                }

                Column {
                    PaletteColor(
                        name = "Secondary",
                        color = LocalPalette.current.secondary,
                        onColor = LocalPalette.current.onSecondary
                    )
                    PaletteColor(
                        name = "Secondary Container",
                        color = LocalPalette.current.secondaryContainer,
                        onColor = LocalPalette.current.onSecondaryContainer
                    )
                }

                Column {
                    PaletteColor(
                        name = "Tertiary",
                        color = LocalPalette.current.tertiary,
                        onColor = LocalPalette.current.onTertiary
                    )
                    PaletteColor(
                        name = "Tertiary Container",
                        color = LocalPalette.current.tertiaryContainer,
                        onColor = LocalPalette.current.onTertiaryContainer
                    )
                }

                Column {
                    PaletteColor(
                        name = "Error",
                        color = LocalPalette.current.error,
                        onColor = LocalPalette.current.onError
                    )
                    PaletteColor(
                        name = "Error Container",
                        color = LocalPalette.current.errorContainer,
                        onColor = LocalPalette.current.onErrorContainer
                    )
                }

            }

            Column(
                modifier = Modifier
                    .height(32.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    PaletteColor(
                        modifier  = Modifier
                            .weight(1f)
                            .height(32.dp)
                            .width(128.dp),
                        color = LocalPalette.current.surfaceContainerLowest
                    )
                    PaletteColor(
                        modifier  = Modifier
                            .weight(1f)
                            .height(32.dp)
                            .width(128.dp),
                        color = LocalPalette.current.surfaceContainerLow
                    )
                    PaletteColor(
                        modifier  = Modifier
                            .weight(1f)
                            .height(32.dp)
                            .width(128.dp),
                        color = LocalPalette.current.surfaceContainer
                    )
                    PaletteColor(
                        modifier  = Modifier
                            .weight(1f)
                            .height(32.dp)
                            .width(128.dp),
                        color = LocalPalette.current.surfaceContainerHigh
                    )
                    PaletteColor(
                        modifier  = Modifier
                            .weight(1f)
                            .height(32.dp)
                            .width(128.dp),
                        color = LocalPalette.current.surfaceContainerHighest
                    )
                }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LabeledSwitch(
                    modifier = Modifier
                        .weight(1f),
                    label = "Switch Off",
                    state = false
                )
                LabeledSwitch(
                    modifier = Modifier
                        .weight(1f),
                    label = "Switch On",
                    state = true
                )
            }

        }
    }

}
