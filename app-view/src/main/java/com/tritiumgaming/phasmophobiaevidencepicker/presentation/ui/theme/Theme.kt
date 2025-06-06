package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme

import android.app.Activity
import androidx.annotation.StyleRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.ExtendedPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.PaletteFromStyle
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.ExtendedTypography
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.TypographyFromStyle

@Composable
fun SelectiveTheme(
    @StyleRes palette: Int = R.style.NonColorblind_Base,
    @StyleRes typography: Int = R.style.NorseRegular_Manual,
    content: @Composable () -> Unit = {}
) {

    SelectiveTheme(
        palette = PaletteFromStyle[palette]?: ClassicPalette,
        typography = TypographyFromStyle[typography]?: ClassicTypography,
        content = content
    )

}

@Composable
fun SelectiveTheme(
    palette: ExtendedPalette = ClassicPalette,
    typography: ExtendedTypography = ClassicTypography,
    content: @Composable () -> Unit = {}
) {

    CompositionLocalProvider(
        LocalPalette provides palette,
        LocalTypography provides typography
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

            window.statusBarColor = palette.statusBarColor.toArgb()
            window.navigationBarColor = palette.navigationBarColor.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                palette.extrasFamily.isLightMode
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                palette.extrasFamily.isLightMode

        }

    }

}

abstract class ExtendedTheme