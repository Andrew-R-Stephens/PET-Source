package com.tritiumgaming.core.ui.theme.palette.common

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class CodexColorFamily(
    val primary: Color = Color.Unspecified,

    val secondaryContainer: Color = Color.Unspecified,
    val onSecondaryContainer: Color = Color.Unspecified,

    val surface: Color = Color.Unspecified,

    val onSurface: Color = Color.Unspecified,
    val onSurfaceVariant: Color = Color.Unspecified,

    val surfaceContainer: Color = Color.Unspecified,

)
