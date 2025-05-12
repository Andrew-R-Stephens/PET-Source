package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ExtendedPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.ExtendedTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.LocalTypography

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