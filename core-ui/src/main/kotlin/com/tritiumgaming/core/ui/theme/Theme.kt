package com.tritiumgaming.core.ui.theme

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import com.tritiumgaming.core.ui.mapper.toPaletteResource
import com.tritiumgaming.core.ui.mapper.toTypographyResource
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.ExtendedPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.theme.type.ExtendedTypography
import com.tritiumgaming.shared.data.market.palette.mappers.PaletteResources.PaletteType
import com.tritiumgaming.shared.data.market.typography.mappers.TypographyResources.TypographyType

@Composable
fun SelectiveTheme(
    palette: PaletteType = PaletteType.CLASSIC,
    typography: TypographyType = TypographyType.CLASSIC,
    uiConfiguration: ExtendedUiConfiguration = ExtendedUiConfiguration(),
    content: @Composable () -> Unit = {}
) {
    val paletteResource = palette.toPaletteResource()
    val typographyResource = typography.toTypographyResource()

    SelectiveTheme(
        palette = paletteResource,
        typography = typographyResource,
        uiConfiguration = uiConfiguration,
        content = content
    )

}

@Composable
private fun SelectiveTheme(
    palette: ExtendedPalette = ClassicPalette,
    typography: ExtendedTypography = ClassicTypography,
    uiConfiguration: ExtendedUiConfiguration = ExtendedUiConfiguration(),
    content: @Composable () -> Unit = {}
) {

    CompositionLocalProvider(
        LocalPalette provides palette,
        LocalTypography provides typography,
        LocalUiConfiguration provides uiConfiguration
    ) {

        MaterialTheme(
            shapes = NormalShapes,
            content = content
        )

    }

    val view = LocalView.current
    if (!view.isInEditMode) {

        SideEffect {

            val context = view.context as ComponentActivity

            val surfaceColor = palette.surface.toArgb()
            val colors = if (palette.extrasFamily.isLightMode) {
                SystemBarStyle.light(surfaceColor, surfaceColor)
            } else { SystemBarStyle.dark(surfaceColor) }

            context.enableEdgeToEdge(
                statusBarStyle = colors,
                navigationBarStyle = colors
            )

        }

    }
}

@Composable
fun ThemeConfigurationControl(
    modifier: Modifier = Modifier,
    palette: ExtendedPalette = LocalPalette.current,
    typography: ExtendedTypography = LocalTypography.current,
    uiConfiguration: ExtendedUiConfiguration = ExtendedUiConfiguration(),
    content: @Composable () -> Unit = {}
) {

    Log.d("ThemeConfigCtrl", "Palette: ${stringResource(palette.extrasFamily.title)}")
    Log.d("ThemeConfigCtrl", "Typography: ${stringResource(typography.extrasFamily.title)}")
    Log.d("ThemeConfigCtrl", "UiConfig: [" +
            "\n\tdensityType: ${uiConfiguration.densityType}" +
            "\n\tisRtl: ${uiConfiguration.isRtl}\n]")

    SelectiveTheme(
        palette = palette,
        typography = typography,
        uiConfiguration = uiConfiguration
    ) {
        Box(
            modifier = modifier
        ) {
            content()
        }

    }

}

@Composable
fun ThemeConfigurationControl(
    modifier: Modifier = Modifier,
    palette: PaletteType = PaletteType.CLASSIC,
    typography: TypographyType = TypographyType.CLASSIC,
    uiConfiguration: ExtendedUiConfiguration = ExtendedUiConfiguration(),
    content: @Composable () -> Unit = {}
) {

    val paletteResource = palette.toPaletteResource()
    val typographyResource = typography.toTypographyResource()

    Log.d("ThemeConfigCtrl", "Palette: ${stringResource(paletteResource.extrasFamily.title)}")
    Log.d("ThemeConfigCtrl", "Typography: ${stringResource(typographyResource.extrasFamily.title)}")
    Log.d("ThemeConfigCtrl", "UiConfig: [" +
            "\n\tdensityType: ${uiConfiguration.densityType}" +
            "\n\tisRtl: ${uiConfiguration.isRtl}\n]")

    SelectiveTheme(
        palette = palette,
        typography = typography,
        uiConfiguration = uiConfiguration
    ) {
        Box(
            modifier = modifier
        ) {
            content()
        }

    }

}

val LocalPalette = staticCompositionLocalOf { ExtendedPalette() }
val LocalTypography = staticCompositionLocalOf { ExtendedTypography() }
