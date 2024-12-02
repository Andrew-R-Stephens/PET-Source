package com.TritiumGaming.phasmophobiaevidencepicker.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import com.TritiumGaming.phasmophobiaevidencepicker.theme.colorSchemes.LocalPalette
import com.TritiumGaming.phasmophobiaevidencepicker.theme.colorSchemes.Non_Colorblind
import com.TritiumGaming.phasmophobiaevidencepicker.theme.types.Classic
import com.TritiumGaming.phasmophobiaevidencepicker.theme.types.LocalCustomFonts

@Preview
@Composable
fun SelectiveTheme(
    //darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit = {}
) {

    // here is the important point, where you will expose custom objects
    CompositionLocalProvider(
        LocalPalette provides Non_Colorblind, // our custom palette
        LocalCustomFonts provides Classic // our custom font
    ) {
        MaterialTheme(
            shapes = NormalShapes,
            content = content
        )
    }

}
