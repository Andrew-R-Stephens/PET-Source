package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.tritiumgaming.core.ui.theme.NormalShapes
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.ExtendedPalette
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.theme.type.ExtendedTypography
import com.tritiumgaming.core.ui.theme.type.LocalTypography

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
