package com.tritiumgaming.core.ui.theme.palette.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class M3ColorFamily(
    val color: Color = Color.Unspecified,
    val onColor: Color = Color.Unspecified,
    val colorContainer: Color = Color.Unspecified,
    val onColorContainer: Color = Color.Unspecified,

    val inverseColor: Color = Color.Unspecified,

    val variant: Color = Color.Unspecified,
    val onColorVariant: Color = Color.Unspecified,
    val containerVariant: Color = Color.Unspecified,
    val onContainerVariant: Color = Color.Unspecified,

    val colorFixed: Color = Color.Unspecified,
    val colorFixedDim: Color = Color.Unspecified,
    val onColorFixed: Color = Color.Unspecified,
    val onColorFixedVariant: Color = Color.Unspecified,
)
