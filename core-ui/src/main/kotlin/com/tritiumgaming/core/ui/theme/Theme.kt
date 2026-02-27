package com.tritiumgaming.core.ui.theme

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowCompat
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.ExtendedPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.theme.type.ExtendedTypography
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.shared.data.preferences.DensityType

@Composable
fun SelectiveTheme(
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

            val window = (view.context as Activity).window

            window.statusBarColor = palette.surface.toArgb()
            window.navigationBarColor = palette.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                palette.extrasFamily.isLightMode
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                palette.extrasFamily.isLightMode

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

